package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class RoomSettingsActivity extends AppCompatActivity {

    private TextView nrCards, txtNrGroupsCards;
    private Button btn_next;
    Typeface chelsea;
    DatabaseReference ref;
    RoomSettingsHelperClass room_hc;
    private int nrCardsCount=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_settings);

        btn_next=(Button) findViewById(R.id.insnameNext);
        nrCards=findViewById(R.id.nrCards);
        txtNrGroupsCards=findViewById(R.id.nrtxtNrGrCards);

        //Set font style
        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        btn_next.setTypeface(chelsea);
        nrCards.setTypeface(chelsea);
        txtNrGroupsCards.setTypeface(chelsea);

        room_hc=new RoomSettingsHelperClass();
        ref= FirebaseDatabase.getInstance().getReference().child("Rooms");

        //pinCounter
        final Query queryLastRoom = ref.orderByKey().limitToLast(1);
        queryLastRoom.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful() && null!=task.getResult()){
                    for(DataSnapshot snapshot:task.getResult().getChildren()){
                        Gson gson=new Gson();
                        JsonElement jsonElement=gson.toJsonTree(snapshot.getValue());
                        room_hc=gson.fromJson(jsonElement, RoomSettingsHelperClass.class);
                        Log.d("TEST-LOG", "Last pin: "+ room_hc.toString());
                    }
                }else{
                    Log.d("TEST-LOG FAIL", "No data");
                }
            }
        });

        //persist data from room settings and open player name activity
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take nr of cards from user and insert it into firebase
                int intNrCards=Integer.parseInt(nrCards.getText().toString().trim());
                room_hc.setNrCards(intNrCards);

                //take last pin added in firebase, increment it and send new value to firebase (new record)
                if(room_hc!=null){
                    room_hc.setPin(room_hc.getPin()+1);
                }
                ref.child(String.valueOf(room_hc.getPin())).setValue(room_hc);
                Log.d("TEST-LOG", "Added: "+room_hc.toString());

                //open set player name activity
                openPlayerName();
            }
        });
    }

    //increment number of cards
    public void increment(View view) {
        if(nrCardsCount>=10)
            nrCardsCount=10;
        else
            nrCardsCount++;
        nrCards.setText(""+nrCardsCount);
    }

    //decrement number of cards
    public void decrement(View view) {
        if(nrCardsCount<=1)
            nrCardsCount=1;
        else
            nrCardsCount--;
        nrCards.setText(""+nrCardsCount);
    }

    //functionality open player name activity
    public void openPlayerName(){
        Intent intent=new Intent(this, PlayerNameActivity.class);
        startActivity(intent);
    }
}