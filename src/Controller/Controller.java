package Controller;

import Domain.ADT.MyIStack;
import Domain.Exceptions.MyException;
import Domain.PrgState;
import Domain.Statements.IStmt;
import Repository.IRepository;
import Repository.Repository;

import javax.swing.*;
import java.io.IOException;

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
