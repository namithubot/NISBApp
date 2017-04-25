package in.nisb.nisbapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class EventSingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_single);

        ExtraFunctions.setSBColor(getWindow(), Color.parseColor("#95a5a6"));

        Intent i = getIntent();
        String id = i.getStringExtra("id");

        loadEvent(id);
    }

    public void loadEvent(String eventid){
        RequestQueue queue = Volley.newRequestQueue(this);
        String fields[] = {"name","photos","place","start_time","description","cover", "ticket_uri"};
        //Toast.makeText(getApplicationContext(),"JOIN OUT" + strjoin("%2C",fields),Toast.LENGTH_SHORT).show();
        //final String url ="https://graph.facebook.com/" + eventid + "?fields="+  strjoin("%2C",fields) +"&access_token=1327383467301154%7CYDfQ94wTelbffydG5XrnanHnqu0";
        final String url = ExtraFunctions.fbUrlGenerator(eventid,fields);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String ret=(response);

                        try{
                            JSONObject jsonObj = new JSONObject(ret);

                            String title="",cover="",date="",place="",desc="",attending="",interested="";

                            TextView tv_title = (TextView) findViewById(R.id.events_single_title);
                            TextView tv_extra = (TextView) findViewById(R.id.events_single_extra);
                            TextView tv_text = (TextView) findViewById(R.id.events_single_text);
                            Button btnreg = (Button) findViewById(R.id.events_register);
//                            TextView tv_attending = (TextView) findViewById(R.id.events_single_attending);

                             title = jsonObj.getString("name");
                            if (jsonObj.has("cover"))
                             cover = jsonObj.getJSONObject("cover").getString("source");
                             date = jsonObj.getString("start_time");
                            if (jsonObj.has("description"))
                             desc = jsonObj.getString("description");
                            if (jsonObj.has("place"))
                             place = jsonObj.getJSONObject("place").getString("name");
                            if (jsonObj.has("ticket_uri")){
                                final String ticket_uri = jsonObj.getString("ticket_uri");
                                btnreg.setVisibility(View.VISIBLE);
                                btnreg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(ticket_uri));
                                        startActivity(i);
                                    }
                                });
                            }
//                          if (jsonObj.has("attending_count") && jsonObj.has("interested_count")) {
//                                attending = jsonObj.getString("attending_count");
//                                interested = jsonObj.getString("interested_count");
//                                tv_attending.setText(attending + " people Attended and " + interested + " people Interested");
//                          }

                            ImageView imageView = (ImageView) findViewById(R.id.events_single_cover);
                            Picasso.with(getApplicationContext()).load(cover).into(imageView);

                            tv_title.setText(title);
                            tv_extra.setText("Date : " + date.substring(0,10) +" , Place : " + place);
                            tv_text.setText(desc);

                        }
                        catch (JSONException j){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
