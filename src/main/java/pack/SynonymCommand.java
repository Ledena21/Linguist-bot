package pack;

public class SynonymCommand {
    public String execute(String word) {
        return Parser.parseWord(word, "Словарь синонимов", "https://gramota.ru/poisk?query=%s&mode=slovari&dicts[]=30");
    }
}