package View;

import Controller.Controller;
import Domain.ADT.*;
import Domain.Exceptions.MyException;
import Domain.Expressions.*;
import Domain.PrgState;
import Domain.Statements.*;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Types.RefType;
import Domain.Types.StringType;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Domain.Values.StringValue;
import Domain.Values.Value;
import Repository.Repository;

import java.io.BufferedReader;
import java.util.Scanner;

public class View {
    Controller controller;

    public View() {
        controller = new Controller(new Repository(), false);
    }

    IStmt example1(){
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        return ex1;
    }

    IStmt example2() throws MyException {
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                                ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        return ex2;
    }

    IStmt example3() throws MyException{
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        return ex3;
    }

    void printExMenu(){
        System.out.println("What would you like to execute?");
        System.out.println("1. Example 1");
        System.out.println("2. Example 2");
        System.out.println("3. Example 3");
        System.out.println("0. Exit");
    }

    void printExecOptions(){
        System.out.println("1. Execute with intermediate steps");
        System.out.println("2. Execute without intermediate steps");
    }

    void execute(int ex) throws MyException {
        printExecOptions();
        Scanner scanner = new Scanner(System.in);
        int cmd = scanner.nextInt();
        switch (cmd){
            case 1:
                controller.setDisplayFlag(true);
                break;
            case 2:
                controller.setDisplayFlag(false);
                break;
            default:
                throw new MyException("Invalid input");
        }
        String fileName = getFileName();
        controller.setLogFile(fileName);
        IStmt stmt;
        switch (ex){
            case 1:
                stmt = example1();
                break;
            case 2:
                stmt = example2();
                break;
            case 3:
                stmt = example3();
                break;
            default:
                throw new MyException("Invalid input");
        }
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
        MyIList<Value> out = new MyList<Value>();
        controller.addProgram(new PrgState(stack, symtbl, out, stmt));
        controller.allStep();
    }

    private String getFileName() {
        System.out.println("Where should we log the program?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void test() {
        try {
            IStmt stmt = example2();
            MyIStack<IStmt> stack = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
            MyIList<Value> out = new MyList<Value>();
            controller.addProgram(new PrgState(stack, symtbl, out, stmt));
            controller.setDisplayFlag(true);
            controller.setLogFile("log.txt");
            controller.allStep();
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }
    }

    public void testFile(){
        try {
            MyIStack<IStmt> stack = new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
            MyIList<Value> out = new MyList<Value>();
            MyIDictionary<String, BufferedReader> filetbl = new MyDictionary<String, BufferedReader>();
            IStmt stmt = new CompStmt(new VarDeclStmt("varf", new StringType()),
                    new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                            new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                    new CompStmt(new VarDeclStmt("varc", new IntType()),
                                            new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                    new CompStmt(new PrintStmt(new VarExp("varc")),
                                                            new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                    new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                            new CloseRFileStmt(new VarExp("varf"))))))))));
            controller.addProgram(new PrgState(stack, symtbl, out, filetbl, stmt));
            controller.setDisplayFlag(true);
            controller.setLogFile("log.txt");
            controller.allStep();
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }
    }

    public void testDeepCopy(){
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
        MyIList<Value> out = new MyList<Value>();
        MyIDictionary<String, BufferedReader> filetbl = new MyDictionary<String, BufferedReader>();
        IStmt stmt = example3();
        controller.setLogFile("test.log");
        controller.addProgram(new PrgState(stack, symtbl, out, filetbl, stmt));
        System.out.println(controller.getCurrent());
        PrgState clone = controller.getCurrent().clone();
        controller.allStep();
        System.out.println(clone);

    }

    public void testHeapAllocation(){
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
        MyIList<Value> out = new MyList<Value>();
        MyIDictionary<String, BufferedReader> filetbl = new MyDictionary<String, BufferedReader>();
        MyIHeap heap = new MyHeap();
        IStmt stmt = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));
        controller.addProgram(new PrgState(stack, symtbl, out, filetbl, heap));
        stack.push(stmt);
        controller.setLogFile("log.txt");
        controller.setDisplayFlag(true);
        controller.allStep();
    }

    public void testHeapReading(){
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
        MyIList<Value> out = new MyList<Value>();
        MyIDictionary<String, BufferedReader> filetbl = new MyDictionary<String, BufferedReader>();
        MyIHeap heap = new MyHeap();
        IStmt stmt = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)))))))));

        controller.addProgram(new PrgState(stack, symtbl, out, filetbl, heap));
        stack.push(stmt);
        controller.setLogFile("log.txt");
        controller.allStep();
    }

    public void testHeapWriting(){
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
        MyIList<Value> out = new MyList<Value>();
        MyIDictionary<String, BufferedReader> filetbl = new MyDictionary<String, BufferedReader>();
        MyIHeap heap = new MyHeap();
        IStmt stmt = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("v")),
                                                new ValueExp(new IntValue(5))))))));

        controller.addProgram(new PrgState(stack, symtbl, out, filetbl, heap));
        stack.push(stmt);
        controller.setLogFile("log.txt");
        controller.allStep();
    }

    public void testGarbageCollector() {
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
        MyIList<Value> out = new MyList<Value>();
        MyIDictionary<String, BufferedReader> filetbl = new MyDictionary<String, BufferedReader>();
        MyIHeap heap = new MyHeap();
        IStmt stmt = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new CompStmt(new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))),
                                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(40))),
                                                                new PrintStmt(new ReadHeapExp(new VarExp("v"))))))))));
        controller.addProgram(new PrgState(stack, symtbl, out, filetbl, heap));
        stack.push(stmt);
        controller.setLogFile("log.txt");
        controller.setDisplayFlag(true);
        controller.allStep();

    }

    public void testWhileStmt() {
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl = new MyDictionary<String, Value>();
        MyIList<Value> out = new MyList<Value>();
        MyIDictionary<String, BufferedReader> filetbl = new MyDictionary<String, BufferedReader>();
        MyIHeap heap = new MyHeap();
        IStmt stmt = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"),
                                new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"),
                                                new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        controller.addProgram(new PrgState(stack, symtbl, out, filetbl, heap));
        stack.push(stmt);
        controller.setLogFile("log.txt");
        controller.setDisplayFlag(true);
        controller.allStep();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                printExMenu();
                int cmd = scanner.nextInt();
                if (cmd == 0) {
                    break;
                }
                execute(cmd);
            }
            catch (MyException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
