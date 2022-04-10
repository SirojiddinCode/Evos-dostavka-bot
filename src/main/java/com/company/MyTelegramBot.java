package com.company;


import com.company.entity.SendMessageEntity;
import com.company.enums.SendMsgType;
import com.company.repository.FoodsRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static com.company.container.Componentconteiner.*;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    HashMap<String,String> userCantact=new HashMap<>();
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            User user = message.getFrom();
            String text = message.getText();
            String chatId = message.getChatId().toString();
            log(user.getFirstName(), user.getLastName(), user.getId(), text);
            if (text != null) {
                if (text.equals("/start")) {
                    sendMsg(welcomeController.start(message));
                } else if (text.equals("\uD83C\uDF74 Menu") ||
                        text.equals("✍ Leave feedback") || text.equals("⚙ Settings")) {
                    sendMsg(welcomeController.menu(message));
                } else if (text.equals("\uD83D\uDECD My orders")) {
                    userCurrentMenu.put(chatId,text);
                    List<String> orderList = FoodsRepository.getorderList(chatId);
                    if (orderList.isEmpty()) {
                        text = "your orders is empty";
                        sendMsg(foodMenuController.myorders(chatId, text));
                    } else {
                        for (String s : orderList) {
                            sendMsg(foodMenuController.myorders(chatId, s));
                        }
                    }
                } else if (text.equals("✅ Yes") || text.equals("❌ No")) {
                    sendMsg(welcomeController.confirmation(message));
                } else if (text.equals("Set") || text.equals("Lavash") || text.equals("Shaurma")) {
                    sendMsg(foodMenuController.foodMenu(message));
                } else if (text.equals("Donar") || text.equals("Burger") || text.equals("Xot-dog")) {
                    sendMsg(foodMenuController.foodMenu(message));
                } else if (text.equals("Drinks") || text.equals("Garnir")) {
                    sendMsg(foodMenuController.foodMenu(message));
                } else if (text.equals("\uD83D\uDCE5 Basket")) {
                    sendMsg(foodsController.basket(message));
                } else if (text.equals("Cash")) {
                    userCurrentMenu.put(message.getChatId().toString(), text);
                    sendMsg(foodMenuController.orderConfirmation(message));
                } else if (text.equals("❌ Cancel")) {
                    message.setText("/start");
                    FoodsRepository.clear(message.getChatId().toString());
                    sendMsg(welcomeController.start(message));
                } else if (text.equals("✅ Confirmation")) {
                    SendMessageEntity sendMessageEntity=foodMenuController.confirmation(message);
                    sendMsg(sendMessageEntity);
                    sendMessageEntity.getSendMessage().setChatId(AdminChatId);
                 String s="customer's phone number: \n +"+userCantact.get(chatId)+"\n"+
                         sendMessageEntity.getSendMessage().getText();
                    sendMessageEntity.getSendMessage().setText(s);
                    sendMsg(sendMessageEntity);
                } else if (text.equals("⬅ Back")) {
                    if (userCurrentMenu.containsKey(user.getId().toString())) {
                        String goToMenuName = userCurrentMenu.get(user.getId().toString());
                        switch (goToMenuName) {
                            case "\uD83C\uDF74 Menu":
                                message.setText("/start");
                                sendMsg(welcomeController.start(message));
                                break;
                            case "make order":
                                message.setText("Send location");
                                sendMsg(foodMenuController.start(message));
                                break;
                            case "✍ Leave feedback":
                                message.setText("/start");
                                sendMsg(welcomeController.start(message));
                                break;
                            case "⚙ Settings":
                                message.setText("/start");
                                sendMsg(welcomeController.start(message));
                                break;
                            case "\uD83D\uDECD My orders":
                                message.setText("/start");
                                sendMsg(welcomeController.start(message));
                                break;
                            case "Send phone":
                                message.setText("Send location");
                                sendMsg(foodMenuController.start(message));
                                break;
                            case "Send location":
                                message.setText("\uD83C\uDF74 Menu");
                                sendMsg(welcomeController.menu(message));
                                break;
                            default:
                                message.setText("Send location");
                                sendMsg(foodMenuController.start(message));
                                break;
                        }
                    }
                } else if (userCurrentMenu.containsKey(user.getId().toString())) {
                    String menu = userCurrentMenu.get(user.getId().toString());
                    if (menu.equals("Set") || menu.equals("Lavash") || menu.equals("Shaurma")) {
                        sendMsg(foodsController.Menu(message, menu));
                    } else if (menu.equals("Donar") || menu.equals("Burger") || menu.equals("Xot-dog")) {
                        sendMsg(foodsController.Menu(message, menu));
                    } else if (menu.equals("Drinks") || menu.equals("Garnir")) {
                        sendMsg(foodsController.Menu(message, menu));
                    } else if (menu.equals("✍ Leave feedback")) {
                        sendMsgForAdmin(message);
                    }
                }

            } else if (text == null) {
                if (message.hasLocation()) {
                    userCurrentMenu.put(message.getChatId().toString(), "Send location");
                    sendMsg(welcomeController.sendlocation(message));
                } else if (message.hasContact()) {
                    userCantact.put(chatId,message.getContact().getPhoneNumber());
                    sendMsg(foodMenuController.makeOrder(message));

                }
            }
        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String[] text = callbackQuery.getData().split("/");
            log(callbackQuery.getFrom().getFirstName(), callbackQuery.getFrom().getLastName(),
                    callbackQuery.getFrom().getId(), callbackQuery.getData());
            if (text[1].equals("calculate")) {
                sendMsg(foodsController.calculate(callbackQuery));
            } else if (text[0].equals("delete")) {
                sendMsg(foodsController.basketRealization(callbackQuery));
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "My_Evos_bot";
    }

    @Override
    public String getBotToken() {
        return "5055365207:AAEVANPXvkHPZBzLjxDFYU4BPA7CKGsgPLg";
    }

    private void log(String first_name, String last_name, Long user_id, String txt) {
        try {
            System.out.println("\n --------------------------------------------------------");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            System.out.println(dateFormat.format(LocalDateTime.now()));
            System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(SendMessageEntity sendMessage) {
        try {
            switch (sendMessage.getType()) {
                case Message:
                    execute(sendMessage.getSendMessage());
                    break;
                case Edit:
                    execute(sendMessage.getEditMessage());
                    break;
                case Photo:
                    execute(sendMessage.getSendPhoto());
                    break;
                case EditMessageReply:
                    execute(sendMessage.getEditMessageReplyMarkup());
                    break;
                case MessageAndEditMessageReply:
                    execute(sendMessage.getEditMessageReplyMarkup());
                    execute(sendMessage.getSendMessage());
                    break;
                case MessageAndEdit:
                    execute(sendMessage.getEditMessage());
                    execute(sendMessage.getSendMessage());
                    break;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsgForAdmin(Message message) {
        SendMessageEntity sendMessageEntity = new SendMessageEntity();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(AdminChatId);
        String s = "the customer who sent the message: @" + message.getFrom().getUserName();
        sendMessage.setText(message.getText() + "\n" + s);
        sendMessageEntity.setSendMessage(sendMessage);
        sendMessageEntity.setType(SendMsgType.Message);
        sendMsg(sendMessageEntity);
    }

}
