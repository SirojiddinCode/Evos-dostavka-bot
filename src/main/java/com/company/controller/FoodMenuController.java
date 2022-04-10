package com.company.controller;

import com.company.entity.Food;
import com.company.entity.SendMessageEntity;
import com.company.enums.OrderStatus;
import com.company.enums.PaymentType;
import com.company.enums.SendMsgType;
import com.company.repository.FoodsRepository;
import com.company.utils.FoodButtonUtil;
import com.company.utils.KeyButtonUtil;
import com.company.utils.WelcomeButtonUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.List;

import static com.company.container.Componentconteiner.delivery;
import static com.company.container.Componentconteiner.userCurrentMenu;

public class FoodMenuController {
    private Long orderId=20000L;
    public SendMessageEntity start(Message message) {
        userCurrentMenu.put(message.getChatId().toString(),message.getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Select a category.");
        sendMessage.setReplyMarkup(FoodButtonUtil.getFoodMenu());
        SendMessageEntity sendMessageEntity=new SendMessageEntity();
        sendMessageEntity.setSendMessage(sendMessage);
        sendMessageEntity.setType(SendMsgType.Message);
        return sendMessageEntity;
    }

    public SendMessageEntity foodMenu(Message message) {
        SendMessageEntity sendMessage=new SendMessageEntity();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        String text = message.getText();
        userCurrentMenu.put(message.getChatId().toString(),text);
        InputFile inputFile = new InputFile(new File("D:/JAVA/Java-lesson/Kurs_ishi/src/main/resources/photo/"
                + text + ".png"));
        switch (text) {
            case "Set":
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setReplyMarkup(FoodButtonUtil.setMenu());

                break;
            case "Lavash":
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setReplyMarkup(FoodButtonUtil.lavashMenu());
                break;
            case "Donar":
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setReplyMarkup(FoodButtonUtil.donarMenu());
                break;
            case "Shaurma":
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setReplyMarkup(FoodButtonUtil.shaurmaMenu());
                break;
            case "Burger":
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setReplyMarkup(FoodButtonUtil.burgerMenu());
                break;
            case "Xot-dog":
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setReplyMarkup(FoodButtonUtil.xotDogMenu());
                break;
            case "Drinks":
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setReplyMarkup(FoodButtonUtil.drinksMenu());
                break;
            case "Garnir":
                sendPhoto.setPhoto(inputFile);
                sendPhoto.setReplyMarkup(FoodButtonUtil.garnirMenu());
                break;
        }

        sendMessage.setSendPhoto(sendPhoto);
        sendMessage.setType(SendMsgType.Photo);
        return sendMessage;
    }

    public SendMessageEntity makeOrder(Message message){
        String chatId=message.getChatId().toString();
        userCurrentMenu.put(chatId,"Send phone");
        SendMessageEntity sendMessageEntity=new SendMessageEntity();
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please select a payment type");
        sendMessage.setReplyMarkup(paymentTypeButton());
        sendMessageEntity.setSendMessage(sendMessage);
        sendMessageEntity.setType(SendMsgType.Message);
        return sendMessageEntity;
    }

    public SendMessageEntity orderConfirmation(Message message){

          String chatId=message.getChatId().toString();
          SendMessageEntity sendMessageEntity=new SendMessageEntity();
          SendMessage sendMessage=new SendMessage();
          sendMessage.setText("do you confirm your order");
          sendMessage.setChatId(chatId);
          sendMessage.setReplyMarkup(orderConfirmationButton());
          sendMessageEntity.setSendMessage(sendMessage);
          sendMessageEntity.setType(SendMsgType.Message);
          return sendMessageEntity;
    }

    public SendMessageEntity confirmation(Message message){
        SendMessageEntity sendMessageEntity=new SendMessageEntity();
        SendMessage sendMessage=new SendMessage();
        List<Food> orderList= FoodsRepository.getfoodListByChatid(message.getChatId().toString());
        StringBuffer str=new StringBuffer();
        str.append("Order number: "+(orderId++)+"/");
        str.append("Status: "+ OrderStatus.Order_started+"/");
        Double sum=0d;
        for (Food food : orderList) {
            str.append(food.getCount()+" x "+food.getFoodName()+"/");
            sum+=(food.getCount()*Double.parseDouble(food.getPrice()));
        }
        str.append("Payment type:"+ PaymentType.Cash +"/");
        str.append("Products: "+sum+" sum"+"/");
        str.append("Delivery: "+delivery+" sum"+"/");
        str.append("Total: "+(delivery+sum)+" sum"+"/");
        FoodsRepository.addOrderToFile(message.getChatId().toString(),str.toString());
        FoodsRepository.clear(message.getChatId().toString());
       String s=str.toString().replace("/","\n");
        sendMessage.setText(s);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(WelcomeButtonUtil.menuButton());
        sendMessageEntity.setSendMessage(sendMessage);
        sendMessageEntity.setType(SendMsgType.Message);
        return sendMessageEntity;
     }

    public SendMessageEntity myorders(String chatId,String text){
        SendMessageEntity sendMessageEntity=new SendMessageEntity();
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(WelcomeButtonUtil.backButton());
        sendMessageEntity.setSendMessage(sendMessage);
        sendMessageEntity.setType(SendMsgType.Message);
        return sendMessageEntity;
    }

    public ReplyKeyboardMarkup orderConfirmationButton(){
        KeyboardRow row1= KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Confirmation","✅"));
        KeyboardRow row2= KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Cancel",":x:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1,row2));
    }

    public ReplyKeyboardMarkup paymentTypeButton(){
        KeyboardRow row1= KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Cash"));
        KeyboardRow row2= KeyButtonUtil.keyRow(KeyButtonUtil.keyButton("Back",":arrow_left:"));
        return KeyButtonUtil.replyKeyboardMarkup(KeyButtonUtil.keyboardRowList(row1,row2));
    }
}
