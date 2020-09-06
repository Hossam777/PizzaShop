package com.example.cvproject1;

public class FoodUnit {
    String description;
    String id;
    String link;
    String name;
    int price;
    int quantity;
    float rating;

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public FoodUnit(String description, String id, String imageLink, String name, int price, float rating) {
        this.description = description;
        this.id = id;
        this.link = imageLink;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }
    public FoodUnit() { }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
