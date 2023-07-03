package com.practice.chatbot.component.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton CREATE_BUTTON = new InlineKeyboardButton("Создать");
    private static final InlineKeyboardButton READ_BUTTON = new InlineKeyboardButton("Читать");

    private static final InlineKeyboardButton UPDATE_BUTTON = new InlineKeyboardButton("Обновить");

    private static final InlineKeyboardButton DELETE_BUTTON = new InlineKeyboardButton("Удалить");

    private static final InlineKeyboardButton SUB_THEME_TABLE_BUTTON = new InlineKeyboardButton("Подтема");
    private static final InlineKeyboardButton THEME_TABLE_BUTTON = new InlineKeyboardButton("Тема");
    private static final InlineKeyboardButton ANSWER_TABLE_BUTTON = new InlineKeyboardButton("Ответ");
    private static final InlineKeyboardButton QUESTION_TABLE_BUTTON = new InlineKeyboardButton("Вопрос");

    public static InlineKeyboardMarkup inlineKeyboardMarkupCRUD() {
        CREATE_BUTTON.setCallbackData("/create");
        READ_BUTTON.setCallbackData("/read");
        UPDATE_BUTTON.setCallbackData("/update");
        DELETE_BUTTON.setCallbackData("/delete");

        return getInlineKeyboardMarkup(CREATE_BUTTON, READ_BUTTON, UPDATE_BUTTON, DELETE_BUTTON);
    }

    public static InlineKeyboardMarkup inlineKeyboardMarkupTable() {
        SUB_THEME_TABLE_BUTTON.setCallbackData("/subthemes");
        THEME_TABLE_BUTTON.setCallbackData("/themes");
        ANSWER_TABLE_BUTTON.setCallbackData("/answers");
        QUESTION_TABLE_BUTTON.setCallbackData("/questions");

        return getInlineKeyboardMarkup(SUB_THEME_TABLE_BUTTON, THEME_TABLE_BUTTON, ANSWER_TABLE_BUTTON, QUESTION_TABLE_BUTTON);
    }

    private static InlineKeyboardMarkup getInlineKeyboardMarkup(InlineKeyboardButton subThemeTableButton, InlineKeyboardButton themeTableButton, InlineKeyboardButton answerTableButton, InlineKeyboardButton questionTableButton) {
        List<InlineKeyboardButton> firstRowInline = List.of(subThemeTableButton, themeTableButton);
        List<InlineKeyboardButton> secondRowInline = List.of(answerTableButton, questionTableButton);
        List<List<InlineKeyboardButton>> rowsInline = List.of(firstRowInline, secondRowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }
}
