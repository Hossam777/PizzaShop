package com.example.cvproject1;

import java.util.ArrayList;

public class MealsHandler {
    static ArrayList<FoodUnit> pizza = new ArrayList<>();
    static ArrayList<FoodUnit> pasta = new ArrayList<>();
    static ArrayList<FoodUnit> rice = new ArrayList<>();

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
}
