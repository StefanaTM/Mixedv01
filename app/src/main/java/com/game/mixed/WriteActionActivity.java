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

    private TextView txtAction, txtPin, txtidAction;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_action);

        //retrieve the pin from PlayerName activity
        Intent intent=getIntent();
        pin=intent.getIntExtra(PlayersRoomActivity.EXTRA_INTEGER_PIN_ACTION, 0);
        txtPin=findViewById(R.id.txtPin);
        txtPin.setText(""+pin);

        //retrieve the pin from WriteAction activity
        if(pin == 0){
            pin=intent.getIntExtra(WriteActionActivity.EXTRA_INTEGER_PIN_ACTIONLOOP, 0);
            txtPin=findViewById(R.id.txtPin);
            txtPin.setText(""+pin);
        }

        txtAction = findViewById(R.id.txtAction);
        textAction=findViewById(R.id.text_action);
        txtidAction=findViewById(R.id.txtidAction); //needed to arrange the action with places and with continuation of action values in the end based on this counter
        btnNext=findViewById(R.id.action_btnNext);

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
                openWriteActionActivity();
            }
        });

    }

    //functionality open Write Action activity or Write Place activity
        public void openWriteActivity(){
            //retrieve the number of cards set by user based on the pin number
            refRooms= FirebaseDatabase.getInstance().getReference("Rooms");
            Query queryNrCards =refRooms.orderByChild("pin").equalTo(pin);
            queryNrCards.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //access room settings helper class in order to read the values from db
                    room_hc = snapshot.getValue(RoomSettingsHelperClass.class);
                    //assign to int nrCards the value found of the attribute nrCards found by queryNrCards
                    int nrCards = Integer.parseInt(room_hc.getNrCards().toString());
                    int initial = 1;
                    while(initial < nrCards){
                        openWriteActionActivity();
                        initial ++;
                    }
                    if (initial == nrCards){
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
        Intent intent=new Intent(this, WriteActionActivity.class);
        intent.putExtra(EXTRA_INTEGER_PIN_ACTIONLOOP, pin);
        startActivity(intent);
    }
    public void openWritePlaceActivity(){
        Intent intent=new Intent(this, WritePlaceActivity.class);
        startActivity(intent);
    }
}
