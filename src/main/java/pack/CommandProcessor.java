package pack;

public class CommandProcessor {

    private final HelpCommand helpCommand = new HelpCommand();
    private final StartCommand startCommand = new StartCommand();
    private final ExitCommand exitCommand = new ExitCommand();
    private final DefaultCommand defaultCommand = new DefaultCommand();
    private final SynonymCommand synonymCommand = new SynonymCommand();

    public String processCommand(String input) {
        if (input.startsWith("/syn_")) {
            String word = input.substring("/syn_".length());
            return synonymCommand.execute(word);
        }

        switch (input) {
            case "/help":
                return helpCommand.execute();
            case "/start":
                return startCommand.execute();
            case "/exit":
                return exitCommand.execute();
            default:
                if (input.matches("[а-яА-ЯёЁ]+")) {
                    return defaultCommand.execute(input);
                } else {
                    return "Введите слово кириллицей или команду (/help)";
                }
        }
    }
}