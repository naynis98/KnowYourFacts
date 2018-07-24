package com.example.a16033774.knowyourfacts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Fragment> al;
    MyFragmentPagerAdapter adapter;
    ViewPager vPager;

    Button btnReadLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vPager = (ViewPager) findViewById(R.id.viewpager1);

        btnReadLater = (Button) findViewById(R.id.btnReadLater);

        FragmentManager fm = getSupportFragmentManager();

        al = new ArrayList<Fragment>();
        al.add(new Frag1());
        al.add(new Frag2());
        al.add(new Frag3());


        adapter = new MyFragmentPagerAdapter(fm, al);

        vPager.setAdapter(adapter);

        btnReadLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent i = new Intent(MainActivity.this,NotiReciever.class);
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this,1,i,0);
                Calendar now = Calendar.getInstance();
                now.add(Calendar.SECOND,5*60);
                alarm.set(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(),pi);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.itemPrevious){
            if(vPager.getCurrentItem()> 0){
                vPager.setCurrentItem(vPager.getCurrentItem() -1, true);
            }
        } else if (item.getItemId() == R.id.itemNext){
            if(vPager.getCurrentItem() < vPager.getChildCount() -1 ) {
                vPager.setCurrentItem(vPager.getCurrentItem() +1, true);
            }
        } else if (item.getItemId() == R.id.itemRandom) {
            Random r = new Random();
            int page = r.nextInt(vPager.getChildCount());
            vPager.setCurrentItem(page,true);
        }
        return super.onOptionsItemSelected(item);
    }

}
