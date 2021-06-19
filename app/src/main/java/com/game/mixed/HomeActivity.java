package com.game.mixed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private TextView txt_homeInsertPin, txt_homeOr;
    private EditText txt_homePin;
    private Button btn_homeCreateRoom, btn_homeInstructions;
    private LinearLayout layout;
    private ImageView logo, btn_play;
    private Typeface chelsea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //hide toolbar
        //getSupportActionBar().hide();

        //setare font
        txt_homeInsertPin=(TextView) findViewById(R.id.homeInsertPin);
        txt_homeOr=(TextView)findViewById(R.id.homeOr);
        txt_homePin=(EditText) findViewById(R.id.homePinNumber);
        btn_homeCreateRoom=(Button)findViewById(R.id.homeCreateRoom);
        btn_homeInstructions=(Button) findViewById(R.id.homeInstructions);
        btn_play = findViewById(R.id.homePlayBtn);
        logo=findViewById(R.id.logo_homehome);
        layout=findViewById(R.id.layout);

        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");

        txt_homeInsertPin.setTypeface(chelsea);
        txt_homeOr.setTypeface(chelsea);
        txt_homePin.setTypeface(chelsea);
        btn_homeCreateRoom.setTypeface(chelsea);
        btn_homeInstructions.setTypeface(chelsea);

        //open instructions activity
        btn_homeInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstructions();
            }
        });

        //open create room activity
        btn_homeCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsertName();
            }
        });

        //open player name activity
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayerName();
            }
        });

    }

    //function open instructions activity
    public void openInstructions(){
        Intent intent=new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    //function open create room activity
    public void openInsertName(){
        Intent intent=new Intent(this, RoomSettingsActivity.class );
        startActivity(intent);
    }

    //function open player name activity
    public void openPlayerName(){
        Intent intent=new Intent(this, PlayerNameActivity.class );
        startActivity(intent);
    }
}