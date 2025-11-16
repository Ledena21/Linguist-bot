package pack;

public class CommandProcessor {

    private final HelpCommand helpCommand;
    private final StartCommand startCommand;
    private final ExitCommand exitCommand;
    private final DefaultCommand defaultCommand;

    public CommandProcessor(){
        helpCommand = new HelpCommand();
        startCommand = new StartCommand();
        exitCommand = new ExitCommand();
        defaultCommand = new DefaultCommand();
    }

    public String processCommand(String input) {

        switch (input) {
            case "/help":
                return helpCommand.execute();
            case "/start":
                return startCommand.execute();
            case "/exit":
                return exitCommand.execute();
            default:
                return defaultCommand.execute(input);
        }
    }
}