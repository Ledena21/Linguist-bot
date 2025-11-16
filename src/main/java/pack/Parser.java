package pack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Parser {

    public static String parseWord(String word, String dict, String link) {
        try {
            word = word.replace('ё', 'е').replace('Ё', 'Е');
            String encodedWord = URLEncoder.encode(word, StandardCharsets.UTF_8);
            String url = String.format(link, encodedWord);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10000)
                    .get();

            String page = doc.text();

            int dictIndex = page.indexOf(dict);
            if (dictIndex == -1) {
                return String.format("Слово «%s» не найдено в словаре «%s».", word, dict);
            }

            String afterDict = page.substring(dictIndex + dict.length());

            String capitalWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();

            int wordIndex = afterDict.indexOf(capitalWord);
            if (wordIndex == -1) {
                wordIndex = afterDict.toLowerCase().indexOf(word.toLowerCase());
                if (wordIndex == -1) {
                    return String.format("Слово «%s» не найдено в словаре «%s».", word, dict);
                }
            }

            String result = afterDict.substring(wordIndex);

            String[] endMarkers = {"Словари", "Существительное", "Метасловарь", "Прот.", "См.", "Ср.", "Синонимы:", "Антонимы:", "Сущ.", "Прил.", "Глаг.", "Он в", "Находится", "Используется"};

            int end = result.length();
            for (String marker : endMarkers) {
                int idx = result.indexOf(marker);
                if (idx != -1 && idx < end) {
                    end = idx;
                    break;
                }
            }

            if (dict.equals("Современный словарь иностранных слов")){
                result = result.substring(dict.length() + word.length() + 2, end).trim();
            } else {
                result = result.substring(0, end).trim();
            }


            return result;

        } catch (Exception e) {
            return String.format("Ошибка: %s", e.getMessage());
        }
    }
}