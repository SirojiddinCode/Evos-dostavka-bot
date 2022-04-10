package com.company.repository;

import com.company.entity.Food;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FoodsRepository {
    public static void addFoodToFile(Food food) {
        StringBuilder str = new StringBuilder();
        str.append(food.getOrderId());
        str.append("/").append(food.getChatId());
        str.append("/").append(food.getFoodName());
        str.append("/").append(food.getCount());
        str.append("/").append(food.getPrice());
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("foods.txt", true);
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.println(str);
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFoodInFile(String chatid, String foodname) {
        List<String> list = new LinkedList<>();
        File file = new File("foods.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] s;
            String line = bufferedReader.readLine();
            while (line != null) {
                s = line.split("/");
                if (!(s[1].equals(chatid) && s[2].equals(foodname))) {
                    list.add(line);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            PrintWriter writer = new PrintWriter(file);
            for (String food : list) {
                writer.println(food);
            }
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Food> getfoodListByChatid(String chatid) {
        List<Food> foods = new LinkedList<>();
        try {
            FileReader fileReader = new FileReader("foods.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            String[] arr;
            String line = reader.readLine();
            while (line != null) {
                arr = line.split("/");
                if (arr[1].equals(chatid)) {
                    Food food = new Food();
                    food.setOrderId(Long.parseLong(arr[0]));
                    food.setChatId(arr[1]);
                    food.setFoodName(arr[2]);
                    food.setCount(Integer.parseInt(arr[3]));
                    food.setPrice(arr[4]);
                    foods.add(food);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foods;
    }

    public static void clear(String chatId) {
        List<String> list = new LinkedList<>();
        File file = new File("foods.txt");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while (line != null) {
                if (!line.split("/")[1].equals(chatId)) {
                    list.add(line);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            PrintWriter writer = new PrintWriter(file);
            for (String s : list) {
                writer.println(s);
            }
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addOrderToFile(String chatId,String s) {

        FileWriter fileWriter = null;
        PrintWriter writer = null;
        try {
            fileWriter = new FileWriter("orders.txt", true);
            writer = new PrintWriter(fileWriter);
            s.replace(" \n","/");
            String str= String.join("#",chatId,s);
            writer.println(str);
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getorderList(String chatId){
        FileReader fileReader= null;
        List<String>orderList=new LinkedList<>();
        try {
            fileReader = new FileReader("orders.txt");
            BufferedReader reader=new BufferedReader(fileReader);
            String line= reader.readLine();
            String arr[];
            while(line!=null){
                arr=line.split("#");
                if (arr[0].equals(chatId)){
                    orderList.add(arr[1].replace("/","\n"));
                }
                line=reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
           return orderList;
    }
}

