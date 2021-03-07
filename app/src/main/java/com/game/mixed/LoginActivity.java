package com.game.mixed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btn_logcreateAccount, btn_logLogin;
    private EditText txt_logEmailAddress, txt_logPassword;
    private FirebaseAuth firebaseAuth;
    private TextView txt_logforgotpass;
    private AlertDialog.Builder reset_alert;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        //for the reset password parameters
        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        btn_logcreateAccount=findViewById(R.id.xlogcreateaccount);
        btn_logLogin=findViewById(R.id.xloglogin);
        txt_logEmailAddress=findViewById(R.id.xlogemail);
        txt_logPassword=findViewById(R.id.xlogpassword);
        txt_logforgotpass=findViewById(R.id.xlogforgotpass);

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

        //forgot password
        txt_logforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start alertdialog
                final View view = inflater.inflate(R.layout.reset_popup, null);
                reset_alert.setTitle("Reset password?")
                        .setMessage("Enter your email to get password reset link.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //validate the email address
                                EditText txt_logForgotPassEmail = view.findViewById(R.id.xlogforgotPassEmail);
                                if(txt_logForgotPassEmail.getText().toString().isEmpty()){
                                    txt_logForgotPassEmail.setError("Please insert email address");
                                    return;
                                }
                                //send the reset link
                                firebaseAuth.sendPasswordResetEmail(txt_logForgotPassEmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(LoginActivity.this, "Reset email link sent.", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", null)
                        .setView(view)
                        .create().show();
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