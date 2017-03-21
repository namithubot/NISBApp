package in.nisb.nisbapp;

import android.content.Intent;
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

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTabs();

    }

    public void setupTabs(){

        pager = (ViewPager) findViewById(R.id.main_pager);
        tabHost = (TabHost) findViewById(R.id.main_tabhost);

        pager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),getApplicationContext()));

        tabHost.setup();
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Home")
                .setIndicator("",getResources().getDrawable(R.drawable.nisblogo)).setContent(R.id.tab1);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Events")
                .setIndicator("",getResources().getDrawable(R.drawable.event)).setContent(R.id.tab1);
        TabHost.TabSpec spec3 = tabHost.newTabSpec("Blog")
                .setIndicator("",getResources().getDrawable(R.drawable.blog)).setContent(R.id.tab1);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                pager.setCurrentItem(tabHost.getCurrentTab(),true);
            }
        });

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (R.id.menu_about==id){
//            Intent i = new Intent(getApplicationContext(),AboutActivity.class);
//            startActivity(i);
        }
        else if (R.id.menu_settings==id){
            Toast.makeText(getApplicationContext(),"Settings will be added",Toast.LENGTH_SHORT).show();
        }
        else if (R.id.menu_logout==id){

        }


        return super.onOptionsItemSelected(item);
    }

}
