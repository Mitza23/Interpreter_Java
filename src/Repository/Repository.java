package Repository;

import Domain.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Repository implements IRepository{

    ArrayList<PrgState> list;
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
    public void logPrgStateExec() throws IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.println(getCrtPrg().getStk());
        logFile.println(getCrtPrg().getSymTable());
        logFile.println(getCrtPrg().getOut());
        logFile.println(getCrtPrg().getFileTable());
        logFile.println(getCrtPrg().getHeap());
        logFile.println("\n\n=====================\n\n");
        logFile.flush();
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
}
