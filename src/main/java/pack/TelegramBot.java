package pack;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

public class TelegramBot extends TelegramLongPollingBot {

    private final CommandProcessor commandProcessor = new CommandProcessor();

    @Override
    public String getBotToken() {
        // Лучше не из env, если вы используете BotConfig — но оставим как у вас
        // ИЛИ замените на BotConfig.token
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String response = commandProcessor.processCommand(text);
            SendMessage message = new SendMessage(String.valueOf(chatId), response);

            if (text.matches("[а-яА-ЯёЁ]+")) {
                InlineKeyboardButton button = new InlineKeyboardButton("Найти синонимы");
                button.setCallbackData("/syn_" + text);
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                markup.setKeyboard(Collections.singletonList(Collections.singletonList(button)));
                message.setReplyMarkup(markup);
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            String response = commandProcessor.processCommand(data);
            SendMessage message = new SendMessage(String.valueOf(chatId), response);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}