package com.company.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Price {
    public static String getPrice(String name){
        try {
            FileReader fileReader=new FileReader(new File("price.txt"));
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line;
            String[]arr;
            while((line=bufferedReader.readLine())!=null){
                arr=line.split("#");
                if (arr[0].equals(name)){
                    return arr[1];
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
