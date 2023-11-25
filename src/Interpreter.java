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
        Controller ctr4 = createController(ex4, "testOpenReadClose.txt");
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));

        menu.show();
    }

    private static Controller createController(InterfaceStatement example, String logFilePath) {
        StackInterface<InterfaceStatement> stack = new MyStack<>();
        DictionaryInterface<String, Value> symbolTable = new MyDictionary<>();
        ListInterface<Value> outputList = new MyList<>();
        FileTableInterface<String, BufferedReader> fileTable = new MyFileTable<>();

        ProgramState programState = new ProgramState(stack, symbolTable, outputList, fileTable, example);
        List<ProgramState> programStateList = List.of(programState);
        InterfaceRepository repository = new Repository(programStateList, logFilePath);
        return new Controller(repository);
    }
}
