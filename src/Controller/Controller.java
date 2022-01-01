package Controller;

import Domain.ADT.MyHeap;
import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.Exceptions.MyException;
import Domain.PrgState;
import Domain.Values.RefValue;
import Domain.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;
    boolean displayFlag;
    ExecutorService executor;

    public Controller(IRepository repository, boolean displayFlag, ExecutorService executor) {
        this.repository = repository;
        this.displayFlag = displayFlag;
        this.executor = executor;
    }

    public Controller(IRepository repository, boolean flag) {
        this.repository = repository;
        displayFlag = flag;
    }

    public Controller(IRepository repository) {
        this.repository = repository;
        displayFlag = false;
    }

    public void addProgram(PrgState prg) {
        repository.addProgram(prg);
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public void setLogFile(String logFile) {
        repository.setLogFilePath(logFile);
    }

    public PrgState getCurrent() {
        return repository.getCrtPrg();
    }

    Map<Integer, Value> unsafeGarbageCollector(MyIDictionary<String, Value> symTable, MyIHeap heap) {
        List<Integer> symTableAddr = getAddrFromSymTable(symTable.getContent().values());
        Map<Integer, Value> heapContent = heap.getContent();
        return heapContent.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue));
    }

    Map<Integer, Value> safeGarbageCollector(MyIDictionary<String, Value> symTable, MyIHeap heap) {
        Map<Integer, Value> heapContent = heap.getContent();
        List<Integer> symTableAddr = getAddrFromSymTable(symTable.getContent().values());
        List<Integer> heapAddr = getAddrFromSymTable(heapContent.values());
        return heapContent.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(ConcurrentHashMap.Entry::getKey, ConcurrentHashMap.Entry::getValue));
    }

    Map<Integer, Value> conservativeGarbageCollector(List<PrgState> prgList) {
        Map<Integer, Value> heapContent = prgList.get(0).getHeap().getContent();
        List<Integer> symTableAddr = new ArrayList<Integer>();
        prgList.stream()
                .forEach(prg -> {
                    symTableAddr.addAll(getAddrFromSymTable(prg.getSymTable().getContent().values()));
                });
        List<Integer> heapAddr = getAddrFromSymTable(heapContent.values());
        return heapContent.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(ConcurrentHashMap.Entry::getKey, ConcurrentHashMap.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgList) {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (IOException e) {
                throw new MyException("Error logging to file program: " + prg.getId());
            }
        });

        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
//        System.out.println(prgList);

        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new MyException(e.toString());
                        }
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            //add the new created threads to the list of existing threads
            prgList.addAll(newPrgList);
            prgList.forEach(prg -> {
                try {
                    repository.logPrgStateExec(prg);
                    System.out.println(prg);
                } catch (IOException e) {
                    throw new MyException("Error logging to file program: " + prg.getId());
                }
            });
            repository.setPrgList(prgList);
        } catch (InterruptedException e) {
            throw new MyException(e.toString());
        }
    }

    public void allStep() {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while (prgList.size() > 0) {
            Map<Integer, Value> heapMap = conservativeGarbageCollector(prgList);
            MyIHeap newHeap = new MyHeap(heapMap, heapMap.keySet().stream().max(Integer::compare).orElse(1));
            prgList.stream()
                    .forEach(prg -> prg.setHeap(newHeap));
            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
//        System.out.println("Program list: " + prgList.size());
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repository.setPrgList(prgList);
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }
}
