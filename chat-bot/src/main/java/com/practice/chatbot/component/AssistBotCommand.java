package com.practice.chatbot.component;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface AssistBotCommand {
    List<BotCommand> BOT_COMMAND_LIST = List.of(
            new BotCommand("/start", "запуск бота"),
            new BotCommand("/help", "информация о боте")
    );

    String HELP_TEXT = """
            Это чат-бот для обеспечения тех.поддержки работников РЖД.
            Ниже представлены команды ,которыми вы можете воспользоваться и описание для них:

            /start - запуск бота
            /theme - тема вопроса
            /help - информация о боте
            """;
}
