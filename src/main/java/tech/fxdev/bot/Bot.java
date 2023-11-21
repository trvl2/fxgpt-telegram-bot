package tech.fxdev.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import tech.fxdev.bot.config.BotConfig;

@Component
public class Bot extends TelegramLongPollingBot {

    final BotConfig config;

    public Bot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageFromUser = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch(messageFromUser) {
                case "/start":
                    startCommandReceived(chatId);
                    break;
                default:
                    repeatMessage(chatId, messageFromUser);
                    break;
            }
        }
    }

    private void startCommandReceived(long chatId) {
        String answer = "Hi, welcome to FxGpt bot!";

        sendMessage(chatId, answer);
    }

    private void repeatMessage(long chatId, String messageFromUser) {
        sendMessage(chatId, messageFromUser);
    }

    private void sendMessage(long chatId, String messageToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageToSend);

        try {
            execute(message);

        } catch (TelegramApiException e) {
            // TODO logs

        }
    }
}
