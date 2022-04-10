package com.company.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class WelcomeButtonUtil {
    public static ReplyKeyboardMarkup menuButton(){
        KeyboardButton button1= KeyButtonUtil.keyButton("Menu","\uD83C\uDF74");
        KeyboardButton button2= KeyButtonUtil.keyButton("My orders","\uD83D\uDECD");
        KeyboardButton button3= KeyButtonUtil.keyButton("Leave feedback",":writing_hand:");
        KeyboardButton button4= KeyButtonUtil.keyButton("Settings","⚙");
        KeyboardRow row1=KeyButtonUtil.keyRow(button1,button2);
        KeyboardRow row2=KeyButtonUtil.keyRow(button3,button4);
        List<KeyboardRow> rowList=KeyButtonUtil.keyboardRowList(row1,row2);
        return KeyButtonUtil.replyKeyboardMarkup(rowList);
    }

    public static ReplyKeyboardMarkup getLocationButton() {
        KeyboardButton button1 = KeyButtonUtil.keyButton("Send lacation","\uD83D\uDCCD");
        button1.setRequestLocation(true);
        KeyboardButton button3 = KeyButtonUtil.keyButton("Back",":arrow_left:");
        KeyboardRow keyboardRow1 = KeyButtonUtil.keyRow(button1);
        KeyboardRow keyboardRow2 = KeyButtonUtil.keyRow(button3);
        List<KeyboardRow> keyboardRowList = KeyButtonUtil.keyboardRowList(keyboardRow1, keyboardRow2);
        return KeyButtonUtil.replyKeyboardMarkup(keyboardRowList);
    }

    public static ReplyKeyboardMarkup afterLocationMenu(){
        KeyboardButton button1= KeyButtonUtil.keyButton("Yes","✅");
        KeyboardButton button2= KeyButtonUtil.keyButton("No",":x:");
        KeyboardButton button3= KeyButtonUtil.keyButton("Back",":arrow_left:");
        KeyboardRow row1=KeyButtonUtil.keyRow(button2,button1);
        KeyboardRow row2=KeyButtonUtil.keyRow(button3);
        List<KeyboardRow>rowList=KeyButtonUtil.keyboardRowList(row1,row2);
        return KeyButtonUtil.replyKeyboardMarkup(rowList);
    }
    public static ReplyKeyboardMarkup backButton(){
        KeyboardButton button= KeyButtonUtil.keyButton("Back",":arrow_left:");
        KeyboardRow row=KeyButtonUtil.keyRow(button);
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row));
    }
}
