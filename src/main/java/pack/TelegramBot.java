package pack;

import config.BotConfig; // это файл с токеном
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    // extends TelegramLongPollingBot мы пишем, потому что мы расширяем функционал готового телеграм бота
    // то есть в тг есть некий шаблон бота, мы его расширяем

    CommandProcessor commandProcessor = new CommandProcessor();

    // дальше будут геттеры для того, чтобы мы из файла получили имя и токен бота

    public String getBotUsername() {
        return BotConfig.username;
    }

    public String getBotToken() {
        return BotConfig.token;
    }

    // переопределяем то, что бот делает в случае получения обновлений

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // если пришло сообщение и оно содержит текст
            String input = update.getMessage().getText(); // ввод пользователя считываем в строку
            long chatId = update.getMessage().getChatId(); // запоминаем номер чата, куда будем отправлять ответ

            String response = commandProcessor.processCommand(input); // обработчик команд формирует ответ

            try {
                execute(new SendMessage(String.valueOf(chatId), response)); //пытаемся отправить сообщение
            } catch (TelegramApiException e) {
                e.printStackTrace(); // вывод ошибки в консоль
            }
        }
    }
}