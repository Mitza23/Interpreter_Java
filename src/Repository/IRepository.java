package Repository;

import Domain.Exceptions.MyException;
import Domain.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    public PrgState getCrtPrg();

    public void addProgram(PrgState prg);

    public void logPrgStateExec() throws MyException, IOException;

    public void setLogFilePath(String logFilePath);

    public String getLogFilePath();

    public List<PrgState> getPrgList();

    public void setPrgList(List<PrgState> list);

    public void logPrgStateExec(PrgState state) throws MyException, IOException;
}
