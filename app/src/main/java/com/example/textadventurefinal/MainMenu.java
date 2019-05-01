package com.example.textadventurefinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity
{
    static final String NOTHING = "NOTHING";
    public static final String MY_PREFS = "save";
    SharedPreferences sharedPrefs;

    Button newGameBtn;
    Button loadGameBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        SetupControls();

    }


    public void SetupControls()
    {
        newGameBtn = findViewById(R.id.newGameBtn);
        newGameBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int playerPos = 0;
                String playerInv = NOTHING;

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("PLAYER_POS", "" + playerPos);
                intent.putExtra("PLAYER_INV", playerInv);
                startActivity(intent);
            }
        });

        loadGameBtn = findViewById(R.id.loadGameBtn);
        loadGameBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // this value needs to be read from shared prefs !!!
                int playerPos;
                String playerInv;

                sharedPrefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
                playerPos = sharedPrefs.getInt("savePos", 0);  // if no saved position, default value is 0
                playerInv = sharedPrefs.getString("saveInv", NOTHING); //if no saved inventory, default value is NOTHING

                String loadPosition = "" + playerPos;
                String loadInv = "" + playerInv;

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("PLAYER_POS", loadPosition);
                intent.putExtra("PLAYER_INV", loadInv);
                startActivity(intent);
            }
        });
    }
}