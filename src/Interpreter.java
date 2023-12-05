import model.ProgramState;
import model.ADT.*;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.*;
import repository.*;
import controller.*;
import view.*;

import java.util.List;
import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        // Example 1: int v; v=2;Print(v)
        InterfaceStatement ex1 = new ComparisonStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new ComparisonStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        Controller ctr1 = createController(ex1, "log1.txt");
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));

        // Example 2: int a; int b; a=2+3*5;b=a+1;Print(b)
        InterfaceStatement ex2 = new ComparisonStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new ComparisonStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new ComparisonStatement(
                                new AssignStatement("a", new ArithmeticExpression(
                                        "+", new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression("*", new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)))
                                )),
                                new ComparisonStatement(
                                        new AssignStatement("b", new ArithmeticExpression("+", new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        Controller ctr2 = createController(ex2, "log2.txt");
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));

        // Example 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        InterfaceStatement ex3 = new ComparisonStatement(
                new VariableDeclarationStatement("a", new BoolType()),
                new ComparisonStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new ComparisonStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new ComparisonStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        Controller ctr3 = createController(ex3, "log3.txt");
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));

        // Example 4: Read and print from file
        InterfaceStatement ex4 = new ComparisonStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new ComparisonStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new ComparisonStatement(
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new ComparisonStatement(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new ComparisonStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new ComparisonStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new ComparisonStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new ComparisonStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        Controller ctr4 = createController(ex4, "log4.txt");
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));

        // Example: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
        InterfaceStatement ex5 = new ComparisonStatement(
                new VariableDeclarationStatement("v", new RefrenceType(new IntType())),
                new ComparisonStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new ComparisonStatement(
                                new VariableDeclarationStatement("a", new RefrenceType(new RefrenceType(new IntType()))),
                                new ComparisonStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new ComparisonStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );
        Controller ctr5 = createController(ex5, "log5.txt");
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));

        // Example: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
        InterfaceStatement ex6 = new ComparisonStatement(
                new VariableDeclarationStatement("v", new RefrenceType(new IntType())),
                new ComparisonStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new ComparisonStatement(
                                new VariableDeclarationStatement("a", new RefrenceType(new RefrenceType(new IntType()))),
                                new ComparisonStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new ComparisonStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression("+",
                                                        new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5)))
                                                )
                                        )
                                )
                        )
                )
        );
        Controller ctr6 = createController(ex6, "log6.txt");
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));

        // Example: Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
        InterfaceStatement ex7 = new ComparisonStatement(
                new VariableDeclarationStatement("v", new RefrenceType(new IntType())),
                new ComparisonStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new ComparisonStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new ComparisonStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression("+",
                                                new ReadHeapExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5)))
                                        )
                                )
                        )
                )
        );
        Controller ctr7 = createController(ex7, "log7.txt");
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));

        // Example: Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        InterfaceStatement ex8 = new ComparisonStatement(
                new VariableDeclarationStatement("v", new RefrenceType(new IntType())),
                new ComparisonStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new ComparisonStatement(
                                new VariableDeclarationStatement("a", new RefrenceType(new RefrenceType(new IntType()))),
                                new ComparisonStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new ComparisonStatement(
                                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );
        Controller ctr8 = createController(ex8, "log8.txt");
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));

        // Example: int v; v=4; (while (v>0) print(v); v=v-1); print(v)
        InterfaceStatement ex9 = new ComparisonStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new ComparisonStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new ComparisonStatement(
                                new WhileStatement(
                                        new RelationalExpressions(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                        new ComparisonStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
        Controller ctr9 = createController(ex9, "log9.txt");
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));


        menu.show();

    }

    private static Controller createController(InterfaceStatement example, String logFilePath) {
        StackInterface<InterfaceStatement> stack = new MyStack<>();
        DictionaryInterface<String, Value> symbolTable = new MyDictionary<>();
        ListInterface<Value> outputList = new MyList<>();
        FileTableInterface<String, BufferedReader> fileTable = new MyFileTable<>();
        HeapInterface heapTable = new MyHeap();

        ProgramState programState = new ProgramState(stack, symbolTable, outputList, fileTable, heapTable, example);
        List<ProgramState> programStateList = List.of(programState);
        InterfaceRepository repository = new Repository(programStateList, logFilePath);
        return new Controller(repository);
    }
}
