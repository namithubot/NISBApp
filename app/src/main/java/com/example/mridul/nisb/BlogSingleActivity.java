package com.example.mridul.nisb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class BlogSingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        Intent i = getIntent();
        String url = i.getStringExtra("link");
        retriveBlog(url);

    }


    public void retriveBlog(final String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String ret=(response);
                        System.out.println(ret);
                        try{
                            JSONObject obj = new JSONObject(ret);

                                String id = obj.getString("id");
                                String userid = obj.getString("userId");
                                String title = obj.getString("title");
                                String body = obj.getString("body");

                            TextView textView_title = (TextView) findViewById(R.id.blog_single_title);
                            TextView textView_desc = (TextView) findViewById(R.id.blog_single_desc);
                            textView_title.setText(title);
                            textView_desc.setText(body);

                        }
                        catch (JSONException j){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}
