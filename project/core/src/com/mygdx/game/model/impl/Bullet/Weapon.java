package com.mygdx.game.model.impl.Bullet;

public class Weapon {


    private String name;
    private int price;
    private boolean unlocked;
    private float width;
    private float height;

    public Weapon(String name, int price, boolean unlocked, float width, float height) {
        this.name = name;
        this.price = price;
        this.unlocked = unlocked;
        this.width = width;
        this.height = height;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    public void display(){
        System.out.println("Name: " + name + " Price: " + price + " Unlocked: " + unlocked + " Width: " + width + " Height: " + height);
    }
}