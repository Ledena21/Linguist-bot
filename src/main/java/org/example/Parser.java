package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.URLEncoder;

public class Parser {
    public static String parseWord(String word) {
        try { // начнем блок try, чтобы при ошибке программа не сломалась и перешла в catch
            String encword = URLEncoder.encode(word, "UTF-8"); // кодируем кириллицу в код, который понимает URL
            String url = String.format("https://gramota.ru/poisk?query=%s&mode=slovari&dicts[]=9", encword);
            String dict = "Современный словарь иностранных слов"; // название словаря

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            String page = doc.text(); // извлекли весь текст из HTML-страницы

            int dictInd = page.indexOf(dict);
            // находим индекс заголовка с исходным словарем
            if (dictInd == -1) { // если индекс -1, значит, не нашли
                return "Этого слова нет в словаре иностранных слов";
            }

            // берем текст после заголовка словаря, прибавляем его длину, чтобы он сам был не включительно
            String afterdict = page.substring(dictInd + dict.length());

            if (afterdict.contains(word)) { // если эта строка содержит наше слово
                int wordIndex = afterdict.indexOf(word); // мы запоминаем его индекс
                String result = afterdict.substring(wordIndex); // и берем текст начиная с этого слова

                // все эти слова ниже обычно значат конец определения, найдем их, чтобы найти конец
                String[] endMarkers = {"Словари", "Существительное", "Метасловарь"};

                int end = result.length();

                for (String marker : endMarkers) { // берем слово, которое является маркером конца
                    int markerIndex = result.indexOf(marker); // ищем его индекс
                    if (markerIndex != -1 && markerIndex < end) { // если слово найдено и его индекс меньше, чем индекс конца всего текста
                        end = markerIndex; // запоминаем индекс
                    }
                }

                result = result.substring(dict.length() + word.length() + 2, end).trim(); // обрезали строку до конца
                return result;

            } else {
                return String.format("Слово '%s' не найдено в словаре иностранных слов", word);
            }

        } catch (Exception e) {
            return String.format("Ошибка: %s", e.getMessage());
        }
    }
}