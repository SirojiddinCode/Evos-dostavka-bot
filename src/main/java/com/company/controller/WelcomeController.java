package com.company.controller;
import com.company.entity.SendMessageEntity;
import com.company.enums.SendMsgType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.company.container.Componentconteiner.foodMenuController;
import static com.company.container.Componentconteiner.userCurrentMenu;
import static com.company.utils.WelcomeButtonUtil.*;

public class WelcomeController {
    public SendMessageEntity start(Message message) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        String text = message.getText();
        User user = message.getFrom();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        switch (text) {
            case "/start":
                if (!userCurrentMenu.isEmpty()) {
                    userCurrentMenu.remove(message.getChatId().toString());
                }
                sendMessage.setText("Welcome " + user.getFirstName());
                sendMessage.setReplyMarkup(menuButton());
                sendMessageEntity.setSendMessage(sendMessage);
                sendMessageEntity.setType(SendMsgType.Message);
                return sendMessageEntity;
        }
        return null;
    }

    public SendMessageEntity menu(Message message) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        String text = message.getText();
        String chatId=message.getChatId().toString();
        User user = message.getFrom();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        userCurrentMenu.put(user.getId().toString(), text);
        switch (text) {
            case "\uD83C\uDF74 Menu":
                sendMessage.setText("Send 📍 geolocation or phone number or select a delivery address");
                sendMessage.setReplyMarkup(getLocationButton());
                sendMessageEntity.setSendMessage(sendMessage);
                sendMessageEntity.setType(SendMsgType.Message);
                break;
            case "✍ Leave feedback":
                sendMessage.setText("Submit your feedback");
                sendMessage.setReplyMarkup(backButton());
                sendMessageEntity.setSendMessage(sendMessage);
                sendMessageEntity.setType(SendMsgType.Message);
                break;
            case "⚙ Settings":
                sendMessage.setText("Settings temporarily unavailable");
                sendMessage.setReplyMarkup(backButton());
                sendMessageEntity.setSendMessage(sendMessage);
                sendMessageEntity.setType(SendMsgType.Message);
                break;
        }
        return sendMessageEntity;
    }

    public SendMessageEntity sendlocation(Message message) {
        User user = message.getFrom();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        if (message.hasLocation()) {
            sendMessage.setText("Relly this is your addess");
            sendMessage.setReplyMarkup(afterLocationMenu());
            sendMessageEntity.setSendMessage(sendMessage);
            sendMessageEntity.setType(SendMsgType.Message);
            return sendMessageEntity;
        }
        return null;
    }

    public SendMessageEntity confirmation(Message message) {
        String text = message.getText();
        if (text.equals("✅ Yes")) {
            message.setText("Send location");
            return foodMenuController.start(message);
        } else if (text.equals("❌ No")) {
            message.setText("\uD83C\uDF74 Menu");
            return menu(message);
        }
        return null;
    }


}
