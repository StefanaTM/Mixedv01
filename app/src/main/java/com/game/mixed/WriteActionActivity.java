package com.game.mixed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WriteActionActivity extends AppCompatActivity {

    private TextView txtAction, txtPin;
    private EditText textAction;
    private Button btnNext;
    Typeface chelsea;
    int pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_action);

        //retrieve the pin from PlayerName activity or from Home activity
        Intent intent=getIntent();
        pin=intent.getIntExtra(PlayersRoomActivity.EXTRA_INTEGER_PIN_ACTION, 0);
        txtPin=findViewById(R.id.txtPin);
        txtPin.setText(""+pin);

        txtAction = findViewById(R.id.txtAction);
        textAction=findViewById(R.id.text_action);
        btnNext=findViewById(R.id.action_btnNext);

        //Set font style
        chelsea= Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        txtAction.setTypeface(chelsea);
        textAction.setTypeface(chelsea);
        btnNext.setTypeface(chelsea);

    }
}