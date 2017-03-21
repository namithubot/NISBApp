package in.nisb.nisbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if User is Logged in then goto main directly
        if (NisbUser.isGuestLogged(getApplicationContext()) || NisbUser.isUserLogged(getApplicationContext()))
            launchMain();

        //signin by authenticating from website
        Button btn_signin = (Button) findViewById(R.id.login_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sign IN feature will soon be added.",Toast.LENGTH_SHORT).show();
            }
        });

        //do guest login
        TextView tv_guest = (TextView) findViewById(R.id.login_guest);
        tv_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NisbUser.doGuestLogin(getApplicationContext());
                launchMain();
            }
        });
    }

    //Handle back Button
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

    private void launchMain(){
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
}
