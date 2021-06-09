package com.game.mixed;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RoomNameActivity extends AppCompatActivity {

    private TextView playerName;
    private EditText insertName;
    private Button btn_next;
    Typeface chelsea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_name);

        //setare font
        playerName=(TextView) findViewById(R.id.insnameRoomName);
        insertName=(EditText) findViewById(R.id.insnameInsertName);
        btn_next=(Button) findViewById(R.id.insnameNext);

        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");

        playerName.setTypeface(chelsea);
        insertName.setTypeface(chelsea);
        btn_next.setTypeface(chelsea);
    }
}