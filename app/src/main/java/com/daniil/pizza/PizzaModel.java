package com.daniil.pizza;

import java.util.List;

public class PizzaModel {

    public String imgurl;
    public String pizzaname;
    public String size;
    public String crust;
    public String sauce;
    public String price;
    public List<String> toppings;
    public String pizzaId;
    public String description;

    public PizzaModel(){

    }

    public PizzaModel(String imageUrl, String name, String size, String crust, String sauce, String price, List<String> toppings, String pizzaId, String description) {
        this.imgurl = imageUrl;
        this.pizzaname = name;
        this.size = size;
        this.crust = crust;
        this.sauce = sauce;
        this.price = price;
        this.toppings = toppings;
        this.pizzaId = pizzaId;
        this.description = description;
    }

    public String getImageUrl() {
        return imgurl;
    }

    public void setImageUrl(String imageUrl) {
        this.imgurl = imageUrl;
    }

    public String getName() {
        return pizzaname;
    }

    public void setName(String name) {
        this.pizzaname = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCrust() {
        return crust;
    }

    public void setCrust(String crust) {
        this.crust = crust;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }

    public String getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(String pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
