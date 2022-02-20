package com.example.finalproject;

public class Room {

    private String roomType;
    private String roomDescription;
    private int drawableRcs;
    private int roomNumber;

    public Room(String roomType, String roomDescription, int drawableRcs, int roomNumber) {
        this.roomType = roomType;
        this.roomDescription = roomDescription;
        this.drawableRcs = drawableRcs;
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public int getDrawableRcs() {
        return drawableRcs;
    }

    public void setDrawableRcs(int drawableRcs) {
        this.drawableRcs = drawableRcs;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
