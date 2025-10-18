package org.example;

public class CommandProcessor {
    private HelpCommand helpCommand = new HelpCommand();
    private ExitCommand exitCommand = new ExitCommand();
    private DefaultCommand defaultCommand = new DefaultCommand();

    public boolean processCommand(String command) {
        switch (command) {
            case "/help":
                helpCommand.execute();
                return true;
            case "/exit":
                exitCommand.execute();
                return false;
            default:
                defaultCommand.execute(command.toLowerCase());
                return true;
        }
    }
}