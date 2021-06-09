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
    private ImageView logo;
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
        logo=findViewById(R.id.logo_homehome);
        layout=findViewById(R.id.layout);

        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");

        txt_homeInsertPin.setTypeface(chelsea);
        txt_homeOr.setTypeface(chelsea);
        txt_homePin.setTypeface(chelsea);
        btn_homeCreateRoom.setTypeface(chelsea);
        btn_homeInstructions.setTypeface(chelsea);

        //catre pagina Instructiuni
        btn_homeInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstructions();
            }
        });

        btn_homeCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsertName();
            }
        });

    }

    public void openInstructions(){
        Intent intent=new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void openInsertName(){
        Intent intent=new Intent(this, RoomNameActivity.class );
        startActivity(intent);
    }
}