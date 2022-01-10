package com.example.finalproject;

public class FoodMenuItem {
    private String title;
    private String description;
    private int drawableId;
    private int itemId;

    public FoodMenuItem(String title, String description, int drawableId, int itemId) {
        this.title = title;
        this.description = description;
        this.drawableId = drawableId;
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getItemId() {
        return itemId;
    }
}
