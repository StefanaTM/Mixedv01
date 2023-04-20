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

public class WriteActionActivity extends AppCompatActivity {

    private TextView txtAction, txtPin, txtidAction, txtNrCards;
    private EditText textAction;
    private Button btnNext;
    Typeface chelsea;
    DatabaseReference refRooms;
    DatabaseReference refAction;
    long actionId = 0;
    int pin;
    RoomSettingsHelperClass room_hc;
    WriteActionHelperClass writeAction_hc;

    //Send pin to write action/place activity
    public static final String EXTRA_INTEGER_PIN_ACTIONLOOP="com.game.mixed.EXTRA_INTEGER_PIN_ACTION";
    //Send nrCards to Action or Place activity
    public static final String EXTRA_INTEGER_NRCARDS="com.game.mixed.EXTRA_INTEGER_NRCARDS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_action);

        //retrieve the pin from PlayerName activity
        Intent intent=getIntent();
        pin=intent.getIntExtra(PlayersRoomActivity.EXTRA_INTEGER_PIN_ACTION, 0);
        txtPin=findViewById(R.id.txtPin);
        txtPin.setText(""+pin);

        //retrieve the pin from WriteAction activity - when users already wrote an action
        if(pin == 0){
            pin=intent.getIntExtra(WriteActionActivity.EXTRA_INTEGER_PIN_ACTIONLOOP, 0);
            txtPin=findViewById(R.id.txtPin);
            txtPin.setText(""+pin);
        }

        txtAction = findViewById(R.id.txtAction);
        textAction=findViewById(R.id.text_action);
        txtidAction=findViewById(R.id.txtidAction); //needed to group the action with places and with continuation of action values in the end based on this counter
        btnNext=findViewById(R.id.action_btnNext);
        txtNrCards = findViewById(R.id.txtNrCards);

        //Set font style
        chelsea= Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        txtAction.setTypeface(chelsea);
        textAction.setTypeface(chelsea);
        btnNext.setTypeface(chelsea);

        //instantiate write action helper class to be used below
        writeAction_hc = new WriteActionHelperClass();
        //create pk counter for Actions class
        refAction= FirebaseDatabase.getInstance().getReference().child("Actions");
        refAction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    actionId=(dataSnapshot.getChildrenCount());
                    txtidAction.setText(""+actionId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Press button to go to write action activity OR jump to write place activity
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //persist action text in db
                writeAction_hc.setAction(textAction.getText().toString().trim());

                int idAction = Integer.parseInt(txtidAction.getText().toString().trim());
                writeAction_hc.setIdAction(idAction+1);

                //take room pin and assign it to the action in firebase
                writeAction_hc.setPin(pin);

                //increase action id class counter
                refAction.child(String.valueOf(actionId+1)).setValue(writeAction_hc);

                //This needs to be changed with a rule implemented in method openWriteActivity below
                openActionOrPlaceActivity();
            }
        });
    }

    //functionality open Write Action activity or Write Place activity
        public void openActionOrPlaceActivity(){
            //retrieve the number of cards set by user based on the pin number
            refRooms= FirebaseDatabase.getInstance().getReference("Rooms");
            Query queryNrCards =refRooms.orderByChild("pin").equalTo(pin);
            queryNrCards.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //access room settings helper class in order to read the values from db
                    room_hc = snapshot.getValue(RoomSettingsHelperClass.class);
                    //assign to int nrCards the value found of the attribute nrCards found by queryNrCards
                    int nrCards = 4; //Integer.parseInt(room_hc.getNrCards().toString().trim()); //If I let //4 instead of retrieing room_hc value it work bt in infinite loop
                    txtNrCards.setText(""+nrCards);
                    Intent intent = new Intent();
                    int nrCardsToDecrease = intent.getIntExtra(WriteActionActivity.EXTRA_INTEGER_NRCARDS, 11); //because the nrOfCards set by users cannot be over 10
                    if(nrCardsToDecrease == 11){//first time loop
                        nrCardsToDecrease =nrCards; //4 let's say set by the user
                    }
                    if(nrCardsToDecrease > 0 ){ //4
                        nrCardsToDecrease = nrCardsToDecrease-1;
                        txtNrCards.setText(""+nrCardsToDecrease);
                        openWriteActionActivity();
                    }else {
                        openWritePlaceActivity();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    public void openWriteActionActivity(){
        int pin=Integer.parseInt(txtPin.getText().toString());
        int nrCardsRemaining= Integer.parseInt(txtNrCards.getText().toString());
        Intent intent=new Intent(this, WriteActionActivity.class);
        //send pin and the remaining nr of cards to be filled in to the same activity
        intent.putExtra(EXTRA_INTEGER_PIN_ACTIONLOOP, pin);//ok
        intent.putExtra(EXTRA_INTEGER_NRCARDS, nrCardsRemaining);
        startActivity(intent);
    }
    public void openWritePlaceActivity(){
        Intent intent=new Intent(this, WritePlaceActivity.class);
        startActivity(intent);
    }
}
