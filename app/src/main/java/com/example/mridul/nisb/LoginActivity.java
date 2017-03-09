package com.example.mridul.nisb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (NisbUser.isGuestLogged(getApplicationContext())){
            System.out.println("Guest Logged in");
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }

        if (NisbUser.isUserLogged(getApplicationContext())){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        Button btnreg = (Button) findViewById(R.id.email_sign_in_button);
        btnreg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
                loginUser(email,password);

            }
        });

        Button btnguest = (Button) findViewById(R.id.btn_guest);
        btnguest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                NisbUser.doGuestLogin(getApplicationContext());
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void loginUser(String email,String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    NisbUser.doUserLogin(getApplicationContext(),((EditText)findViewById(R.id.email)).getText().toString());
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"New User will be created",Toast.LENGTH_SHORT);
                    registerUser(((EditText)findViewById(R.id.email)).getText().toString(),((EditText)findViewById(R.id.password)).getText().toString());
                }
            }
        });
    }

    public void registerUser(String email,String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //new User has been created
                Toast.makeText(getApplicationContext(),"New User has been Created",Toast.LENGTH_SHORT).show();
                if (task.isSuccessful()){
                    loginUser(((EditText)findViewById(R.id.email)).getText().toString(),((EditText)findViewById(R.id.password)).getText().toString());
                }

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        return super.onKeyDown(keyCode, event);
    }


}

