package org.example;

public class DefaultCommand {
    public void execute(String input) {
        String result = Parser.parseWord(input);
        System.out.println(result);
    }
}