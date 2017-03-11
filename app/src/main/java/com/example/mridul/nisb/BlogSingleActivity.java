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
        String content = i.getStringExtra("content");

        TextView textView_title = (TextView) findViewById(R.id.blog_single_title);
        final TextView textView_desc = (TextView) findViewById(R.id.blog_single_desc);

        textView_title.setText(title);
        Spanned spanned = Html.fromHtml(content, new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        ToastIt(source);


                        LevelListDrawable d = new LevelListDrawable();
                        Drawable empty = getResources().getDrawable(R.drawable.abc_btn_check_material);;
                        d.addLevel(0, 0, empty);
                        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                        new ImageGetterAsyncTask(getApplicationContext(), source, d).execute(textView_desc);

                        return d;
                    }
                }, null);
        textView_desc.setText(spanned);

    //    retriveBlog(url);

    }

    private void ToastIt(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
    }


    class ImageGetterAsyncTask extends AsyncTask<TextView, Void, Bitmap> {


        private LevelListDrawable levelListDrawable;
        private Context context;
        private String source;
        private TextView t;

        public ImageGetterAsyncTask(Context context, String source, LevelListDrawable levelListDrawable) {
            this.context = context;
            this.source = source;
            System.out.println("IMAGE SOURCE  =  " + source);
            this.levelListDrawable = levelListDrawable;
        }

        @Override
        protected Bitmap doInBackground(TextView... params) {
            t = params[0];
            try {
                //Log.d(LOG_CAT, "Downloading the image from: " + source);
                System.out.println("DOWNLOADING PIC");
                return Picasso.with(context).load(source).get();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            //System.out.println("GOT THE PIC");
            try {
                Drawable d = new BitmapDrawable(context.getResources(), bitmap);
                System.out.println( "GOT THE PIC ");
                Point size = new Point();
                //((Activity) context).getWindowManager().getDefaultDisplay().getSize(size);
                // Lets calculate the ratio according to the screen width in px
                //int multiplier = size.x / bitmap.getWidth();
                int multiplier=1;
                //Log.d(LOG_CAT, "multiplier: " + multiplier);
                levelListDrawable.addLevel(1, 1, d);
                // Set bounds width  and height according to the bitmap resized size
                levelListDrawable.setBounds(0, 0, bitmap.getWidth() * multiplier, bitmap.getHeight() * multiplier);
                levelListDrawable.setLevel(1);

                t.setText(t.getText()); // invalidate() doesn't work correctly...
            } catch (Exception e) { /* Like a null bitmap, etc. */
            System.out.print("ERROR IMG " + e.getLocalizedMessage());}
        }
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
