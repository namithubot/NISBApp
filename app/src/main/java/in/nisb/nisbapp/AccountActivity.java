package in.nisb.nisbapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //set status bar color
        ExtraFunctions.setSBColor(getWindow(),Color.parseColor("#95a5a6"));

        if (NisbUser.isUserLogged(getApplicationContext())){
            loadAccountInfo();
        }
        else {
            TableLayout tb = (TableLayout) findViewById(R.id.account_table);
            tb.setVisibility(View.INVISIBLE);
            tb.setLayoutParams(new TableLayout.LayoutParams(1,1));
        }

        Button btn_logout = (Button) findViewById(R.id.account_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NisbUser.doUserLogout(getApplicationContext());
                NisbUser.doGuestLogout(getApplicationContext());
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });


    }

    private void loadAccountInfo(){
        TextView tvName = (TextView) findViewById(R.id.account_name);
        TextView tvEmail = (TextView) findViewById(R.id.account_email);
        TextView tvIeeeno = (TextView) findViewById(R.id.account_ieeeno);
        TextView tvMobile = (TextView) findViewById(R.id.account_mobile);
        TextView tvBranch = (TextView) findViewById(R.id.account_branch);
        CheckBox cbCS = (CheckBox) findViewById(R.id.account_cs);

        try {
            JSONObject jo = NisbUser.getUserData(getApplicationContext());
            tvName.setText(jo.getString("name"));
            tvEmail.setText(jo.getString("emailAddress"));
            tvIeeeno.setText("" + jo.getInt("ieeeno"));
            tvMobile.setText(jo.getString("phone"));
            tvBranch.setText(jo.getString("branch"));
            cbCS.setChecked(jo.getBoolean("isCS"));
        }catch (JSONException e){}


    }
}
