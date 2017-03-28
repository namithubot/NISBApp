package in.nisb.nisbapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by mridul on 20/3/17.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExtraFunctions.setSBColor(getWindow(), Color.parseColor("#95a5a6"));

        Intent intent;

        if (NisbUser.isUserLogged(getApplicationContext()) || NisbUser.isGuestLogged(getApplicationContext()))
            intent = new Intent(this, MainActivity.class);

        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
