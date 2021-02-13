package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private TextView insertPin, or;
    private EditText pin;
    private Button createRoom, instructions;
    private Typeface chelsea;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth=FirebaseAuth.getInstance();

        //hide toolbar
        //getSupportActionBar().hide();

        //setare font
        insertPin=(TextView) findViewById(R.id.insertPin);
        or=(TextView)findViewById(R.id.or);
        pin=(EditText) findViewById(R.id.pinNumber);
        createRoom=(Button)findViewById(R.id.createRoom);
        instructions=(Button) findViewById(R.id.instructions);

        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");

        insertPin.setTypeface(chelsea);
        or.setTypeface(chelsea);
        pin.setTypeface(chelsea);
        createRoom.setTypeface(chelsea);
        instructions.setTypeface(chelsea);

        //catre pagina Instructiuni
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstructions();
            }
        });

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsertName();
            }
        });
    }

    public void openInstructions(){
        Intent intent=new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void openInsertName(){
        Intent intent=new Intent(this, InsertNameActivity.class );
        startActivity(intent);
    }

    //log out user
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.xloglogoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
}