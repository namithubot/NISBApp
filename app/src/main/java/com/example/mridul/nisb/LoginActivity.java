package com.example.mridul.nisb;

import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
    AutoCompleteTextView emailTV;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (NisbUser.isGuestLogged(getApplicationContext())){
            ToastIt("You are Logged in as Guest");
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }

        if (NisbUser.isUserLogged(getApplicationContext())){
            ToastIt("You are Logged in as Guest");
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }

        emailTV = (AutoCompleteTextView) findViewById(R.id.email);
        addEmailAutoComplete();

        firebaseAuth = FirebaseAuth.getInstance();

        String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();

        Button btn_sign_in = (Button) findViewById(R.id.email_sign_in_button);
        btn_sign_in.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
                loginUser(email,password);
            }
        });

        Button btn_register = (Button) findViewById(R.id.email_register_button);
        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
                String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
                registerUser(email,password);
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

    //add suggestions from mobile
    private void addEmailAutoComplete() {

        Account[] accounts = AccountManager.get(this).getAccounts();
        Set<String> emailSet = new HashSet<String>();
        for (Account account : accounts) {
            if (EMAIL_PATTERN.matcher(account.name).matches()) {
                emailSet.add(account.name);
            }
        }
        emailTV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>(emailSet)));

    }

    private void ToastIt(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
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
                    ToastIt("Could not Log in.");
                }
            }
        });
    }

    public void registerUser(String email,String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //new User has been created
                if (task.isSuccessful()){
                    ToastIt("Successfully Registered");
                    loginUser(((EditText)findViewById(R.id.email)).getText().toString(),((EditText)findViewById(R.id.password)).getText().toString());
                }
                else {
                    ToastIt("Could not Register with the given credentials");
                }
            }
        });
    }

    //Close if back button pressed
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

