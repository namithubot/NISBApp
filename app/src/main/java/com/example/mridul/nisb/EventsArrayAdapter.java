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
        super(context, R.layout.adapter_event_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if (convertView==null)
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_event_item,parent,false);

        TextView Title = (TextView) convertView.findViewById(R.id.event_adapter_title);
        TextView Eid = (TextView) convertView.findViewById(R.id.event_adapter_id);

        try{
            JSONObject one = values[position];
            //String evJson = String.valueOf(one);

            String title = one.getString("name");
            String eventid = one.getString("id");

            Title.setText(title);
            Eid.setText(eventid);

        }catch (JSONException j){

        }

        return convertView;
    }


}

