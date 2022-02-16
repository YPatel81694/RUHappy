package main.ruhappy;

import android.annotation.TargetApi;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Calendar;

public class barindetail extends  AppCompatActivity {
    private ViewPager viewPager;
    private TextView openClose;
    private TextView hours;
    JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barindetail);

       // setStatusBarTranslucent(true);
        if(Build.VERSION.SDK_INT > 21)
        {
            getWindow().setStatusBarColor(getResources().getColor(R.color.cream));
        }


        final BarLibrary barSelected = (BarLibrary) getIntent().getSerializableExtra("barObject");
        int day = getIntent().getIntExtra("day", 1);

        openClose = (TextView) findViewById(R.id.textView3);
        hours = (TextView) findViewById(R.id.textView4);

        viewPager = (ViewPager) findViewById(R.id.pager);
        try {
            data = sender(day, barSelected);
            checkTime(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TabPageAdapter tpa = new TabPageAdapter(getSupportFragmentManager(), barSelected, data);
        viewPager.setAdapter(tpa);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        TextView viewMap = (TextView) findViewById(R.id.textView5);
        viewMap.setClickable(true);
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMap(barSelected.getLatitude(), barSelected.getLongitude(), barSelected.getAddress());
            }
        });
        TextView allbars = (TextView) findViewById(R.id.textViewBID);
        final ImageView barImage = (ImageView) findViewById(R.id.imageView);
        Typeface cursive = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        allbars.setTypeface(cursive);
        allbars.setText(barSelected.getName());

        Glide.with(this).load(barSelected.getBarImages()).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(barImage);


       /* Picasso.with(this).load(barSelected.getBarImages()).networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.loading).fit().centerCrop().into(barImage, new Callback() {

            @Override
            public void onSuccess() {


            }

            @Override
            public void onError() {
                Picasso.with(getApplicationContext()).load(barSelected.getBarImages())
                        .placeholder(R.drawable.loading).fit().centerCrop().into(barImage);
            }
        });*/




    }

    void startMap(double lat, double longitude, String address){
        Uri location = Uri.parse("geo:"+lat+","+longitude+"?q="+address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }
    JSONObject sender (int day, BarLibrary object) throws JSONException {
        JSONObject data;
        switch(day){
            case(Calendar.SUNDAY):
                data = new JSONObject(object.getSunday());
                hours.setText(data.getString("hoursToday"));
                return data;
            case(Calendar.MONDAY):
                data = new JSONObject(object.getMonday());
                hours.setText(data.getString("hoursToday"));
                return data;
            case(Calendar.TUESDAY):
                data = new JSONObject(object.getTuesday());
                hours.setText(data.getString("hoursToday"));
                return data;
            case(Calendar.WEDNESDAY):
                data = new JSONObject(object.getWednesday());
                hours.setText(data.getString("hoursToday"));
                return data;
            case(Calendar.THURSDAY):
                data = new JSONObject(object.getThursday());
                hours.setText(data.getString("hoursToday"));
                return data;
            case(Calendar.FRIDAY):
                data = new JSONObject(object.getFriday());
                hours.setText(data.getString("hoursToday"));
                return data;
            case(Calendar.SATURDAY):
                data = new JSONObject(object.getSaturday());
                hours.setText(data.getString("hoursToday"));
                return data;
        }

        return null;
    }
    public void back2(View v)
    {

        barindetail.this.finish();


    }
    @TargetApi(19)
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    void checkTime(JSONObject data) throws JSONException {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String open = data.getString("openTime");
        String close = data.getString("closeTime");
        int startHour = Integer.parseInt(open.split("\\:")[0]);
        int startMinute = Integer.parseInt(open.split("\\:")[1]);
        int endHour = Integer.parseInt(close.split("\\:")[0]);
        int endMinute = Integer.parseInt(close.split("\\:")[1]);

        if (endHour <= 2)
        {
            endHour = endHour + 24;
        }
        if(hour >= startHour && hour <= endHour)
        {
            if(hour == startHour)
            {
                if(minute >= startMinute)
                {
                    openClose.setText("Open");
                    openClose.setTextColor(Color.GREEN);
                }
                else
                {
                    openClose.setText("Closed");
                    openClose.setTextColor(Color.RED);
                }
            }
            else if(hour == endHour)
            {
                if(minute <= endMinute)
                {
                    openClose.setText("Open");
                    openClose.setTextColor(Color.GREEN);
                }
                else
                {
                    openClose.setText("Closed");
                    openClose.setTextColor(Color.RED);
                }
            }
            else
            {
                openClose.setText("Open");
                openClose.setTextColor(Color.GREEN);
            }

        }
        else
        {
            openClose.setText("Closed");
            openClose.setTextColor(Color.RED);
        }
    }
}
