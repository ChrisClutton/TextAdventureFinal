package com.example.textadventurefinal;

public class Room {

    static final int NO_EXIT = -1;
    static final String NOTHING = "NOTHING";

    private int north;
    private int east;
    private int south;
    private int west;

    private String name;
    private String description;
    private String inventory;

    Room() {
        north = NO_EXIT;
        east = NO_EXIT;
        south = NO_EXIT;
        west = NO_EXIT;
        name = NOTHING;
        description = NOTHING;
        inventory = NOTHING;
    }

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getSouth() {
        return south;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }
}
