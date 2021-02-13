package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btn_logcreateAccount, btn_logLogin;
    private EditText txt_logEmailAddress, txt_logPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();

        btn_logcreateAccount=findViewById(R.id.xlogcreateaccount);
        btn_logLogin=findViewById(R.id.xloglogin);
        txt_logEmailAddress=findViewById(R.id.xlogemail);
        txt_logPassword=findViewById(R.id.xlogpassword);

        //go to register activity
        btn_logcreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        //when user clicks on Login button
        btn_logLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extract the data and validate it
                if(txt_logEmailAddress.getText().toString().isEmpty()){
                    txt_logEmailAddress.setError("Email is missing");
                    return;
                }
                if(txt_logPassword.getText().toString().isEmpty()){
                    txt_logPassword.setError("Password is missing");
                    return;
                }

                //data is valid
                //login user
                firebaseAuth.signInWithEmailAndPassword(txt_logEmailAddress.getText().toString(), txt_logPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login is successful
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }
}