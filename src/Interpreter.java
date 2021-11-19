import Controller.Controller;
import Domain.ADT.*;
import Domain.Exceptions.MyException;
import Domain.Expressions.ArithExp;
import Domain.Expressions.ValueExp;
import Domain.Expressions.VarExp;
import Domain.PrgState;
import Domain.Statements.*;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.Value;
import Repository.IRepository;
import Repository.Repository;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        try {
            IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new PrintStmt(new VarExp("v"))));
            MyIStack<IStmt> stack1 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl1 = new MyDictionary<String, Value>();
            MyIList<Value> out1 = new MyList<Value>();
            MyIDictionary<String, BufferedReader> filetbl1 = new MyDictionary<String, BufferedReader>();
            PrgState prg1 = new PrgState(stack1, symtbl1, out1, filetbl1, ex1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            Controller ctr1 = new Controller(repo1);


            IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                    new CompStmt(new VarDeclStmt("b", new IntType()),
                            new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                    ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                    new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new
                                            IntValue(1)))), new PrintStmt(new VarExp("b"))))));
            MyIStack<IStmt> stack2 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl2 = new MyDictionary<String, Value>();
            MyIList<Value> out2 = new MyList<Value>();
            MyIDictionary<String, BufferedReader> filetbl2 = new MyDictionary<String, BufferedReader>();
            PrgState prg2 = new PrgState(stack2, symtbl2, out2, filetbl2, ex2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            Controller ctr2 = new Controller(repo2);


            IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new
                                            IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                            VarExp("v"))))));
            MyIStack<IStmt> stack3 = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl3 = new MyDictionary<String, Value>();
            MyIList<Value> out3 = new MyList<Value>();
            MyIDictionary<String, BufferedReader> filetbl3 = new MyDictionary<String, BufferedReader>();
            PrgState prg3 = new PrgState(stack3, symtbl3, out3, filetbl3, ex3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            Controller ctr3 = new Controller(repo3);


            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
            menu.show();
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }
    }
}
