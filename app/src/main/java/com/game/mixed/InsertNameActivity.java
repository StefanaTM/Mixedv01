package com.game.mixed;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class InsertNameActivity extends AppCompatActivity {

    TextView playerName;
    EditText insertName;
    CheckBox chk_admin;
    private Button btn_next;
    Typeface chelsea;
    private Object CheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_name);

        //setare font
        playerName=(TextView) findViewById(R.id.playerName);
        insertName=(EditText) findViewById(R.id.insertName);
        chk_admin=(CheckBox)findViewById(R.id.chk_admin);
        btn_next=(Button) findViewById(R.id.btn_next);

        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");

        playerName.setTypeface(chelsea);
        insertName.setTypeface(chelsea);
        chk_admin.setTypeface(chelsea);
        btn_next.setTypeface(chelsea);
    }
}