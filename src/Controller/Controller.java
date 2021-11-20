package Controller;

import Domain.ADT.MyIStack;
import Domain.Exceptions.MyException;
import Domain.PrgState;
import Domain.Statements.IStmt;
import Domain.Values.RefValue;
import Domain.Values.Value;
import Repository.IRepository;
import Repository.Repository;

import javax.swing.*;
import java.io.IOException;
import java.util.Collection;
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

    public PrgState getCurrent(){
        return repository.getCrtPrg();
    }

//    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
//        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
//    }
//
//    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
//        return symTableValues.stream()
//                .filter(v-> v instanceof RefValue)
//                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
//                .collect(Collectors.toList());
//    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        if(stk.isEmpty()){
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
