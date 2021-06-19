package com.game.mixed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerNameActivity extends AppCompatActivity {

    private TextView txtPlayerName;
    private EditText playerName;
    private Button btnNext;
    Typeface chelsea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        txtPlayerName=findViewById(R.id.txtPlayerName);
        playerName=findViewById(R.id.playerName);
        btnNext=findViewById(R.id.plname_btnNext);

        //Set font style
        chelsea= Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        txtPlayerName.setTypeface(chelsea);
        playerName.setTypeface(chelsea);
        btnNext.setTypeface(chelsea);

    }

}