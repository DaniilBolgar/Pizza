package com.daniil.pizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText vpassword;
    private Button signinbtn,signupbtn;
    private FirebaseAuth authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        vpassword = findViewById(R.id.vpassword);
        signinbtn = findViewById(R.id.signinbtn);
        signupbtn = findViewById(R.id.singupbtn);

        authenticator = FirebaseAuth.getInstance();

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(email.getText().toString())){
                    Log.d("firebaseauth","test");
                    if(vpassword.getText().toString().equals(password.getText().toString())){
                        authenticator.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.d("firebaseauth","success!");
                                }
                                else{
                                    Log.d("firebaseauth","oops");
                                    // If sign in fails, display a message to the user.
                                    Log.w("firebaseauth", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"The passwords you have entered do not match! Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"The email you have entered is invalid. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    private boolean validate(String email){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}