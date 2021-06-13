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

public class RoomNameActivity extends AppCompatActivity {

    private TextView roomName;
    private EditText insertRoomName;
    private Button btn_next;
    Typeface chelsea;
    DatabaseReference ref;
    RoomsHelperClass room;
    long roomId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_name);

        //setare font
        roomName=(TextView) findViewById(R.id.insnameRoomName);
        insertRoomName=(EditText) findViewById(R.id.insnameInsertName);
        btn_next=(Button) findViewById(R.id.insnameNext);
        room=new RoomsHelperClass();
        ref= FirebaseDatabase.getInstance().getReference().child("Rooms");
        //create pk for the Rooms class
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    roomId=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Set font style
        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");
        roomName.setTypeface(chelsea);
        insertRoomName.setTypeface(chelsea);
        btn_next.setTypeface(chelsea);

        //open set number of cards activity
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert room name into firebase
                room.setRoomName(insertRoomName.getText().toString().trim());
                ref.child(String.valueOf(roomId+1)).setValue(room);

                //open set number cards activity
                openSetNrCards();
            }
        });

    }

    //functionality open set number of cards activity
    public void openSetNrCards(){
        Intent intent=new Intent(this, SetNumberCardsActivity.class);
        startActivity(intent);
    }
}