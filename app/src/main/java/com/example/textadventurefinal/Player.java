package com.example.textadventurefinal;

public class Player {
    static final String NOTHING = "NOTHING";
    private int position;
    private String inventory;

    Player(int Pos, String Inv) {

        position = Pos;
        inventory = Inv;
    }

    public int getPlayerPos() {
        return position;
    }

    public void setPlayerPos(int playerPos) {
        this.position = playerPos;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }
}
