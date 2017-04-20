package in.nisb.nisbapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

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

/**
 * Created by mridul on 19/3/17.
 */

public class EventFragment extends Fragment {

    View view;

    GridView gview_all;
    GridView gview_cs;
    SwipeRefreshLayout sr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_events, container, false);


        final TabHost t = (TabHost) view.findViewById(R.id.events_tabhost);
        t.setup();

        sr = (SwipeRefreshLayout) view.findViewById(R.id.events_refresh);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadGrids();
            }
        });
        sr = (SwipeRefreshLayout) view.findViewById(R.id.events_refresh2);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadGrids();
            }
        });

        TabHost.TabSpec spec1 = t.newTabSpec("all");
        spec1.setIndicator("All NISB Events");
        spec1.setContent(R.id.events_all);
        t.addTab(spec1);

        TabHost.TabSpec spec2 = t.newTabSpec("cs");
        spec2.setIndicator("Computer Society");
        spec2.setContent(R.id.events_cs);
        t.addTab(spec2);

        t.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#dddddd"));

        t.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("cs")){
                    t.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#ffffff"));
                    t.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#dddddd"));
                }
                else {
                    t.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#ffffff"));
                    t.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#dddddd"));
                }
                //Toast.makeText(getContext(),tabId,Toast.LENGTH_SHORT).show();
            }
        });

        loadGrids();

        return view;
    }

    private void loadGrids() {
        gview_all = (GridView) view.findViewById(R.id.events_all_grid);
        gview_cs = (GridView) view.findViewById(R.id.events_cs_grid);

        getEvents();
        getCSEvents();

        gview_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(),EventSingleActivity.class);
                i.putExtra("id",((TextView)view.findViewById(R.id.events_adapter_id)).getText());
                startActivity(i);
            }
        });
        gview_cs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(),EventSingleActivity.class);
                i.putExtra("id",((TextView)view.findViewById(R.id.events_adapter_id)).getText());
                startActivity(i);
            }
        });

    }




    void getEvents(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String fields[] = {"id","name","cover","start_time"};
        //final String url ="https://graph.facebook.com/v2.8/nieieeestudentbranch/events?fields=id%2C%20name%2C%20cover%2Cstart_time&format=json&sdk=android&access_token=1327383467301154|YDfQ94wTelbffydG5XrnanHnqu0";
        final  String url = ExtraFunctions.fbUrlGenerator("nieieeestudentbranch/events",fields);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("FB RESPONSE "+response);
                        // Display the first 500 characters of the response string.

                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray ja = jsonObj.getJSONArray("data");
                            JSONObject b[] = new JSONObject[ja.length()];
                            for (int i =0;i<ja.length();i++){
                                b[i] = ja.getJSONObject(i);
                            }
                            gview_all.setAdapter(new EventsAllAdapter(view.getContext(),b));
                            ((SwipeRefreshLayout) view.findViewById(R.id.events_refresh)).setRefreshing(false);
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

    void getCSEvents(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url ="https://graph.facebook.com/v2.8//nieieeecomputersociety/events?fields=id%2C%20name%2C%20cover%2Cstart_time&format=json&sdk=android&access_token=1327383467301154|YDfQ94wTelbffydG5XrnanHnqu0";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("FB RESPONSE "+response);
                        // Display the first 500 characters of the response string.

                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray ja = jsonObj.getJSONArray("data");
                            JSONObject b[] = new JSONObject[ja.length()];
                            for (int i =0;i<ja.length();i++){
                                b[i] = ja.getJSONObject(i);
                            }
                            gview_cs.setAdapter(new EventsAllAdapter(view.getContext(),b));
                            ((SwipeRefreshLayout) view.findViewById(R.id.events_refresh2)).setRefreshing(false);
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

    private class EventsCsAdapter extends BaseAdapter {
        private Context mContext;

        // Constructor
        public EventsCsAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return 0; //mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }
            else
            {
                imageView = (ImageView) convertView;
            }
//        imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }
    }

    private class EventsAllAdapter extends BaseAdapter {
        private Context mContext;
        private JSONObject b[];

        // Constructor
        public EventsAllAdapter(Context c,JSONObject[] a) {
            mContext = c;
            b=a;
        }

        public int getCount() {
            //while (b==null){ }
            return b.length;
        }

        public Object getItem(int position) {
            return b[position];
        }

        public long getItemId(int position) {
            return position;
        }


        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            TextView t1,t2;

            convertView=LayoutInflater.from(getContext()).inflate(R.layout.events_grid_item,parent,false);

            imageView = (ImageView) convertView.findViewById(R.id.events_adapter_image);
            t1 = (TextView) convertView.findViewById(R.id.events_adapter_title);
            t2 = (TextView) convertView.findViewById(R.id.events_adapter_id);

            try {
                t1.setText(b[position].getString("name"));
                t2.setText(b[position].getString("id"));
                String cover = b[position].getJSONObject("cover").getString("source");
                imageView.setAdjustViewBounds(true);
                Picasso.with(mContext).load(cover).into(imageView);

            } catch (JSONException e) {
                Log.d("JSON ERROR","");
                e.printStackTrace();}


            return convertView;
        }


    }
}
