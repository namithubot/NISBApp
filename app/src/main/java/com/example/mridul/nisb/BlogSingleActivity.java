package com.example.mridul.nisb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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
        String title = i.getStringExtra("title");
        String extra = "by " + i.getStringExtra("author") + " on " + i.getStringExtra("date");
        String content = i.getStringExtra("content").replace("<img","<img style=\" width:100%; \"");


        System.out.println(content);

        TextView textView_title = (TextView) findViewById(R.id.blog_single_title);
        TextView textView_extra = (TextView) findViewById(R.id.blog_single_extra);

        textView_title.setText(title);
        textView_extra.setText(extra);

        WebView wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);


    }

    private void ToastIt(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
    }


}
