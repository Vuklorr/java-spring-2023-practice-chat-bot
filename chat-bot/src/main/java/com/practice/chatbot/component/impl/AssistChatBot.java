package com.practice.chatbot.component.impl;

import com.practice.chatbot.component.AssistBotCommand;
import com.practice.chatbot.component.utils.Buttons;
import com.practice.chatbot.configutation.BotConfig;
import com.practice.chatbot.database.controller.SubthemeController;
import com.practice.chatbot.database.controller.ThemeController;
import com.practice.chatbot.database.entity.Subtheme;
import com.practice.chatbot.database.entity.Theme;
import com.practice.chatbot.database.service.implementation.ThemeServiceImpl;
import com.practice.chatbot.database.utils.HibernateSessionFactoryUtil;
import com.practice.chatbot.crud.AnswerCRUD;
import com.practice.chatbot.crud.QuestionCRUD;
import com.practice.chatbot.crud.SubthemeCRUD;
import com.practice.chatbot.utils.*;
import com.practice.chatbot.crud.ThemeCRUD;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class AssistChatBot extends TelegramLongPollingBot implements AssistBotCommand {

    private final BotConfig config;
    private final BotConfig bot;

    private String lastMethod;
    private final long helpChatID = 1854421516;
    private int subthemeID;
    private List<Subtheme> subthemes;

    public AssistChatBot(BotConfig config, BotConfig bot) {
        this.config = config;

        try {
            this.execute(new SetMyCommands(BOT_COMMAND_LIST, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        this.bot = bot;
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
        String[] commandHelper = receiveMessage.split(" ");
        switch (commandHelper[0]) {
            case "/start" -> startBot(chatId, userName);
            case "/theme" -> selectThemeBot(chatId);
            case "/help" -> sendHelpText(chatId);
            case "/1","/2","/3" -> getSubthemeBot(chatId, receiveMessage);
            case "/s1","/s2","/s3","/s4" -> questionWelcome(chatId,receiveMessage);
            case "/ask" -> questionSolver(chatId,receiveMessage);
            case "/r" -> messageAnswer(receiveMessage);

            case "/superuser" -> superUser(chatId);
            case "correctPassword" -> correctPassword(chatId, userName);
            case "unCorrectPassword" -> unCorrectPassword(chatId);

            case "/create" -> create(chatId);
            case "/read" -> read(chatId);
            case "/update" -> update(chatId);
            case "/delete" -> delete(chatId);

            case "/read_themes" -> readTheme(chatId);
            case "/read_subthemes" -> readSubTheme(chatId);
            case "/read_answers" -> readAnswer(chatId);
            case "/read_questions" -> readQuestion(chatId);

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
        subthemes = SubthemeFinder.findSubthemes(uMessage);
        StringBuilder sb = new StringBuilder();
        sb.append("Выберите одну из предложенных подтем: \n");
        for(int i = 0;i<subthemes.size();i++){
            sb.append("/s").append(i+1).append(". ").append(subthemes.get(i)).append("\n");
        }
        message.setText(sb.toString());
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void questionWelcome(long chatID, String uMessage){
        SendMessage message = new SendMessage();
        int subthemeNum = Integer.parseInt(uMessage.substring(2,3));
        subthemeID = SubthemeIDFinder.getID(String.valueOf(subthemes.get(subthemeNum-1)));
        message.setChatId(chatID);
        message.setText("Пожалуйста, введите интересующий Вас вопрос в формате:\n"+"/ask [вопрос]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void questionSolver(long chatID, String uMessage){
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        String uQuestion = uMessage.substring(4);
        String uQuestionConvert = ConvertQuestions.convertQuestion(uQuestion);
        String answer = QuestionSolver.findAnswer(subthemeID,uQuestionConvert);
        if (answer != null){
            StringBuilder sb = new StringBuilder("Ответ на ваш вопрос: \n");
            message.setText(sb.append(answer).toString());
        } else{
            SendMessage logMessage = new SendMessage();
            logMessage.setChatId(-1001854421516L);
            StringBuilder sb = new StringBuilder("Поступил новый необработанный запрос: \n");
            sb.append("ChatID -> "+chatID+"\n").append("Вопрос: ").append(uQuestion);
            logMessage.setText(sb.toString());
            //FIXME Включить если нужны сообщения в группу
            /*try {
                execute(logMessage);
                log.info("GroupMessage sent");
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }*/
            message.setText("К сожалению, на данный момент ответа нет.\n" + "Ваш запрос был отправлен администраторам.");
        }
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void messageAnswer(String uMessage){
        SendMessage message = new SendMessage();
        String[] commands = uMessage.split(" ");
        message.setChatId(commands[1]);
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        StringBuilder sb = new StringBuilder(String.format("[Ответ от администратора {%s}] :\n",date));
        sb.append(commands[2]);
        message.setText(sb.toString());
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
        message.setReplyMarkup(Buttons.inlineKeyboardMarkupReadTable());
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

    private void readTheme(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        String themes = ThemeCRUD.themeRead();

        message.setText("``` " + themes + "```");
        message.setParseMode(ParseMode.MARKDOWNV2);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void readSubTheme(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        String subthemes = SubthemeCRUD.subthemeRead();

        message.setText("``` " + subthemes + "```");
        message.setParseMode(ParseMode.MARKDOWNV2);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void readAnswer(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        String answer = AnswerCRUD.answerRead();

        message.setText("``` " + answer + "```");
        message.setParseMode(ParseMode.MARKDOWNV2);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void readQuestion(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        String question = QuestionCRUD.questionRead();

        message.setText("``` " + question + "```");
        message.setParseMode(ParseMode.MARKDOWNV2);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
