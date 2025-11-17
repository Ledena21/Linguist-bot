package pack;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.Collections;

public class TelegramBot extends TelegramLongPollingBot {

    private final CommandProcessor commandProcessor = new CommandProcessor();

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) { // если есть сообщение и оно текст
            String text = update.getMessage().getText(); // получим сам текст

            String response = commandProcessor.processCommand(text); // отправим его обработчику команд
            SendMessage message = new SendMessage(String.valueOf(update.getMessage().getChatId()), response); // создаем объект класса, который отправляет сообщение, передаем id чата и текст ответа

            if (text.matches("[а-яА-ЯёЁ]+")) { // если что-то на кириллице
                InlineKeyboardButton button = new InlineKeyboardButton("Найти синонимы"); // создаем кнопку с надписью
                button.setCallbackData(String.format("/syn_%s", text)); // данные обратного вызова /syn + слово
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup(); // создадим контейнер строк под сообщением
                markup.setKeyboard(Collections.singletonList(Collections.singletonList(button))); // создаем список из одного элемента
                message.setReplyMarkup(markup); // разметка кнопки, чтобы она появилась под сообщением
            }

            try {
                execute(message); // пытаемся отправить ответ
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasCallbackQuery()) { // если кнопка нажата
            String data = update.getCallbackQuery().getData(); // получаем данные обратного вызова
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString(); // запоминаем id чата
            try {
                AnswerCallbackQuery answer = new AnswerCallbackQuery(); // создаем объект ответа
                answer.setCallbackQueryId(update.getCallbackQuery().getId()); // создаем ответ
                execute(answer); // пытаемся его выполнить, то есть кнопка перестанет светиться
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            String response = commandProcessor.processCommand(data); // передаем данные обработчику команд
            SendMessage message = new SendMessage(chatId, response); // он формирует сообщение

            try {
                execute(message); // пытаемся отправить
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}