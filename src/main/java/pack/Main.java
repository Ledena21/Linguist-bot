package pack;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            // создаем объект класса TelegramBotsApi, это класс, который управляет всеми ботами в тг
            //и имеет их методы. DefaultBotSession.class это указали способ получения обновлений
            // через Long Polling, то есть постоянный опрос сервера тг
            botsApi.registerBot(new TelegramBot());
            // создаётся экземпляр класса TelegramBot и передаётся в api, т.е. бот регистрируется
            // в этот момент библиотека получает нваш токен, подключается к тг и начинает фоновый поток, который ждёт новых сообщений
            System.out.println("Бот запустился");
        } catch (TelegramApiException e) {
            System.err.println("Бот не запустился: " + e.getMessage());
        }
    }
}