package com.example.textadventurefinal;

import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String MY_PREFS = "save";
    SharedPreferences sharedPrefs;

    static final int NO_EXIT = -1;
    static final int NUM_OF_ROOMS = 25;

    Room[] theDungeon;

    Player thePlayer;

    TextView textViewName;
    TextView textViewDescription;
    TextView textViewPlayerInv;
    TextView textViewRoomInv;

    Button dropBtn;
    Button northBtn;
    Button pickupBtn;

    Button westBtn;
    Button eastBtn;

    Button saveBtn;
    Button southBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String playerPosString = getIntent().getStringExtra("PLAYER_POS");
        Log.w("Load Location", "Player location from Main Menu = " + playerPosString);
        String playerInvString = getIntent().getStringExtra("PLAYER_INV");
        Log.w("Load Inventory", "Player inventory from Main menu = " + playerInvString);

        initTheDungeon();
        displayRooms();

        readXMLFile();
        displayRooms();

        setupPlayer(Integer.parseInt(playerPosString), playerInvString);

        setupControls();
        validDirections();

        displayMessages();
    }

    public void pickup() {
        if((thePlayer.getInventory().equals(Player.NOTHING)) &&
                (theDungeon[thePlayer.getPlayerPos()].getInventory()!= Room.NOTHING)) {
            thePlayer.setInventory(theDungeon[thePlayer.getPlayerPos()].getInventory());
            theDungeon[thePlayer.getPlayerPos()].setInventory(Room.NOTHING);
        }
    }

    public void drop() {
        if ((thePlayer.getInventory() != Player.NOTHING) &&
                (theDungeon[thePlayer.getPlayerPos()].getInventory().equals(Room.NOTHING))) {
            theDungeon[thePlayer.getPlayerPos()].setInventory(thePlayer.getInventory());
            thePlayer.setInventory(thePlayer.NOTHING);
        }
    }

    public void displayMessages() {
        textViewName.setText(theDungeon[thePlayer.getPlayerPos()].getName());
        textViewDescription.setText((theDungeon[thePlayer.getPlayerPos()].getDescription()));
        textViewPlayerInv.setText("Player has " + thePlayer.getInventory());
        textViewRoomInv.setText("Room contains " + theDungeon[thePlayer.getPlayerPos()].getInventory());
    }

    public void setupPlayer(int newPlayerPos, String newPlayerInv) {

        thePlayer = new Player(newPlayerPos, newPlayerInv);
    }

    public void setupControls() {
        textViewName = findViewById(R.id.textViewName);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewPlayerInv = findViewById(R.id.textViewPlayerInv);
        textViewRoomInv = findViewById(R.id.textViewRoomInv);

        dropBtn = findViewById(R.id.dropBtn);
        dropBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drop();
                displayMessages();
            }
        });

        northBtn = findViewById(R.id.northBtn);
        northBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thePlayer.setPlayerPos(theDungeon[thePlayer.getPlayerPos()].getNorth());
                displayMessages();
                validDirections();
            }
        });

        pickupBtn = findViewById(R.id.pickupBtn);
        pickupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickup();
                displayMessages();
            }
        });

        westBtn = findViewById(R.id.westBtn);
        westBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                thePlayer.setPlayerPos(theDungeon[thePlayer.getPlayerPos()].getWest());
                displayMessages();
                validDirections();
            }
        });

        eastBtn = findViewById(R.id.eastBtn);
        eastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thePlayer.setPlayerPos(theDungeon[thePlayer.getPlayerPos()].getEast());
                displayMessages();
                validDirections();
            }
        });

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCount();
                Toast toast = Toast.makeText(getApplicationContext(),"Game Saved!",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        });

        southBtn = findViewById(R.id.southBtn);
        southBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thePlayer.setPlayerPos(theDungeon[thePlayer.getPlayerPos()].getSouth());
                displayMessages();
                validDirections();
            }
        });
    }

    public void SaveCount() {
        sharedPrefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putInt("savePos", thePlayer.getPlayerPos() );
        edit.putString("saveInv", thePlayer.getInventory() );
        edit.commit();
    }

    public void validDirections() {
        if (theDungeon[thePlayer.getPlayerPos()].getNorth() == NO_EXIT) {
            northBtn.setEnabled(false);
        } else {
            northBtn.setEnabled(true);
        }
        if (theDungeon[thePlayer.getPlayerPos()].getEast() == NO_EXIT) {
            eastBtn.setEnabled(false);
        } else {
            eastBtn.setEnabled(true);
        }
        if (theDungeon[thePlayer.getPlayerPos()].getSouth() == NO_EXIT) {
            southBtn.setEnabled(false);
        } else {
            southBtn.setEnabled(true);
        }
        if (theDungeon[thePlayer.getPlayerPos()].getWest() == NO_EXIT) {
            westBtn.setEnabled(false);
        } else {
            westBtn.setEnabled(true);
        }

    }

    public void initTheDungeon() {
        theDungeon = new Room[NUM_OF_ROOMS];

        for (int pos = 0; pos < NUM_OF_ROOMS; pos++) {
            theDungeon[pos] = new Room();
        }
    }

    public void displayRooms() {
        Log.w("display ROOM", "**************** start of display rooms ********************************");
        for (int pos = 0; pos < NUM_OF_ROOMS; pos++) {
            Log.w("display ROOM", "Name = " + theDungeon[pos].getName());
            Log.w("display ROOM", "Description = " + theDungeon[pos].getDescription());
            Log.w("display ROOM", "Inventory = " + theDungeon[pos].getInventory());
            Log.w("display ROOM", "North = " + theDungeon[pos].getNorth());
            Log.w("display ROOM", "East = " + theDungeon[pos].getEast());
            Log.w("display ROOM", "West = " + theDungeon[pos].getWest());
            Log.w("display ROOM", "South = " + theDungeon[pos].getSouth());
        }
        Log.w("display ROOM", "**************** end of display rooms **********************************");
    }

    public void readXMLFile() {
        int pos = 0;
        try {
            XmlResourceParser xpp = getResources().getXml(R.xml.dungeon);
            xpp.next();
            int eventType = xpp.getEventType();
            int room_count = 0;
            String elemtext = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String elemName = xpp.getName();
                    if (elemName.equals("dungeon")) {
                        String titleAttr = xpp.getAttributeValue(null,"title");
                        String authorAttr = xpp.getAttributeValue(null,"author");
                    } if (elemName.equals("room")) {
                        room_count = room_count + 1;
                    } if (elemName.equals("north")) {
                        elemtext = "north";
                    } if (elemName.equals("east")) {
                        elemtext = "east";
                    } if (elemName.equals("south")) {
                        elemtext = "south";
                    } if (elemName.equals("west")) {
                        elemtext = "west";
                    } if (elemName.equals("name")) {
                        elemtext = "name";
                    } if (elemName.equals("description")) {
                        elemtext = "description";
                    } if (elemName.equals("inventory")) {
                        elemtext = "inventory";
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (elemtext.equals("north")) {
                        Log.w("ROOM", "north = " + xpp.getText());
                        theDungeon[room_count-1].setNorth( Integer.valueOf(xpp.getText()));
                    } else if (elemtext.equals("east")) {
                        Log.w("ROOM", "east = " + xpp.getText());
                        theDungeon[room_count-1].setEast(Integer.valueOf(xpp.getText()));
                    } else if (elemtext.equals("south")) {
                        Log.w("ROOM", "south = " + xpp.getText());
                        theDungeon[room_count-1].setSouth(Integer.valueOf(xpp.getText()));
                    } else if (elemtext.equals("west")) {
                        Log.w("ROOM", "west = " + xpp.getText());
                        theDungeon[room_count - 1].setWest(Integer.valueOf(xpp.getText()));
                    } else if (elemtext.equals("name")) {
                        Log.w("ROOM", "name = " + xpp.getText());
                        theDungeon[room_count - 1].setName(xpp.getText());
                    } else if (elemtext.equals("description")) {
                        Log.w("ROOM", "description = " + xpp.getText());
                        theDungeon[room_count-1].setDescription( xpp.getText());
                    } else if (elemtext.equals("inventory")) {
                        Log.w("ROOM", "inventory = " + xpp.getText());
                        theDungeon[room_count-1].setInventory(xpp.getText());
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
        } catch (IOException e) {
        }
    }
}
