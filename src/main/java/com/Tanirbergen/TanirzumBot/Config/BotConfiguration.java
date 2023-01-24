package com.Tanirbergen.TanirzumBot.Config;

import com.Tanirbergen.TanirzumBot.Bot;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotConfiguration extends TelegramLongPollingBot {

    private final Bot bot;


    private static final String HELP_MESSAGE =
            EmojiParser.parseToUnicode("Этот бот поможет вам получить информацию про Танирберген :raised_hands:\n\n" +
                    "Что бы работать с ботом нажмите на /start \n\n" +
                    "Для получение других информации нажмите на определенные кнопки");

    private static final String DEFAULT_MESSAGE =
            EmojiParser.parseToUnicode(" Вы не правильно вели команду" + " :raised_hands:");

    private static final String URL_GITHUB =
            EmojiParser.parseToUnicode("Здесь вы можете ознакомиться работами Танирбергена :computer:\n\n" +
                    "https://github.com/Tanirzum");

    private static final String URL_LEETCODE =
            EmojiParser.parseToUnicode("Решение задач на Leetcode :sweat_smile:\n\n" +
                    "https://leetcode.com/Tanirzumm");

    private static final String URL_SOCIAL =
            EmojiParser.parseToUnicode("Это соц сети Танирбергена :relaxed:\n\n" +
                    "Linkedin: http://linkedin.com/in/tanirbergen-zamanbek-0a1392205\n\n" +
                    "Whatsapp: https://wa.me/87478626825\n\n" +
                    "Instagram: https://instagram.com/tanirzum?igshid=YmMyMTA2M2Y=");

    private static final String URL_RESUME =
            EmojiParser.parseToUnicode("Резюме Танирбергена :sweat_smile:\n\n" + "https://drive.google.com/file/d/1HWd8ixvteYyw9ocEsH9E3I5xjs87q8OS/view?usp=share_link");


    @Autowired
    public BotConfiguration(Bot bot) {
        this.bot = bot;
        List<BotCommand> listCommandTelegram = new ArrayList<>();
        listCommandTelegram.add(new BotCommand("/start", "Поможет начать бот"));
        listCommandTelegram.add(new BotCommand("/help", "Как пользоваться ботом"));
        try {
            execute(new SetMyCommands(listCommandTelegram, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException te) {
            te.getMessage();
        }
    }

    @Override
    public String getBotUsername() {
        return bot.getUsername();
    }

    @Override
    public String getBotToken() {
        return bot.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();


            switch (message) {
                case "/start":
                    startCommandMessage(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    sendCommandMessage(chatId, HELP_MESSAGE);
                    break;
                case "Github":
                    sendCommandMessage(chatId, URL_GITHUB);
                    break;
                case "Leetcode":
                    sendCommandMessage(chatId, URL_LEETCODE);
                    break;
                case "Соц Сети":
                    sendCommandMessage(chatId, URL_SOCIAL);
                    break;
                case "Резюме":
                    sendCommandMessage(chatId, URL_RESUME);
                    break;
                default:
                    sendCommandMessage(chatId,
                            update.getMessage().getChat().getFirstName() + DEFAULT_MESSAGE);
            }
        }
    }

    private void startCommandMessage(Long id, String username) {
        String message = EmojiParser.parseToUnicode("Добро пожаловать, " + username +
                " здесь вы можете получать информацию про Танирберген " + ":relaxed:");

        sendCommandMessage(id, message);
    }

    private void sendCommandMessage(Long id, String textToSend) {

        SendMessage message = new SendMessage();

        message.setChatId(String.valueOf(id));
        message.setText(textToSend);
        replyKeyboardMarkup(message);
    }

    public void replyKeyboardMarkup(SendMessage message) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> listKeyboard = new ArrayList<>();

        KeyboardRow about = new KeyboardRow();
        about.add("Резюме");
        about.add("Соц Сети");
        listKeyboard.add(about);

        KeyboardRow social = new KeyboardRow();
        social.add("Github");
        social.add("Leetcode");
        listKeyboard.add(social);

        replyKeyboardMarkup.setKeyboard(listKeyboard);

        message.setReplyMarkup(replyKeyboardMarkup);


        try {
            execute(message);
        } catch (TelegramApiException te) {
            te.getMessage();
        }
    }
}
