package com.company.controller;

import com.company.container.Componentconteiner;
import com.company.entity.Food;
import com.company.entity.SendMessageEntity;
import com.company.enums.SendMsgType;
import com.company.repository.FoodsRepository;
import com.company.repository.Price;
import com.company.utils.FoodButtonUtil;
import com.company.utils.InlineKeyboardUtil;
import com.company.utils.KeyButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.company.container.Componentconteiner.*;

public class FoodsController {
    private Long foodId = 10000L;
    HashMap<String, Food> foodMap = new HashMap<>();

    public SendMessageEntity Menu(Message message, String menu) {
        String text = message.getText();
      //  Componentconteiner.userCurrentMenu.put(message.getChatId().toString(), text);
        SendMessageEntity sendMessage = new SendMessageEntity();
        sendMessage.setSendPhoto(foodMenu(message, menu));
        sendMessage.setType(SendMsgType.Photo);
        return sendMessage;
    }

    public SendPhoto foodMenu(Message message, String menu) {
        String text = message.getText();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        InputFile inputFile = new InputFile(new File("src/main/resources/" + menu + "/" + text + ".png"));
        String price = Price.getPrice(text);
        Food food = new Food(foodId++,message.getChatId().toString(), text, 1, price);
        foodMap.put(message.getChatId().toString(), food);
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setReplyMarkup(calculateButton("1"));
        sendPhoto.setCaption(text + " Price's: " + price + " sum");
        return sendPhoto;
    }

    public SendMessageEntity calculate(CallbackQuery callbackQuery) {
        String[] text = callbackQuery.getData().split("/");
        String chatid = callbackQuery.getFrom().getId().toString();
        SendMessageEntity sendMsg = new SendMessageEntity();
        Food food = foodMap.get(chatid);
       if (text[2].equals("cart")) {
           FoodsRepository.addFoodToFile(foodMap.get(chatid));
            EditMessageReplyMarkup editMessage=new EditMessageReplyMarkup();
            editMessage.setChatId(chatid);
           editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
           callbackQuery.getMessage().setDeleteChatPhoto(true);
           editMessage.setInlineMessageId(callbackQuery.getInlineMessageId());
           SendMessage sendMessage = new SendMessage();
           sendMessage.setText("Choose one of the following");
           sendMessage.setChatId(chatid);
           sendMessage.setReplyMarkup(finish());
           sendMsg.setSendMessage(sendMessage);
           sendMsg.setEditMessageReplyMarkup(editMessage);
           sendMsg.setType(SendMsgType.MessageAndEditMessageReply);
        } else if (text[2].equals("+")) {
            EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
            editMessage.setChatId(chatid);
            food.setCount(food.getCount() + 1);
            editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
            editMessage.setInlineMessageId(callbackQuery.getInlineMessageId());
            editMessage.setReplyMarkup(calculateButton(String.valueOf(food.getCount())));
            sendMsg.setEditMessageReplyMarkup(editMessage);
            sendMsg.setType(SendMsgType.EditMessageReply);
        } else if (text[2].equals("-") && food.getCount() >= 0) {
            EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
            editMessage.setChatId(chatid);
            food.setCount(food.getCount() - 1);
            editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
            editMessage.setInlineMessageId(callbackQuery.getInlineMessageId());
            editMessage.setReplyMarkup(calculateButton(String.valueOf(food.getCount())));
            sendMsg.setEditMessageReplyMarkup(editMessage);
            sendMsg.setType(SendMsgType.EditMessageReply);
        }
        return sendMsg;
    }


    /*public InlineKeyboardMarkup checking() {
        InlineKeyboardButton button = InlineKeyboardUtil.button("add to basket", "/calculate/cart");
        return InlineKeyboardUtil.keyboard(InlineKeyboardUtil.rowList(InlineKeyboardUtil.row(button)));
    }*/

