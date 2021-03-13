package com.example.mycalisthenicarea.bdd;

import com.example.mycalisthenicarea.Model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class ListDeProduits {
    private static List<FoodItem> items;

    protected ListDeProduits(){}

    public static List<FoodItem> getInstance(){
        if(items==null){
            items=new ArrayList<>();
        }
            return items;
    }
}
