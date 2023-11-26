package tech.fxdev.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import tech.fxdev.bot.config.BotConfig;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    final BotConfig config;

    public Bot(BotConfig config) {

        this.config = config;

        List<BotCommand> listofCommands = new ArrayList<>();

        listofCommands.add(new BotCommand("/start", "Dashboard"));
        listofCommands.add(new BotCommand("/newthread", "Create a new thread"));
        listofCommands.add(new BotCommand("/allthreads", "List of all your threads"));
        listofCommands.add(new BotCommand("/subscription", "Manage your subscription"));
        listofCommands.add(new BotCommand("/settings", "Manage your settings"));
        listofCommands.add(new BotCommand("/help", "Help me"));

        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));

        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());

        }

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
                    log.info("Received /start from User [" + update.getMessage().getFrom().getId() + "]");
                    startCommandReceived(chatId);
                    break;

                case "/newthread":
                    log.info("Received /newthread from User [" + update.getMessage().getFrom().getId() + "]");
                    newthreadCommandReceived(chatId);
                    break;

                case "/allthreads":
                    log.info("Received /allthreads from User [" + update.getMessage().getFrom().getId() + "]");
                    allthreadsCommandReceived(chatId);
                    break;

                case "/subscription":
                    log.info("Received /subscription from User [" + update.getMessage().getFrom().getId() + "]");
                    subscriptionCommandReceived(chatId);
                    break;

                case "/settings":
                    log.info("Received /settings from User [" + update.getMessage().getFrom().getId() + "]");
                    settingsCommandReceived(chatId);
                    break;

                case "/help":
                    log.info("Received /help from User [" + update.getMessage().getFrom().getId() + "]");
                    helpCommandReceived(chatId);
                    break;

                default:
                    log.info("Received message from User [" + update.getMessage().getFrom().getId() + "] [" + update.getMessage().getText() + "]");
                    repeatMessage(chatId, messageFromUser);
                    break;
            }
        }
    }

    private void startCommandReceived(long chatId) {
        String answer = "Hi, welcome to FxGpt bot!";

        sendMessage(chatId, answer);
    }

    private void newthreadCommandReceived(long chatId) {
        String answer = "Threads section will be implemented in the future";

        sendMessage(chatId, answer);
    }

    private void allthreadsCommandReceived(long chatId) {
        String answer = "Chat section will be implemented in the future";

        sendMessage(chatId, answer);
    }

    private void subscriptionCommandReceived(long chatId) {
        String answer = "Subscription section will be implemented in the future";

        sendMessage(chatId, answer);
    }

    private void settingsCommandReceived(long chatId) {
        String answer = "Settings section will be implemented in the future";

        sendMessage(chatId, answer);
    }

    private void helpCommandReceived(long chatId) {
        String answer =  "You can execute commands from the main menu on the left or by typing a command:\n\n";

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
            log.error("Telegram API error while executing response message: " + e.getMessage());

        }
    }
}
