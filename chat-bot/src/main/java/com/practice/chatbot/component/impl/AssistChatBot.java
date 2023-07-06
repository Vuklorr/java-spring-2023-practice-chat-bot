package com.practice.chatbot.component.impl;

import com.practice.chatbot.component.AssistBotCommand;
import com.practice.chatbot.component.utils.Buttons;
import com.practice.chatbot.configutation.BotConfig;
import com.practice.chatbot.database.controller.SubthemeController;
import com.practice.chatbot.database.controller.ThemeController;
import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.service.implementation.ThemeServiceImpl;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import com.practice.chatbot.utils.ConvertThemeWithCommand;
import com.practice.chatbot.utils.SubthemeFinder;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
public class AssistChatBot extends TelegramLongPollingBot implements AssistBotCommand {

    private final BotConfig config;

    private String lastMethod;

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
        long chatId  = 0;
        long userId = 0;
        String userName = "";
        String receivedMessage = "";

        if(lastMethod == null) {
            //если получено сообщение текстом
            if(update.hasMessage()) {
                chatId = update.getMessage().getChatId();
                userId = update.getMessage().getFrom().getId();
                userName = update.getMessage().getFrom().getFirstName();
                receivedMessage = update.getMessage().getText();

                //если нажата одна из кнопок бота
            } else if (update.hasCallbackQuery()) {
                chatId = update.getCallbackQuery().getMessage().getChatId();
                userId = update.getCallbackQuery().getFrom().getId();
                userName = update.getCallbackQuery().getFrom().getFirstName();
                receivedMessage = update.getCallbackQuery().getData();
            }
        } else {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();

            if(lastMethod.equals("superUser")) {
                if (update.getMessage().getText().equals("123")) {
                    receivedMessage = "correctPassword";
                } else {
                    receivedMessage = "unCorrectPassword";
                }
            }
        }
        lastMethod = null;
        botAnswerUtils(receivedMessage, chatId, userName);
    }

    private void botAnswerUtils(String receiveMessage, long chatId, String userName) {
        switch (receiveMessage) {
            case "/start" -> startBot(chatId, userName);
            case "/theme" -> selectThemeBot(chatId);
            case "/help" -> sendHelpText(chatId);
            case "/1","/2","/3" -> getSubthemeBot(chatId,receiveMessage);

            case "/superuser" -> superUser(chatId);
            case "correctPassword" -> correctPassword(chatId, userName);
            case "unCorrectPassword" -> unCorrectPassword(chatId);

            case "/create" -> create(chatId);
            case "/read" -> read(chatId);
            case "/update" -> update(chatId);
            case "/delete" -> delete(chatId);

            case "/subthemes" -> readSubTheme(chatId);
            case "/themes" -> readTheme(chatId);
            case "/answers" -> readAnswer(chatId);
            case "/questions" -> readQuestion(chatId);

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
        message.setParseMode(ParseMode.MARKDOWNV2);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void selectThemeBot(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(ConvertThemeWithCommand.convertThem());
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void getSubthemeBot(long chatID, String uMessage){
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(SubthemeFinder.findSubthemes(uMessage));
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void superUser(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Введите пароль:");
        try {
            execute(message);
            lastMethod = "superUser";
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void correctPassword(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Добро пожаловать, " + userName + "!\n" +
                "Выберите CRUD операцию:\n");
        message.setReplyMarkup(Buttons.inlineKeyboardMarkupCRUD());
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void unCorrectPassword(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Неверный пароль!");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void create(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("1В разработке!");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void read(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите таблицу, которую хотите посмотреть:");
        message.setReplyMarkup(Buttons.inlineKeyboardMarkupTable());
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void update(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("3В разработке!");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void delete(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("4В разработке!");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void readSubTheme(Long chatId) {

    }

    private void readTheme(Long chatId) {

    }


    private void readQuestion(Long chatId) {

    }


    private void readAnswer(Long chatId) {

    }

}
