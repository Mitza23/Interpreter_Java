package Controller;

import Domain.ADT.MyIDictionary;
import Domain.ADT.MyIHeap;
import Domain.ADT.MyIStack;
import Domain.Exceptions.MyException;
import Domain.PrgState;
import Domain.Statements.IStmt;
import Domain.Values.RefValue;
import Domain.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;
    boolean displayFlag;

    public Controller(IRepository repository, boolean flag) {
        this.repository = repository;
        displayFlag = flag;
    }

    public Controller(IRepository repository) {
        this.repository = repository;
        displayFlag = false;
    }

    public void addProgram(PrgState prg){
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
        HashMap<Integer, Value> heapContent = heap.getContent();
        return heapContent.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue));
    }

    Map<Integer, Value> safeGarbageCollector(MyIDictionary<String, Value> symTable, MyIHeap heap) {
        HashMap<Integer, Value> heapContent = heap.getContent();
        List<Integer> symTableAddr = getAddrFromSymTable(symTable.getContent().values());
        List<Integer> heapAddr = getAddrFromSymTable(heapContent.values());
        return heapContent.entrySet()
                .stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue));
    }

//    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, HashMap<Integer,Value> heap){
//        return heap.entrySet()
//                .stream()
//                .filter(e->{
//                    boolean ok = symTableAddr.contains(e.getKey());
//                    if(!ok){
//                        List<RefValue> refValuesList = heap.entrySet()
//                                .stream()
//                                .filter(e1 -> e1.getValue() instanceof RefValue)
//                                .map(e1 ->(RefValue)e1.getValue())
//                                .collect(Collectors.toList());
//                        ArrayList<Integer> addresses = new ArrayList<Integer>();
//                        for(RefValue val : refValuesList){
//                            addresses.add(val.getAddr());
//                            while (val instanceof RefValue){
//                                val = heap.get(val.getAddr());
//                            }
//                        }
//                    }
//                    return ok;
//                })
//                .collect();
//    }

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

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        if (stk.isEmpty()) {
            throw new MyException("prgstate stack is empty");
        }
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

   public void allStep() throws MyException {
        try {
            PrgState prg = repository.getCrtPrg();
            repository.logPrgStateExec();
            if (displayFlag) {
                System.out.println(prg.toString());
                System.out.println("\n\n==============\n\n");
            }

            while (!prg.getStk().isEmpty()) {
                prg = oneStep(prg);
                repository.logPrgStateExec();

                prg.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(prg.getSymTable(), prg.getHeap()));

                repository.logPrgStateExec();
                if (displayFlag) {
                    System.out.println(prg.toString());
                    System.out.println("\n\n==============\n\n");
                }
            }
            if (!displayFlag) {
                System.out.println(prg.toString());
                System.out.println("\n\n==============\n\n");
            }
        }
        catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
