package com.practice.chatbot.component.impl;

import com.practice.chatbot.component.AssistBotCommand;
import com.practice.chatbot.component.utils.Buttons;
import com.practice.chatbot.configutation.BotConfig;
import com.practice.chatbot.database.controller.ThemeController;
import com.practice.chatbot.utils.ConvertThemeWithCommand;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class AssistChatBot extends TelegramLongPollingBot implements AssistBotCommand {

    private final BotConfig config;

    public AssistChatBot(BotConfig config) {
        this.config = config;

        try {
            this.execute(new SetMyCommands(BOT_COMMAND_LIST, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        long chatId;
        long userId = 0;
        String userName;
        String receivedMessage;

        //если получено сообщение текстом
        if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userName);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, chatId, userName);
        }
    }

    private void botAnswerUtils(String receiveMessage, long chatId, String userName) {
        switch (receiveMessage) {
            case "/start" -> startBot(chatId, userName);
            case "/theme" -> selectThemeBot(chatId);
            case "/help" -> sendHelpText(chatId);
        }
    }

    private void startBot(long chatId, String userName) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        String themCommand = ConvertThemeWithCommand.convertThem();

        message.setText("Привет, " + userName + "!\n"
                + "Я чат-бот для обеспечения тех.поддержки работников ОАО \"РЖД\"!\n"
                + "Выберите тему вопроса:\n"
                + themCommand);
        //message.setReplyMarkup(Buttons.inlineKeyboardMarkup());

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendHelpText(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(AssistBotCommand.HELP_TEXT);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    //FIXME запросы не работают хз, почему
    private void selectThemeBot(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("EMPTY");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


}
