package pack;

public class DefaultCommand {
    public void execute(String input) {
        if (input.matches("[а-яА-ЯёЁ]+")){
            String result = Parser.parseWord(input);
            System.out.println(result);
        }
        else{
            System.out.println("Введите слово, написанное русской кириллицей, или команду");
        }
    }
}