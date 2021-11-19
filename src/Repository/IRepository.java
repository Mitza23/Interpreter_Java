package Repository;

import Domain.Exceptions.MyException;
import Domain.PrgState;

import java.io.IOException;

public interface IRepository {
    public PrgState getCrtPrg();
    public void addProgram(PrgState prg);
    public void logPrgStateExec() throws MyException, IOException;
    public void setLogFilePath(String logFilePath);
    public String getLogFilePath();
}
