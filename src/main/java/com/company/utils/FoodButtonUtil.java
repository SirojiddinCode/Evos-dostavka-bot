package com.company.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class FoodButtonUtil {
    public static ReplyKeyboardMarkup getFoodMenu() {
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Set"), KeyButtonUtil.keyButton("Lavash"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Shaurma"), KeyButtonUtil.keyButton("Donar"));
        KeyboardRow row3 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Burger"), KeyButtonUtil.keyButton("Xot-dog"));
        KeyboardRow row4 = KeyButtonUtil.keyRow( KeyButtonUtil.keyButton("Drinks"),KeyButtonUtil.keyButton("Garnir"));
        KeyboardRow row6 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2, row3, row4, row6));
    }
    public static ReplyKeyboardMarkup setMenu(){
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Kids COMBO"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2));
    }
    public static ReplyKeyboardMarkup lavashMenu(){
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("FITTER"),
                KeyButtonUtil.keyButton("Beef lavash kalampir"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Chicken lavash Kalampir"),
                KeyButtonUtil.keyButton("Beef lavash"));
        KeyboardRow row3 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Chicken lavash"),
                KeyButtonUtil.keyButton("Chicken lavash with cheese"));
        KeyboardRow row4 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Beef lavash with cheese"));
        KeyboardRow row5 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2, row3, row4, row5));
    }
    public static ReplyKeyboardMarkup donarMenu() {
        KeyboardButton button1 = KeyButtonUtil.keyButton("Beef donar");
        KeyboardButton button2 = KeyButtonUtil.keyButton("Chicken donar");
        KeyboardButton button3 = KeyButtonUtil.keyButton("Back", ":arrow_left:");
        KeyboardRow keyboardRow1 = KeyButtonUtil.keyRow(button1, button2);
        KeyboardRow keyboardRow2 = KeyButtonUtil.keyRow(button3);


        List<KeyboardRow> keyboardRowList = KeyButtonUtil.keyboardRowList(keyboardRow1, keyboardRow2);

        return KeyButtonUtil.replyKeyboardMarkup(keyboardRowList);
    }
    public static ReplyKeyboardMarkup shaurmaMenu(){
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Shawarma beef"),
                KeyButtonUtil.keyButton("Chicken shawarma"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Spicy chicken shawarma"),
                KeyButtonUtil.keyButton("Beef Shawarma Spicy"));
        KeyboardRow row3 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2, row3));
    }
    public static  ReplyKeyboardMarkup burgerMenu(){
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Hamburger"),
                KeyButtonUtil.keyButton("Doubleburger"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("DoubleCheeseburger"),
                KeyButtonUtil.keyButton("Cheeseburger"));
        KeyboardRow row3 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2, row3));
    }
    public static  ReplyKeyboardMarkup xotDogMenu(){
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Hot Dog Baguette"),
                KeyButtonUtil.keyButton("Hot Dog Baguette Double"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Classic hot dog"),
                KeyButtonUtil.keyButton("Hot Dog Kids"));
        KeyboardRow row3 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2, row3));
    }
    public static ReplyKeyboardMarkup drinksMenu(){
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Pepsi 0.5"),
                KeyButtonUtil.keyButton("Water 0.5"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("baby juice"),
                KeyButtonUtil.keyButton("Coffee 3 in 1"));
        KeyboardRow row3 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Coffee black"),
                KeyButtonUtil.keyButton("Pepsi 1.5"));
        KeyboardRow row4 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Juice Bliss"),
                KeyButtonUtil.keyButton("pepsi spill"));
        KeyboardRow row5 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2, row3,row4,row5));
    }
    public static ReplyKeyboardMarkup garnirMenu(){
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Mayonnaise"),
                KeyButtonUtil.keyButton("Ketchup"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Potatoes"),
                KeyButtonUtil.keyButton("Rice"));
        KeyboardRow row3 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Salad"),
                KeyButtonUtil.keyButton("Chicken fillet"));
        KeyboardRow row4 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Cheese sauce"),
                KeyButtonUtil.keyButton("Potato fries"));
        KeyboardRow row5 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Garlic Sause"),
                KeyButtonUtil.keyButton("Chili sauce"));

        KeyboardRow row6 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2, row3,row4,row5,row6));
    }

}
