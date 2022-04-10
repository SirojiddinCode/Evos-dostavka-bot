package com.company.entity;

import com.company.enums.SendMsgType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class SendMessageEntity {
    private SendMessage sendMessage;
    private EditMessageText editMessage;
    private SendPhoto sendPhoto;
    private EditMessageReplyMarkup editMessageReplyMarkup;
    private SendMsgType type;

    public EditMessageReplyMarkup getEditMessageReplyMarkup() {
        return editMessageReplyMarkup;
    }

    public void setEditMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) {
        this.editMessageReplyMarkup = editMessageReplyMarkup;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public EditMessageText getEditMessage() {
        return editMessage;
    }

    public void setEditMessage(EditMessageText editMessage) {
        this.editMessage = editMessage;
    }

    public SendPhoto getSendPhoto() {
        return sendPhoto;
    }

    public void setSendPhoto(SendPhoto sendPhoto) {
        this.sendPhoto = sendPhoto;
    }

    public SendMsgType getType() {
        return type;
    }

    public void setType(SendMsgType type) {
        this.type = type;
    }
}