    public InlineKeyboardMarkup calculateButton(String count) {
        InlineKeyboardButton button1 = InlineKeyboardUtil.button("+", "/calculate/+");
        InlineKeyboardButton button2 = InlineKeyboardUtil.button(count, "/count");
        InlineKeyboardButton button3 = InlineKeyboardUtil.button("-", "/calculate/-");
        InlineKeyboardButton button4 = InlineKeyboardUtil.button("add to basket",
                "/calculate/cart","\uD83D\uDCE5");
        List<InlineKeyboardButton> row1 = InlineKeyboardUtil.row(button3, button2, button1);
        List<InlineKeyboardButton> row2 = InlineKeyboardUtil.row(button4);
        return InlineKeyboardUtil.keyboard(InlineKeyboardUtil.rowList(row1, row2));
    }

    public ReplyKeyboardMarkup finish() {
        KeyboardRow row1 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Basket", "\uD83D\uDCE5"));
        KeyboardRow row2 = KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back", ":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2));
    }

    public SendMessageEntity basket(Message message) {
        SendMessageEntity sendMessageEntity=new SendMessageEntity();
        SendMessage sendMessage=new SendMessage();
        String chatId = message.getChatId().toString();
        List<Food> orders = FoodsRepository.getfoodListByChatid(chatId);
        String s;
        if (orders.size()>0) {
           s = "In the basket: \n";
            double sum = 0d;
            for (Food order : orders) {
                s = s + order.getCount() + " x " + order.getFoodName() + "\n";
                sum += (order.getCount() * Double.parseDouble(order.getPrice()));
            }
            s=s+"\n";
            s = s + "Products: " + sum + " sum"+"\n"+ "Delivery:"+delivery+" sum"+"\n"+ "Total:"+(sum+delivery)+" sum";
            sendMessage.setText(s);
            sendMessage.setReplyMarkup(basketButton(orders));
        }else {
            s="Your basket is empty";
            sendMessage.setText(s);
            sendMessage.setReplyMarkup(FoodButtonUtil.getFoodMenu());
        }
        sendMessage.setChatId(chatId);
        sendMessageEntity.setSendMessage(sendMessage);
        sendMessageEntity.setType(SendMsgType.Message);
        return sendMessageEntity;
    }

    public SendMessageEntity basketEdit(Message message) {
        SendMessageEntity sendMessageEntity=new SendMessageEntity();
        EditMessageText editMessage=new EditMessageText();
        editMessage.setMessageId(message.getMessageId());
        String chatId = message.getChatId().toString();
        editMessage.setChatId(chatId);
        List<Food> orders = FoodsRepository.getfoodListByChatid(chatId);
        String s;
        if (orders.size()>0) {
            s = "In the basket: \n";
            double sum = 0d;
            for (Food order : orders) {
                s = s + order.getCount() + " x " + order.getFoodName() + "\n";
                sum += (order.getCount() * Double.parseDouble(order.getPrice()));
            }
            s=s+"\n";
            s = s + "Products: " + sum + " sum"+"\n"+ "Delivery:"+delivery+" sum"+"\n"+ "Total:"+(sum+delivery)+" sum";
            editMessage.setText(s);
            editMessage.setReplyMarkup(basketButton(orders));
            sendMessageEntity.setType(SendMsgType.Edit);
        }else {
            s="Your basket is empty";
            editMessage.setText(s);
            sendMessageEntity.setSendMessage(foodMenuController.start(message).getSendMessage());
            sendMessageEntity.setType(SendMsgType.MessageAndEdit);
        }
        sendMessageEntity.setEditMessage(editMessage);
        return sendMessageEntity;
    }

    private InlineKeyboardMarkup basketButton(List<Food> orders) {
        List<List<InlineKeyboardButton>>rowlist=new LinkedList<>();
        List<InlineKeyboardButton> row1=InlineKeyboardUtil.row(InlineKeyboardUtil.button(" Clear Basket",
                "delete/clear","\uD83D\uDED2"),InlineKeyboardUtil.button(" Make an order",
                "delete/makeOrder","\uD83D\uDE96"));
        List<InlineKeyboardButton> row2=InlineKeyboardUtil.row(InlineKeyboardUtil.button("Back",
                "delete/back",":arrow_left:"));
        rowlist.add(row1);
        rowlist.add(row2);
        for (Food order : orders) {
        List<InlineKeyboardButton> row=InlineKeyboardUtil.row(InlineKeyboardUtil.button(order.getFoodName(),
                "delete/"+order.getFoodName(),":x:"));
        rowlist.add(row);
        }
        return InlineKeyboardUtil.keyboard(rowlist);
    }

    public SendMessageEntity basketRealization(CallbackQuery callbackQuery){
        String text=callbackQuery.getData();
        String chatId=callbackQuery.getFrom().getId().toString();
        String arr[]=text.split("/");
        SendMessageEntity sendMessageEntity=new SendMessageEntity();
        switch (arr[1]){
            case "clear":
                FoodsRepository.clear(chatId);
                sendMessageEntity.setSendMessage(foodMenuController.start(callbackQuery.getMessage()).getSendMessage());;
                EditMessageText editMessage=new EditMessageText();
                editMessage.setText("cleared \n Select the button");
                editMessage.setChatId(chatId);
                editMessage.setMessageId(callbackQuery.getMessage().getMessageId());
                sendMessageEntity.setType(SendMsgType.MessageAndEdit);
                sendMessageEntity.setEditMessage(editMessage);
                break;
            case "makeOrder"://Telefon raqamingizni quyidagi formatda yuboring yoki kiriting: +998 ** *** ** **
                userCurrentMenu.put(chatId,"make order");
                SendMessage sendMessage=new SendMessage();
                sendMessage.setText("Send or enter your phone number in the following format: +998 ** *** ** **");
                sendMessage.setChatId(chatId);
                sendMessage.setReplyMarkup(makeOrderButton());
                sendMessageEntity.setSendMessage(sendMessage);
                sendMessageEntity.setType(SendMsgType.Message);
                break;
            case "back":
                Message message=callbackQuery.getMessage();
                message.setText("Send location");
               sendMessageEntity= Componentconteiner.foodMenuController.start(message);
               EditMessageText editMessageText=new EditMessageText();
               editMessageText.setMessageId(message.getMessageId());
               editMessageText.setText("Select a category");
               editMessageText.setChatId(chatId);
               sendMessageEntity.setEditMessage(editMessageText);
               sendMessageEntity.setType(SendMsgType.MessageAndEdit);
               break;
            default:
                FoodsRepository.deleteFoodInFile(chatId,arr[1]);
                sendMessageEntity=basketEdit(callbackQuery.getMessage());
                break;

        }
        return sendMessageEntity;
    }

    public ReplyKeyboardMarkup makeOrderButton(){
        KeyboardButton button1 = KeyButtonUtil.keyButton("Send phone","\uD83D\uDCDE");
        button1.setRequestContact(true);
        KeyboardButton button2 = KeyButtonUtil.keyButton("Back",":arrow_left:");
        KeyboardRow row1=KeyButtonUtil.keyRow(button1);
        KeyboardRow row2=KeyButtonUtil.keyRow(button2);
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1, row2));
    }
  /*  public CodeMessage createEditMessageReplyMarkup (String chatId, Integer messageId, String inlineMessageId, int count) {
        EditMessageReplyMarkup new_message = new EditMessageReplyMarkup();
        new_message.setChatId(chatId);
        new_message.setMessageId(messageId);
        new_message.setInlineMessageId(inlineMessageId);
        new_message.setReplyMarkup(SetController.defineSetCountKeyboard(count));
        CodeMessage codeMessage = new CodeMessage();
        codeMessage.setEditMessageReplyMarkup(new_message);
        codeMessage.setMessageType(MessageType.EditMessageMarkup);
        return codeMessage;
    }*/
}
