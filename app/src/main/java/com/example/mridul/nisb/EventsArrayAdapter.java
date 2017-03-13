package com.example.mridul.nisb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by namit on 3/13/17.
 */

public class EventsArrayAdapter extends ArrayAdapter<JSONObject> {
    private final Context context;
    private final JSONObject[] values;

    public EventsArrayAdapter(Context context, JSONObject[] values) {
        super(context, R.layout.adapter_events, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if (convertView==null)
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_events,parent,false);

        TextView Title = (TextView) convertView.findViewById(R.id.event_json);

        /*try{*/
            JSONObject one = values[position];
            String evJson = String.valueOf(one);
            /*String author = one.getString("author");
            String title = one.getString("title");
            String content = one.getString("content");

            Title.setText(title);
            Extra.setText(author + " , " + date);
            Content.setText(content);
            Author.setText(author);
            Date.setText(date);*/
            Title.setText(evJson);

        /*}catch (JSONException j){

        }*/

        //0 - Blog Title
        //1 - Author
        //2 - Date
        //3 - Url

        return convertView;
    }


}

