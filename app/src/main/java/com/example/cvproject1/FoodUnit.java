package com.example.cvproject1;

public class FoodUnit {
    String description;
    String id;
    String link;
    String name;
    int price;
    int quantity;
    public FoodUnit(String description, String id, String imageLink, String name, int price) {
        this.description = description;
        this.id = id;
        this.link = imageLink;
        this.name = name;
        this.price = price;
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
