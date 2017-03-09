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

        import org.w3c.dom.Text;

public class BlogArrayAdapter extends ArrayAdapter<String>{
    private final Context context;
    private final String[] values;

    public BlogArrayAdapter(Context context, String[] values) {
        super(context, R.layout.adapter_blog_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if (convertView==null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_blog_item,parent,false);

        TextView Title = (TextView) convertView.findViewById(R.id.title);
        TextView Extra = (TextView) convertView.findViewById(R.id.extra);
        TextView Blog_url = (TextView) convertView.findViewById(R.id.url);

        String data = values[position];
        String data_list[] = data.split(",");

        Title.setText(data_list[0]);
        Extra.setText(data_list[1] + " , " + data_list[2]);
        Blog_url.setText(data_list[3]);
        //0 - Blog Title
        //1 - Author
        //2 - Date
        //3 - Url

        return convertView;
    }


}
