package Repository;

import Domain.Exceptions.MyException;
import Domain.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    List<PrgState> list;
    String logFilePath;

    public Repository() {
        list = new ArrayList<PrgState>();
    }

    public Repository(String logFilePath) {
        list = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
    }

    public Repository(PrgState prog, String logFilePath) {
        list = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
        addProgram(prog);
    }

    @Override
    public PrgState getCrtPrg() {
        return list.get(list.size() - 1);
    }

    public void addProgram(PrgState prg){
        list.add(prg);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)))) {
//            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
            logFile.println(getCrtPrg().getStk());
            logFile.println(getCrtPrg().getSymTable());
            logFile.println(getCrtPrg().getOut());
            logFile.println(getCrtPrg().getFileTable());
            logFile.println(getCrtPrg().getHeap());
            logFile.println("\n\n=====================\n\n");
            logFile.flush();
        } catch (IOException e) {
            throw new MyException("IO error: " + e.getMessage());
        }
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    @Override
    public List<PrgState> getPrgList() {
        return list;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.list = list;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException, IOException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(state.toString());
            logFile.println("\n\n=====================\n\n");
            logFile.flush();
        } catch (IOException e) {
            throw new MyException("IO error: " + e.getMessage());
        }
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
}
