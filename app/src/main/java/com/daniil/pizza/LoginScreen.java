package com.daniil.pizza;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginScreen extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText vpassword;
    private Button signinbtn,signupbtn;
    private FirebaseAuth authenticator;
    private String UID;
    private SignInButton googleBtn;
    private GoogleSignInClient gClient;
    private GoogleSignInOptions gOptions;
    private static final int gSigninCode = 9001;
    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharPref = getPreferences(Context.MODE_PRIVATE);
        isLoggedIn = sharPref.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            Intent intent = new Intent(this, MainScreen.class);
            startActivity(intent);

        } else {
            setContentView(R.layout.activity_main);
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            vpassword = findViewById(R.id.vpassword);
            signinbtn = findViewById(R.id.signinbtn);
            signupbtn = findViewById(R.id.singupbtn);
            googleBtn = findViewById(R.id.sign_in_button);

            authenticator = FirebaseAuth.getInstance();

            GoogleSignInOptions.Builder googleSignInOptionsBuilder;
            gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            gClient = GoogleSignIn.getClient(this, gOptions);

            signupbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validate(email.getText().toString())) {
                        Log.d("firebaseauth", "test");
                        if (vpassword.getText().toString().equals(password.getText().toString())) {
                            authenticator.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("firebaseauth", "success!");
                                        UID = task.getResult().getUser().getUid();
                                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("UID", UID).apply();
                                        editor.putBoolean("isLoggedIn", true).apply();
                                    } else {
                                        Log.d("firebaseauth", "oops");
                                        // If sign in fails, display a message to the user.
                                        Log.w("firebaseauth", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), task.getException().toString(),
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "The passwords you have entered do not match! Please try again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "The email you have entered is invalid. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            signinbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
                    ViewGroup vGroup = findViewById(R.id.content);
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.authentication_popup, vGroup, false);
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    Button b = dialogView.findViewById(R.id.signinDialog);
                    final EditText email = dialogView.findViewById(R.id.emailTxt);
                    final EditText password = dialogView.findViewById(R.id.passwordTxt);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("button","success!");
                            if(!email.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")){
                                authenticator.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            UID = task.getResult().getUser().getUid();
                                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("UID", UID).apply();
                                            editor.putBoolean("isLoggedIn", true).apply();
                                            Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                                            startActivity(intent);
                                        } else{
                                            Toast.makeText(getApplicationContext(), task.getException().toString(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            } else{
                                Toast.makeText(getApplicationContext(),"Please Enter Your Email and Password",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    alertDialog.show();
                }
            });

            googleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signInIntent = gClient.getSignInIntent();
                    startActivityForResult(signInIntent, gSigninCode);
                }
            });

        }
    }
    private boolean validate(String email){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == gSigninCode){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("firebaseauth", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e){
                Log.w("firebaseauth", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential cred = GoogleAuthProvider.getCredential(idToken, null);
        authenticator.signInWithCredential(cred).addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = authenticator.getCurrentUser();
                    Log.d("firebaseauth", "success! " + user.getUid());
                    UID = task.getResult().getUser().getUid();
                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("UID",UID).apply();
                    editor.putBoolean("isLoggedIn",true).apply();
                } else {
                    Log.w("firebaseauth", "createUserWithGoogleFailed", task.getException());
                    Toast.makeText(getApplicationContext(), task.getException().toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}