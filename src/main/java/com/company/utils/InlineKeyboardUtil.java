package com.company.utils;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineKeyboardUtil {

    public static InlineKeyboardButton button(String name, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton(name);
        button.setCallbackData(callBackData);
        return button;
    }

    public static InlineKeyboardButton button(String name, String callBackData, String emoji) {
        String emojiText = EmojiParser.parseToUnicode(emoji + " " + name);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(emojiText);
        button.setCallbackData(callBackData);
        return button;
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... buttons) {
        return new LinkedList<>(Arrays.asList(buttons));
    }

    public static List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton>... rows) {
        return new ArrayList<>(Arrays.asList(rows));
    }

    public static InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> rowList) {
        return new InlineKeyboardMarkup(rowList);
    }
}
