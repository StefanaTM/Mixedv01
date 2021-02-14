package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private TextView txt_homeInsertPin, txt_homeOr, txt_homeVerifyEmail;
    private EditText txt_homePin;
    private Button btn_homeCreateRoom, btn_homeInstructions, btn_homeVerify;
    private LinearLayout layout;
    private ImageView logo;
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
        txt_homeInsertPin=(TextView) findViewById(R.id.homeInsertPin);
        txt_homeOr=(TextView)findViewById(R.id.homeOr);
        txt_homePin=(EditText) findViewById(R.id.homePinNumber);
        txt_homeVerifyEmail = findViewById(R.id.homeVerifyEmail);
        btn_homeVerify = findViewById(R.id.homeVerifyButton);
        btn_homeCreateRoom=(Button)findViewById(R.id.homeCreateRoom);
        btn_homeInstructions=(Button) findViewById(R.id.homeInstructions);
        logo=findViewById(R.id.logo_homehome);
        layout=findViewById(R.id.layout);

        chelsea=Typeface.createFromAsset(getAssets(), "chelsea.ttf");

        txt_homeInsertPin.setTypeface(chelsea);
        txt_homeOr.setTypeface(chelsea);
        txt_homePin.setTypeface(chelsea);
        btn_homeCreateRoom.setTypeface(chelsea);
        btn_homeInstructions.setTypeface(chelsea);

        //catre pagina Instructiuni
        btn_homeInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstructions();
            }
        });

        btn_homeCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsertName();
            }
        });

        if(!firebaseAuth.getCurrentUser().isEmailVerified()){
            btn_homeVerify.setVisibility(View.VISIBLE);
            txt_homeVerifyEmail.setVisibility(View.VISIBLE);
            logo.setVisibility(View.GONE);
            txt_homeInsertPin.setVisibility(View.GONE);
            txt_homeOr.setVisibility(View.GONE);
            txt_homePin.setVisibility(View.GONE);
            btn_homeCreateRoom.setVisibility(View.GONE);
            btn_homeInstructions.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
        }

        btn_homeVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send the verification email
                firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HomeActivity.this, "Verification email sent.", Toast.LENGTH_LONG).show();
                    }
                });
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