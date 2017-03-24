package in.nisb.nisbapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class BlogSingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        ExtraFunctions.setSBColor(getWindow(), Color.parseColor("#95a5a6"));

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String extra = i.getStringExtra("extra");
        String content = i.getStringExtra("content");

        TextView textView_title = (TextView) findViewById(R.id.blog_single_title);
        TextView textView_extra = (TextView) findViewById(R.id.blog_single_extra);
        textView_title.setText(title);
        textView_extra.setText(extra);

        WebView wv = (WebView) findViewById(R.id.blog_single_web);
        wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv.loadDataWithBaseURL(null, "<br>" + content + "<br><br><br>", "text/html", "utf-8", null);
    }
}
