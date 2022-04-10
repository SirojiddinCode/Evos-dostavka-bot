package com.company.container;

import com.company.controller.FoodMenuController;
import com.company.controller.FoodsController;
import com.company.controller.WelcomeController;

import java.util.HashMap;

public interface Componentconteiner {
    HashMap<String,String> userCurrentMenu = new HashMap<>();
    WelcomeController welcomeController=new WelcomeController();
    FoodMenuController foodMenuController =new FoodMenuController();
    FoodsController foodsController=new FoodsController();
    Double delivery=7000d;
    String AdminChatId="1983393920";
}
