package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URLEncoder;

public class Parser {
    public static String parseWord(String word) {
        try {
            String encword = URLEncoder.encode(word, "UTF-8");
            String url = "https://gramota.ru/poisk?query=" + encword + "&mode=slovari&dicts[]=9";
            String dict = "Современный словарь иностранных слов";

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            String page = doc.text();

            int dictInd = page.indexOf(dict);

            if (dictInd == -1) {
                return "Этого слова нет в словаре иностранных слов";
            }

            String afterdict = page.substring(dictInd + dict.length());

            if (afterdict.contains(word)) {
                int wordIndex = afterdict.indexOf(word);
                String result = afterdict.substring(wordIndex);

                String[] endMarkers = {"Словари", "Существительное", "Метасловарь"};

                int end = result.length();

                for (String marker : endMarkers) {
                    int markerIndex = result.indexOf(marker);
                    if (markerIndex != -1 && markerIndex < end) {
                        end = markerIndex;
                    }
                }

                result = result.substring(dict.length() + word.length() + 2, end).trim();
                return result;

            } else {
                return "Слово '" + word + "' не найдено в словаре иностранных слов";
            }

        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
