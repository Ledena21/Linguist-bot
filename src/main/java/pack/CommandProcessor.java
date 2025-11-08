package pack;

public class CommandProcessor {

    public String processCommand(String input) {
        HelpCommand helpCommand = new HelpCommand();
        StartCommand startCommand = new StartCommand();
        ExitCommand exitCommand = new ExitCommand();
        DefaultCommand defaultCommand = new DefaultCommand();

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