package in.nisb.nisbapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

/**
 * Created by mridul on 19/3/17.
 */

public class BlogFragment extends Fragment {

    View view;
    SwipeRefreshLayout sr;
    GridView gview;

    JSONObject blogs[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_blog,container,false);

        gview = (GridView) view.findViewById(R.id.blog_grid);
        getBlogs();

        sr = (SwipeRefreshLayout) view.findViewById(R.id.blog_refresh);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBlogs();
            }
        });

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(),BlogSingleActivity.class);
                i.putExtra("title",((TextView) view.findViewById(R.id.blog_adapter_title)).getText().toString());
                i.putExtra("extra",((TextView) view.findViewById(R.id.blog_adapter_extra)).getText().toString());
                i.putExtra("content",((TextView) view.findViewById(R.id.blog_adapter_content)).getText().toString());
                startActivity(i);
            }
        });

        return view;
    }

    public void getBlogs(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final String url ="https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fblog.nisb.in%2Findex.php%2Ffeed%2Frss";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray ja = jsonObj.getJSONArray("items");
                            JSONObject b[] = new JSONObject[ja.length()];
                            for (int i =0;i<ja.length();i++){
                                b[i] = ja.getJSONObject(i);
                            }
                            gview.setAdapter(new BlogFragment.BlogAdapter(view.getContext(),b));
                            ((SwipeRefreshLayout) view.findViewById(R.id.blog_refresh)).setRefreshing(false);
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


    private class BlogAdapter extends BaseAdapter{
            private Context mContext;
            private JSONObject b[];

            // Constructor
            public BlogAdapter(Context c,JSONObject[] a) {
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

                TextView tv_title,tv_extra,tv_text,tv_content;

                convertView=LayoutInflater.from(getContext()).inflate(R.layout.blog_grid_item,parent,false);

                tv_title = (TextView) convertView.findViewById(R.id.blog_adapter_title);
                tv_extra = (TextView) convertView.findViewById(R.id.blog_adapter_extra);
                tv_text = (TextView) convertView.findViewById(R.id.blog_adapter_text);
                tv_content = (TextView) convertView.findViewById(R.id.blog_adapter_content);

                try {
                    tv_title.setText(b[position].getString("title"));
                    tv_extra.setText(b[position].getString("author") + "  " + b[position].getString("pubDate"));
                    tv_text.setText(Html.fromHtml(b[position].getString("description").substring(0,180)+"..."));
                    tv_content.setText(b[position].getString("content").replace("<img","<img width='100%'"));

                } catch (JSONException e) {
                    Log.d("JSON ERROR","");
                    e.printStackTrace();}

                return convertView;
            }
    }

}
