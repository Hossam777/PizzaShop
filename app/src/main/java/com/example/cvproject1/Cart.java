package com.example.cvproject1;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Cart {
    private static ArrayList<FoodUnit> bagFoodItems = new ArrayList<>();
    private static ArrayList<String> bag = new ArrayList<String>(){
        @NonNull
        @Override
        public String toString() {
            String bagString = "";
            for(int i = 0; i < bag.size(); ++i){
                bagString += bag.get(i);
                if(i != bag.size() - 1)
                    bagString += ";";
            }
            return bagString;
        }
    };
    public static void addFoodUnit(FoodUnit foodUnit) {
        boolean newItem = true;
        for(int i = 0; i < bagFoodItems.size(); ++i){
            if(bagFoodItems.get(i).getId().equals(foodUnit.getId())){
                FoodUnit item = bagFoodItems.get(i);
                item.setQuantity(item.getQuantity() + 1);
                bag.remove(i);
                bagFoodItems.remove(i);
                bag.add(convertFoodUnitToString(item, item.getQuantity()));
                bagFoodItems.add(item);
                newItem = false;
                break;
            }
        }
        if(newItem){
            bag.add(convertFoodUnitToString(foodUnit, 1));
            foodUnit.setQuantity(1);
            bagFoodItems.add(foodUnit);
        }
    }
    public static void removeFoodUnit(String id) {
        for(int i = 0; i < bagFoodItems.size(); ++i){
            if(bagFoodItems.get(i).getId().equals(id)){
                bag.remove(i);
                bagFoodItems.remove(i);
                break;
            }
        }
    }
    private static String convertFoodUnitToString(FoodUnit foodUnit, int quantity){
        String itemString = foodUnit.getId()
                + "|" + foodUnit.getName()
                + "|" + foodUnit.getDescription()
                + "|" + foodUnit.getPrice()
                + "|" + quantity
                + "|" + foodUnit.getLink();
        return itemString;
    }
    private static FoodUnit convertStringToFoodItem(String itemString){
        FoodUnit item = new FoodUnit();
        String[] splitted = itemString.split("\\|");
        item.setId(splitted[0]);
        item.setName(splitted[1]);
        item.setDescription(splitted[2]);
        item.setPrice(Integer.parseInt(splitted[3]));
        item.setQuantity(Integer.parseInt(splitted[4]));
        item.setLink(splitted[5]);
        return item;
    }
    public static ArrayList<FoodUnit> getFoodList(){
        return bagFoodItems;
    }
    public static void writeTOSharedPreference(Context context){
        SharedPreferenceInterface.writeString(context, SharedPreferenceInterface.bagKey, bag.toString());
    }
    public static void readFromSharedPreference(Context context){
        String bagString = SharedPreferenceInterface.readString(context, SharedPreferenceInterface.bagKey);
        bag = new ArrayList<>();
        bagFoodItems = new ArrayList<>();
        if(bagString != null && (!bagString.equals("[]"))){
            bag.addAll(Arrays.asList(bagString.split(";")));
            for(String item : bag){
                bagFoodItems.add(convertStringToFoodItem(item));
            }
        }
    }
}
