package in.nisb.nisbapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;




import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTabs();
    }


    public void setupTabs(){

        pager = (ViewPager) findViewById(R.id.main_pager);
        tabLayout = (TabLayout) findViewById(R.id.main_tablayout);

        pager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),getApplicationContext()));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.drawable.nisblogo24);
        tabLayout.getTabAt(1).setText("Events"); //.setIcon(R.drawable.event);
        tabLayout.getTabAt(2).setText("Blog"); //.setIcon(R.drawable.blog);
        tabLayout.getTabAt(3).setIcon(R.drawable.notification);
    }

    //Handle back Button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        return super.onKeyDown(keyCode, event);
    }

}
