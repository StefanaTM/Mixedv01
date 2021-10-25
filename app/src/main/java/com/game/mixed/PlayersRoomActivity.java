package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlayersRoomActivity extends AppCompatActivity {
    private TextView txtPin, pinGenerated, waitingForPlayers;
    private ListView playerslistView;
    private Button btnNext;
    Typeface chelsea;
    int pin;
    PlayerNameHelperClass hc_playerName;

    //Send pin to WriteAction activity
    public static final String EXTRA_INTEGER_PIN_ACTION="com.game.mixed.EXTRA_INTEGER_PIN_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_room);

        //retrieve the pin from PlayerName activity or from Home activity
        Intent intent=getIntent();
        pin=intent.getIntExtra(PlayerNameActivity.EXTRA_INTEGER_PIN_PLAYERS, 0);
        pinGenerated=findViewById(R.id.pin_generated);
        pinGenerated.setText(""+pin);

        txtPin=findViewById(R.id.txtPin);
        waitingForPlayers=findViewById(R.id.waitingForPlayers);
        playerslistView=findViewById(R.id.playersListView);
        btnNext=findViewById(R.id.players_btnNext);


        //Set font style
        chelsea= Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        txtPin.setTypeface(chelsea);
        waitingForPlayers.setTypeface(chelsea);
        pinGenerated.setTypeface(chelsea);
        btnNext.setTypeface(chelsea);

        final ArrayList<String> playersList = new ArrayList<>();
        final ArrayAdapter playersListAdapter=new ArrayAdapter<String>(this, R.layout.players_list_item, R.id.playersList_xml, playersList);
        playerslistView.setAdapter(playersListAdapter);
        //initialize instance of PlayerNameHelperClass
        hc_playerName = new PlayerNameHelperClass();

        DatabaseReference refPlayersList= FirebaseDatabase.getInstance().getReference().child("Players");
        //add query to interrogate the players table in order to retrieve just the children with same pin
        Query takeAllPlayersForSpecificPin = refPlayersList.orderByChild("pin").equalTo(pin);
        //change the implementation writing instead of refPlayersList.addValue to the new query
        takeAllPlayersForSpecificPin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                playersList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    hc_playerName = snapshot.getValue(PlayerNameHelperClass.class);
                    playersList.add(hc_playerName.getPlayerName());
                    //playersList.add(snapshot.getValue().toString());
                }
                playersListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Press button to go to write action activity
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWriteActionRoom();
            }
        });
    }

    //functionality open Write Action activity
    public void openWriteActionRoom(){
        TextView pinGenerated=findViewById(R.id.pin_generated);
        pinGenerated.setText(""+pin);
        int pin=Integer.parseInt(pinGenerated.getText().toString());
        Intent intent=new Intent(this, WriteActionActivity.class);
        intent.putExtra(EXTRA_INTEGER_PIN_ACTION, pin);
        startActivity(intent);
    }
}