package view;
import controller.Controller;
import model.MyException;

public class RunExample extends Command
{
    private final Controller controller;
    public RunExample(String key, String description, Controller controller)
    {
        super(key, description);
        this.controller = controller;
    }
    @Override
    public void execute()
    {
        try
        {
            controller.allStep();
            System.out.println(controller.getOut(controller.getRepository().getCurrentProgram()));
        }
        catch (MyException error)
        {
            System.out.println(error.getMessage());
        }
    }
}
