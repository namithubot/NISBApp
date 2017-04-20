package in.nisb.nisbapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mridul on 28/3/17.
 */

public class NotificationFragment extends Fragment {
    View view;
    SwipeRefreshLayout sr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_notifs,container,false);

        loadList();
        TextView tv_clear = (TextView) view.findViewById(R.id.notifs_clear);
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NisbUser.clearNotifications(getContext());
                loadList();
            }
        });


        sr = (SwipeRefreshLayout) view.findViewById(R.id.notifs_refresh);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadList();
            }
        });

        return view;
    }
    void loadList(){
        ListView lv = (ListView) view.findViewById(R.id.notifs_list);
        JSONObject notifs_list[] = NisbUser.getNotifications(getContext());
        if (notifs_list!=null)
            lv.setAdapter(new NotificationAdapter(getContext(),notifs_list));
        else {
            String empty[] = {};
            lv.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.notifs_list_item,empty));
        }


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_data = (TextView) view.findViewById(R.id.notifs_data);
                try {
                    JSONObject jo = new JSONObject(tv_data.getText().toString());
                    String context = jo.getString("context");
                    Intent intent;
                    if (context.equals("blog-single")){
                        intent = new Intent(getContext(),BlogSingleActivity.class);
                        intent.putExtra("title",jo.getString("title"));
                        intent.putExtra("extra",jo.getString("extra"));
                        intent.putExtra("content",jo.getString("content"));
                        startActivity(intent);
                    }
                    if (context.equals("event-single")){
                        intent = new Intent(getContext(),EventSingleActivity.class);
                        intent.putExtra("id",jo.getString("id"));
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        ((SwipeRefreshLayout) view.findViewById(R.id.notifs_refresh)).setRefreshing(false);

    }

    private class NotificationAdapter extends ArrayAdapter<JSONObject>{
        Context c;
        JSONObject jo[];
        public NotificationAdapter(Context con,JSONObject j[]){
            super(con,R.layout.notifs_list_item,j);
            c =con;
            jo = j;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(c).inflate(R.layout.notifs_list_item, parent, false);
            TextView tv_title = (TextView) convertView.findViewById(R.id.notifs_text);
            TextView tv_data = (TextView) convertView.findViewById(R.id.notifs_data);
            try {
                String title = jo[position].getString("body");
                tv_title.setText(title);
                tv_data.setText(jo[position].toString());
            }catch (JSONException j){}

            return convertView;
        }
    }
}
