package com.example.finalproject;

public class Order {
    private String number;
    private String foodItem;
    private String status;
    private String roomNumber;

    public Order(String number, String foodItem, String status, String roomNumber) {
        this.number = number;
        this.foodItem = foodItem;
        this.status = status;
        this.roomNumber = roomNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
