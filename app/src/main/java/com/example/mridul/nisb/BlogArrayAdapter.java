package com.example.mridul.nisb;

/**
 * Created by mridul on 5/3/17.
 */

        import com.example.mridul.nisb.R;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import org.json.JSONException;
        import org.json.JSONObject;
        import org.w3c.dom.Text;

public class BlogArrayAdapter extends ArrayAdapter<JSONObject>{
    private final Context context;
    private final JSONObject[] values;

    public BlogArrayAdapter(Context context, JSONObject[] values) {
        super(context, R.layout.adapter_blog_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if (convertView==null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_blog_item,parent,false);

        TextView Title = (TextView) convertView.findViewById(R.id.blog_adapter_title);
        TextView Extra = (TextView) convertView.findViewById(R.id.blog_adapter_extra);
        TextView Content = (TextView) convertView.findViewById(R.id.blog_adapter_content);
        TextView Author = (TextView) convertView.findViewById(R.id.blog_adapter_author);
        TextView Date = (TextView) convertView.findViewById(R.id.blog_adapter_date);

        try{
            JSONObject one = values[position];
            String date = one.getString("pubDate");
            String author = one.getString("author");
            String title = one.getString("title");
            String content = one.getString("content");

            Title.setText(title);
            Extra.setText(author + " , " + date);
            Content.setText(content);
            Author.setText(author);
            Date.setText(date);

        }catch (JSONException j){

        }

        //0 - Blog Title
        //1 - Author
        //2 - Date
        //3 - Url

        return convertView;
    }


}
