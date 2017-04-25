package in.nisb.nisbapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ExtraFunctions.setSBColor(getWindow(), Color.parseColor("#95a5a6"));

        //if User is Logged in then goto main directly
        if (NisbUser.isGuestLogged(getApplicationContext()) || NisbUser.isUserLogged(getApplicationContext()))
            launchMain();

        //signin by authenticating from website
        final Button btn_signin = (Button) findViewById(R.id.login_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t_email = (TextView) findViewById(R.id.login_email);
                TextView t_ieeeno = (TextView) findViewById(R.id.login_ieeeno);
                authenticateUser(t_email.getText().toString(),t_ieeeno.getText().toString());
                btn_signin.setText("Loggin in..");
                btn_signin.setEnabled(false);
                //Toast.makeText(getApplicationContext(),"Sign IN feature will soon be added.",Toast.LENGTH_SHORT).show();
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

    //get auth details from nisb.in
    private void authenticateUser(final String email,final String ieeeno){

        String REGISTER_URL = "http://nisb.in/appSignIn/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try{
                            JSONObject jo = new JSONObject(response);
                            if (jo.getString("success").equals("true")){
                                String name = jo.getJSONObject("userData").getString("name");
                                String data = jo.getJSONObject("userData").toString();
                                NisbUser.doUserLogin(getApplicationContext(),email,ieeeno,name,data);
                                launchMain();
                            }
                            else{
                                Button btn_signin = ((Button) findViewById(R.id.login_signin));
                                btn_signin.setText("Login");
                                btn_signin.setEnabled(true);
                                Toast.makeText(getApplicationContext(),"Check the details.",Toast.LENGTH_LONG).show();
                            }


                        }catch(JSONException e){}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Button btn_signin = ((Button) findViewById(R.id.login_signin));
                        btn_signin.setText("Login");
                        btn_signin.setEnabled(true);
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("ieeeno",ieeeno);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
        i.putExtra("context","login");
        startActivity(i);
    }
}
