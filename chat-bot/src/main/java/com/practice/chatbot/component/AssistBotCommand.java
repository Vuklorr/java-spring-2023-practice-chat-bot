package com.practice.chatbot.component;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface AssistBotCommand {
    List<BotCommand> BOT_COMMAND_LIST = List.of(
            new BotCommand("/start", "запуск бота"),
            new BotCommand("/help", "список команд бота")
    );

    String HELP_TEXT = """
            Это чат\\-бот для обеспечения техподдержки работников РЖД\\.
            Ниже представлены команды которыми вы можете воспользоваться и описание для них\\:

            /start \\- запуск бот
            ||/superuser \\- команда для разработчиков \\(не нажимать\\)||
            """;
}
