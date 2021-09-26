package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayerNameActivity extends AppCompatActivity {

    private TextView txtPlayerName;
    private EditText playerName;
    private Button btnNext;
    Typeface chelsea;
    DatabaseReference ref;
    PlayerNameHelperClass playerName_hc;
    long playerId=0;
    int pin;

    public static final String EXTRA_INTEGER_PIN_PLAYERS="com.game.mixed.EXTRA_INTEGER_PIN_PLAYERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        //retrieve the pin from RoomSettings activity
        Intent intent_auto=getIntent();
        pin=intent_auto.getIntExtra(RoomSettingsActivity.EXTRA_INTEGER_PIN, 0);
        final TextView txtPin=findViewById(R.id.txtPin);
        txtPin.setText(""+pin);

        txtPlayerName=findViewById(R.id.txtPlayerName);
        playerName=findViewById(R.id.playerName);
        btnNext=findViewById(R.id.plname_btnNext);

        //retrieve the pin inserted by the users in the home activity
        Intent intent_home = getIntent();
        int pin_home = intent_home.getIntExtra(HomeActivity.EXTRA_HOMEPINNUMBER, 0);
        if(pin_home!=0){
            txtPin.setText(""+pin_home);
        }

        //Set font style
        chelsea= Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        txtPlayerName.setTypeface(chelsea);
        playerName.setTypeface(chelsea);
        btnNext.setTypeface(chelsea);

        playerName_hc = new PlayerNameHelperClass();
        ref= FirebaseDatabase.getInstance().getReference().child("Players");

        //create pk for the Players class
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    playerId=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take player name from user and insert it into firebase
                playerName_hc.setPlayerName(playerName.getText().toString().trim());
                //take room pin and assign it to the player in firebase
                int pin=Integer.parseInt(txtPin.getText().toString().trim());
                playerName_hc.setPin(pin);

                //increase player id counter
                ref.child(String.valueOf(playerId+1)).setValue(playerName_hc);

                //open players room activity
                openPlayersRoom();
            }
        });
    }

    //functionality open players room activity
    public void openPlayersRoom(){
        TextView txtPin=findViewById(R.id.txtPin);
        txtPin.setText(""+pin);
        int pin=Integer.parseInt(txtPin.getText().toString());
        Intent intent=new Intent(this, PlayersRoomActivity.class);
        intent.putExtra(EXTRA_INTEGER_PIN_PLAYERS, pin);
        startActivity(intent);
    }

}