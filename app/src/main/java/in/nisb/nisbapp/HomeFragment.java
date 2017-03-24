package in.nisb.nisbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mridul on 19/3/17.
 */

public class HomeFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home,container,false);

        TextView gr = (TextView) view.findViewById(R.id.home_user_greet);
        if (NisbUser.isUserLogged(getContext())==true)
            gr.setText("You are Logged in as " + NisbUser.getUserName(getContext()));
        if (NisbUser.isGuestLogged(getContext())==true)
            gr.setText("You are Logged in as Guest");



        //about button
        Button btn_about = (Button) view.findViewById(R.id.home_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),AboutActivity.class);
                startActivity(i);
            }
        });

        //contact button
        Button btn_contact = (Button) view.findViewById(R.id.home_contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ContactActivity.class);
                startActivity(i);

            }
        });

        //Account button
        Button btn_account = (Button) view.findViewById(R.id.home_account);
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),AccountActivity.class);
                startActivity(i);
            }
        });

        //Ankura button
        Button btn_ankura = (Button) view.findViewById(R.id.home_ankura);
        btn_ankura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),AnkuraActivity.class);
                startActivity(i);
            }
        });

        WebView wv_updates = (WebView) view.findViewById(R.id.home_update_web);
        wv_updates.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_updates.setWebViewClient(new WebViewClient());
        wv_updates.loadUrl("http://nisb.in/jsonupdates");

        return view;
    }
}
