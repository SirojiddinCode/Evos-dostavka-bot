package com.company.entity;

import com.company.enums.OrderStatus;
import com.company.enums.PaymentType;

public class Food {
    private Long orderId;
    private String chatId;
    private String foodName;
    private String price;
    private int count;
    private String timeOfDelivery;
    private OrderStatus orderStatus=OrderStatus.Order_cancelled;
    private PaymentType paymentType;


    public Food() {

    }

    public Food(Long foodId,String chatId, String foodName, int count, String price) {
        this.chatId = chatId;
        this.foodName = foodName;
        this.count = count;
        this.price = price;
        this.orderId=foodId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTimeOfDelivery() {
        return timeOfDelivery;
    }

    public void setTimeOfDelivery(String timeOfDelivery) {
        this.timeOfDelivery = timeOfDelivery;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
