package com.practice.chatbot.component.impl;

import com.practice.chatbot.component.AssistBotCommand;
import com.practice.chatbot.component.utils.Buttons;
import com.practice.chatbot.configutation.BotConfig;
import com.practice.chatbot.database.entity.Subtheme;
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

            case "/create_themes" -> dataCreateTheme(chatId);
            case "/ct" -> createTheme(chatId, receiveMessage);
            case "/create_subthemes" -> dataCreateSubTheme(chatId);
            case "/cs" -> createSubTheme(chatId, receiveMessage);
            case "/create_answers" -> dataCreateAnswer(chatId);
            case "/ca" -> createAnswer(chatId, receiveMessage);
            case "/create_questions" -> dataCreateQuestion(chatId);
            case "/cq" -> createQuestion(chatId, receiveMessage);

            case "/read_themes" -> readTheme(chatId);
            case "/read_subthemes" -> readSubTheme(chatId);
            case "/read_answers" -> readAnswer(chatId);
            case "/read_questions" -> readQuestion(chatId);

            case "/update_themes" -> dataUpdateTheme(chatId);
            case "/ut" -> updateTheme(chatId, receiveMessage);
            case "/update_subthemes" -> dataUpdateSubTheme(chatId);
            case "/us" -> updateSubTheme(chatId, receiveMessage);
            case "/update_answers" -> dataUpdateAnswer(chatId);
            case "/ua" -> updateAnswer(chatId, receiveMessage);
            case "/update_questions" -> dataUpdateQuestion(chatId);
            case "/uq" -> updateQuestion(chatId, receiveMessage);

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
        message.setText("Выберите таблицу, в которой хотите создать новую запись:");
        message.setReplyMarkup(Buttons.inlineKeyboardMarkupCreateTable());

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
        message.setText("Выберите таблицу, в которой хотите обновить запись:");
        message.setReplyMarkup(Buttons.inlineKeyboardMarkupUpdateTable());
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

    private void dataCreateTheme(Long chatId) {
        readTheme(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        message.setText("Введите новую запись без ID в формате: /ct [Тема]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void createTheme(Long chatId, String uMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String controllerMessage = ThemeCRUD.themeCreate(uMessage);

        message.setText(controllerMessage);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void dataCreateSubTheme(Long chatId) {
        readSubTheme(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        message.setText("Введите новую запись без ID в формате: /cs [Подтема ID_темы]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void createSubTheme(Long chatId, String uMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String controllerMessage = SubthemeCRUD.subthemeCreate(uMessage);

        message.setText(controllerMessage);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void dataCreateAnswer(Long chatId) {
        readAnswer(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        message.setText("Введите новую запись без ID в формате: /ca [Ответ]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void createAnswer(Long chatId, String uMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String controllerMessage = AnswerCRUD.answerCreate(uMessage);

        message.setText(controllerMessage);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void dataCreateQuestion(Long chatId) {
        readQuestion(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        message.setText("Введите новую запись без ID в формате: /cq [ID_подтемы Вопрос ID_ответа]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void createQuestion(Long chatId, String uMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String controllerMessage = QuestionCRUD.questionCreate(uMessage);

        message.setText(controllerMessage);
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
    private void dataUpdateTheme(Long chatId) {
        readTheme(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        message.setText("Введите измененную запись c ID в формате: /ut [ID_старой_темы Новая_тема]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void updateTheme(Long chatId, String uMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String controllerMessage = ThemeCRUD.themeUpdate(uMessage);
        message.setText(controllerMessage);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void dataUpdateSubTheme(Long chatId) {
        readSubTheme(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        message.setText("Введите измененную запись c ID в формате: /us [ID_старой_подтемы Новая_подтема ID_темы]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void updateSubTheme(Long chatId, String uMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String controllerMessage = SubthemeCRUD.subthemeUpdate(uMessage);

        message.setText(controllerMessage);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void dataUpdateAnswer(Long chatId) {
        readAnswer(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        message.setText("Введите измененную запись c ID в формате: /ua [ID_старого_ответа Новый_ответ]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void updateAnswer(Long chatId, String uMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String controllerMessage = AnswerCRUD.answerUpdate(uMessage);

        message.setText(controllerMessage);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void dataUpdateQuestion(Long chatId) {
        readQuestion(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Введите измененную запись c ID в формате: /uq [ID_старого_вопроса ID_подтемы Новый_вопрос ID_ответа]");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void updateQuestion(Long chatId, String uMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String controllerMessage = QuestionCRUD.questionUpdate(uMessage);

        message.setText(controllerMessage);
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
