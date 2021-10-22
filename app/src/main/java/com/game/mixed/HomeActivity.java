package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    private TextView txt_homeInsertPin, txt_homeOr;
    private EditText txt_homePin;
    private Button btn_homeCreateRoom, btn_homeInstructions;
    private LinearLayout layout;
    private ImageView logo, btn_play;
    private Typeface chelsea;

    public static final String EXTRA_INTEGER_PIN_HOME = "com.game.mixed.EXTRA_HOMEPINNUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //hide toolbar
        //getSupportActionBar().hide();

        //set font
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

        //open Room Settings activity
        btn_homeCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRoomSettings();
            }
        });

        //create a database reference to check if the pin inserted exists in the database
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Rooms");
        //Select all pins that exists in the database
        final Query checkPins = ref.orderByChild("pin");
        //open player name activity
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txt_homePin.getText().toString().isEmpty()){
                    checkPins.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String localPin = txt_homePin.getText().toString().trim();
                            if(snapshot.child(localPin).exists()){
                                openPlayerName();
                            }else{
                                Toast.makeText(getApplicationContext(), "Please insert a correct PIN number.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    //function open instructions activity
    public void openInstructions(){
        Intent intent=new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    //function open create room activity
    public void openRoomSettings(){
        Intent intent=new Intent(this, RoomSettingsActivity.class );
        startActivity(intent);
    }

    //function open player name activity
    public void openPlayerName(){
        int pin=Integer.parseInt(txt_homePin.getText().toString());
        Intent intent=new Intent(this, PlayerNameActivity.class );
        intent.putExtra(EXTRA_INTEGER_PIN_HOME, pin);
        startActivity(intent);
    }
}