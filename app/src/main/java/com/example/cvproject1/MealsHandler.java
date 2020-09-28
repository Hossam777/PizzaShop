package com.example.cvproject1;

import java.util.ArrayList;

public class MealsHandler {
    static ArrayList<FoodUnit> pizza = new ArrayList<>();
    static ArrayList<FoodUnit> pasta = new ArrayList<>();
    static ArrayList<FoodUnit> rice = new ArrayList<>();

    static public void clearData(){
        pizza = new ArrayList<>();
        pasta = new ArrayList<>();
        rice = new ArrayList<>();
    }
    static public ArrayList<FoodUnit> getPizza() {
        return pizza;
    }
    static public void setPizza(ArrayList<FoodUnit> pizza) {
        MealsHandler.pizza = pizza;
    }

    static public ArrayList<FoodUnit> getPasta() {
        return pasta;
    }

    static public void setPasta(ArrayList<FoodUnit> pasta) {
        MealsHandler.pasta = pasta;
    }

    static public ArrayList<FoodUnit> getRice() {
        return rice;
    }

    static public void setRice(ArrayList<FoodUnit> rice) {
        MealsHandler.rice = rice;
    }

    public static String getMealName(String id) {
        for(FoodUnit foodUnit : pizza){
            if(foodUnit.getId().equals(id))
                return foodUnit.getName();
        }for(FoodUnit foodUnit : pasta){
            if(foodUnit.getId().equals(id))
                return foodUnit.getName();
        }for(FoodUnit foodUnit : rice){
            if(foodUnit.getId().equals(id))
                return foodUnit.getName();
        }
        return "Meal Not Found";
    }
}
