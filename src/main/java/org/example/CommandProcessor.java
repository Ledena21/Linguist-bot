package org.example;

public class CommandProcessor {
    public boolean processCommand(String command) {
        switch (command) {
            case "\\help":
                new HelpCommand().execute();
                return true;
            case "\\exit":
                new ExitCommand().execute();
                return false;
            default:
                new DefaultCommand().execute(command);
                return true;
        }
    }
}