package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText txt_regNickname, txt_regEmailAddress, txt_regPassword, txt_regConfPassword;
    private Button btn_regRegister, btn_regLogin;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_regNickname=findViewById(R.id.xregnickname);
        txt_regEmailAddress=findViewById(R.id.xregemail);
        txt_regPassword=findViewById(R.id.xregpassword);
        txt_regConfPassword=findViewById(R.id.xregconfpassword);
        btn_regRegister=findViewById(R.id.xregRegister);
        btn_regLogin=findViewById(R.id.xregLogin);

        fAuth= FirebaseAuth.getInstance();

        btn_regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        btn_regRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //retrieving data from the form
                String str_Nickname=txt_regNickname.getText().toString();
                String str_email=txt_regEmailAddress.getText().toString();
                String str_password=txt_regPassword.getText().toString();
                String str_confPassword=txt_regConfPassword.getText().toString();

                if(str_Nickname.isEmpty()){
                    txt_regNickname.setError("Please insert a nickname");
                    return;
                }

                if(str_email.isEmpty()){
                    txt_regEmailAddress.setError("Please insert an email");
                    return;
                }

                if(str_password.isEmpty()){
                    txt_regPassword.setError("Please insert a password");
                    return;
                }

                if(str_confPassword.isEmpty()){
                    txt_regConfPassword.setError("Please confirm the password");
                    return;
                }

                if(!str_password.equals(str_confPassword)){
                    txt_regConfPassword.setError("The password does not match");
                    return;
                }

                Toast.makeText(RegisterActivity.this, "The data has been validated", Toast.LENGTH_LONG).show();

                //add a listener that will display an error in case there is any error while registering
                fAuth.createUserWithEmailAndPassword(str_email, str_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //send user to Home activity
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}