package com.example.mridul.nisb;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    String ret="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText t = (EditText) findViewById(R.id.main_editText);
        t.setText(FirebaseInstanceId.getInstance().getToken());
        System.out.println("IID Token = " + FirebaseInstanceId.getInstance().getToken());
        setUserToken(FirebaseInstanceId.getInstance().getToken());

        loadTabs();

        retriveBlogs();

    }


    //update user's device info to the realtime db
    public void setUserToken(String token){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email = NisbUser.getUserEmail(getApplicationContext());
        DatabaseReference myRef = database.getReference("user");
        myRef.child(md5(email)).setValue(token);
        System.out.print("Changing Value for " + email +" user/" + md5(email));

    }

    public static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    //Logout
    public void logout(){
        NisbUser.doGuestLogout(getApplicationContext());
        NisbUser.doUserLogout(getApplicationContext());
        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
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

    //Load the Tabbed View
    public void loadTabs(){

        TabHost t = (TabHost) findViewById(R.id.tabhost1);
        t.setup();

        TabHost.TabSpec spec1 = t.newTabSpec("Home");
        spec1.setIndicator("Home");
        spec1.setContent(R.id.tab_home);
        t.addTab(spec1);


//        TabHost.TabSpec spec3 = t.newTabSpec("Team");
//        spec3.setIndicator("Team");
//        spec3.setContent(R.id.tab_team);
//        t.addTab(spec3);
//
//        TabHost.TabSpec spec4 = t.newTabSpec("Gallery");
//        spec4.setIndicator("Gallery");
//        spec4.setContent(R.id.tab_gallery);
//        t.addTab(spec4);

        TabHost.TabSpec spec5 = t.newTabSpec("Events");
        spec5.setIndicator("Events");
        spec5.setContent(R.id.tab_events);
        t.addTab(spec5);

        TabHost.TabSpec spec6 = t.newTabSpec("Blog");
        spec6.setIndicator("Blog");
        spec6.setContent(R.id.tab_blog);
        t.addTab(spec6);

//        TabHost.TabSpec spec7 = t.newTabSpec("Roadmap");
//        spec7.setIndicator("Roadmap");
//        spec7.setContent(R.id.tab_roadmap);
//        t.addTab(spec7);
//
//        TabHost.TabSpec spec8 = t.newTabSpec("wie");
//        spec8.setIndicator("Women in Engineering");
//        spec8.setContent(R.id.tab_wie);
//        t.addTab(spec8);
//
//        TabHost.TabSpec spec9 = t.newTabSpec("cs");
//        spec9.setIndicator("Computer Society");
//        spec9.setContent(R.id.tab_cs);
//        t.addTab(spec9);
//
//        TabHost.TabSpec spec10 = t.newTabSpec("fg");
//        spec10.setIndicator("Focus Group");
//        spec10.setContent(R.id.tab_fg);
//        t.addTab(spec10);


        t.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId=="tab_blog")
                    retriveBlogs();

            }
        });
    }

    //Load and Handle Click events
    public void loadBlogPosts(JSONObject[] Blogs){

        ListView main_list = (ListView) findViewById(R.id.blog_list);
        main_list.setAdapter(new BlogArrayAdapter(this,Blogs));


        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tu = (TextView) view.findViewById(R.id.url);
                TextView tt = (TextView) view.findViewById(R.id.title);

                Intent i=new Intent(getApplicationContext(),BlogSingleActivity.class);
                i.putExtra("title", tt.getText());
                i.putExtra("content",tu.getText());
                startActivityForResult(i,1);
            }
        });
    }

    //Send Request to url and render blogs
    public String retriveBlogs(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fblog.nisb.in%2Findex.php%2Ffeed%2Frss";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        ret=(response);

                        try{
                            JSONObject jsonObj = new JSONObject(ret);
                            JSONArray ja = jsonObj.getJSONArray("items");
                            JSONObject a[] = new JSONObject[ja.length()];
                            for (int i =0;i<ja.length();i++){
                                a[i] = ja.getJSONObject(i);
                            }

                            loadBlogPosts(a);
                        }
                        catch (JSONException j){

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ret= ("That didn't work!");
                    }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return ret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (R.id.menu_about==id){
            Intent i = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(i);
        }
        else if (R.id.menu_settings==id){
            Toast t = Toast.makeText(getApplicationContext(),"Settings will be added",Toast.LENGTH_SHORT);
            t.show();
        }
        else if (R.id.menu_logout==id){
            logout();
        }


        return super.onOptionsItemSelected(item);
    }
}
