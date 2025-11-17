package pack;

public class DefaultCommand {
    public String execute(String input) {
        if (input.matches("[а-яА-ЯёЁ]+")) {
            return Parser.parseWord(input, "Современный словарь иностранных слов", "https://gramota.ru/poisk?query=%s&mode=slovari&dicts[]=9");
        } else {
            return "Введите слово, написанное русской кириллицей, или команду (/help)";
        }
    }
}