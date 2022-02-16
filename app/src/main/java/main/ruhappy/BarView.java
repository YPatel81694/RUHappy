package main.ruhappy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;




public class BarView extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    Calendar calendar = Calendar.getInstance();
    ListView list;
    ListViewAdapter viewAdapter;
    SearchView searchView;
    ArrayList<BarLibrary> barLibraryList = new ArrayList<BarLibrary>();
    ArrayList<BarLibrary> orig = new ArrayList<BarLibrary>();
    Activity activity = this;
    timeChecker util;
    double currLong;
    double currLat;
    Filter filter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_view);
        findViewById(R.id.listView).requestFocus();
        //setStatusBarTranslucent(true);
        if(Build.VERSION.SDK_INT > 21)
        {
            getWindow().setStatusBarColor(getResources().getColor(R.color.cream));
        }

        double[] locate = (double[]) getIntent().getSerializableExtra("location");
        currLat = locate[0];
        currLong = locate[1];
        /*LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyLocationListener();
        try {
            Looper looper = null;
            mlocManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mlocListener, looper);
        } catch (SecurityException e) {
            // lets the user know there is a problem with the gps
        }*/

            list = (ListView) findViewById(R.id.listView);
            searchView = (SearchView) findViewById(R.id.searchView);
            setupSearchView();
            list.setTextFilterEnabled(false);
            barLibraryList = (ArrayList<BarLibrary>) getIntent().getSerializableExtra("barList");
        if(currLat != -1.0) {
            for (int x = 0; x < barLibraryList.size(); x++) {
                setMiles(barLibraryList.get(x));
            }
            Collections.sort(barLibraryList, new Comparator<BarLibrary>() {
                @Override
                public int compare(BarLibrary lhs, BarLibrary rhs) {
                    return new Double(lhs.getDistance()).compareTo(rhs.getDistance());
                }
            });
        }
        orig = barLibraryList;
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            util = new timeChecker(barLibraryList, day);
            util.execute();


        TextView allbars = (TextView) findViewById(R.id.textView);
        Typeface cursive = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        allbars.setTypeface(cursive);

        final TextView date = (TextView) findViewById(R.id.textView2);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        final String dayOfTheWeek = sdf.format(d);





        ImageButton frontArrow = (ImageButton) findViewById(R.id.imageButton4);
        frontArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    nextDay(date, dayOfTheWeek);

            }
        });

        ImageButton backArrow = (ImageButton) findViewById(R.id.imageButton3);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousDay(date, dayOfTheWeek);
            }
        });



    }

    public void setMiles(BarLibrary x){
        float[] distance = new float[1];
        double lat = x.getLatitude();
        double lon = x.getLongitude();
        Location.distanceBetween(lat, lon, currLat, currLong, distance);
        double miles = (double) distance[0] * 0.000621371;
        DecimalFormat df = new DecimalFormat("#.##");
        miles = Double.valueOf(df.format(miles));
        x.setDistance(miles);
    }
    public class MyLocationListener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location loc)

        {
            currLat = loc.getLatitude();
            currLong = loc.getLongitude();
            viewAdapter.setCurrLat(currLat);
            viewAdapter.setCurrLong(currLong);
            viewAdapter.notifyDataSetChanged();
        }


        @Override

        public void onProviderDisabled(String provider)
        {

        }


        @Override

        public void onProviderEnabled(String provider)
        {


        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {


        }

    }
    private void setupSearchView()
    {

        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("Find Bars");
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                barLibraryList = orig;
                viewAdapter.setList(barLibraryList);
                list.setAdapter(viewAdapter);
                return false;
            }
        });
    }
    public void back(View v)
    {
        Intent intent = new Intent(BarView.this, MainActivity.class);
        BarView.this.finish();
        startActivity(intent);


    }
    public void nextDay(TextView date, String dayOfTheWeek){
        String datedesired = (String) date.getText();
        if (datedesired.equals("Today")) {
            datedesired = dayOfTheWeek;
        }
        if(viewAdapter.updateList() != null)
        {
           // barLibraryList = viewAdapter.updateList();
        }
        switch (datedesired) {
            case "Sunday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.MONDAY);
                util.execute();
                if (dayOfTheWeek.equals("Monday")) {
                    date.setText("Today");

                } else {
                    date.setText("Monday");
                }
                break;
            case "Monday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.TUESDAY);
                util.execute();
                if (dayOfTheWeek.equals("Tuesday")) {
                    date.setText("Today");
                } else {
                    date.setText("Tuesday");
                }
                break;
            case "Tuesday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.WEDNESDAY);
                util.execute();
                if (dayOfTheWeek.equals("Wednesday")) {
                    date.setText("Today");
                } else {
                    date.setText("Wednesday");
                }
                break;
            case "Wednesday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.THURSDAY);
                util.execute();
                if (dayOfTheWeek.equals("Thursday")) {
                    date.setText("Today");
                } else {
                    date.setText("Thursday");
                }
                break;
            case "Thursday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.FRIDAY);
                util.execute();
                if (dayOfTheWeek.equals("Friday")) {
                    date.setText("Today");
                } else {
                    date.setText("Friday");
                }
                break;
            case "Friday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.SATURDAY);
                util.execute();
                if (dayOfTheWeek.equals("Saturday")) {
                    date.setText("Today");
                } else {
                    date.setText("Saturday");
                }
                break;
            case "Saturday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.SUNDAY);
                util.execute();
                if (dayOfTheWeek.equals("Sunday")) {
                    date.setText("Today");
                } else {
                    date.setText("Sunday");
                }
                break;

        }

    }

    public void previousDay(TextView date, String dayOfTheWeek) {
        String datedesired = (String) date.getText();
        if (datedesired.equals("Today")) {
            datedesired = dayOfTheWeek;
        }
        switch (datedesired) {
            case "Sunday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.SATURDAY);
                util.execute();
                if (dayOfTheWeek.equals("Saturday")) {
                    date.setText("Today");
                } else {
                    date.setText("Saturday");
                }
                break;
            case "Monday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.SUNDAY);
                util.execute();
                if (dayOfTheWeek.equals("Sunday")) {
                    date.setText("Today");
                } else {
                    date.setText("Sunday");
                }
                break;
            case "Tuesday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.MONDAY);
                util.execute();
                if (dayOfTheWeek.equals("Monday")) {
                    date.setText("Today");
                } else {
                    date.setText("Monday");
                }
                break;
            case "Wednesday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.TUESDAY);
                util.execute();
                if (dayOfTheWeek.equals("Tuesday")) {
                    date.setText("Today");
                } else {
                    date.setText("Tuesday");
                }
                break;
            case "Thursday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.WEDNESDAY);
                util.execute();
                if (dayOfTheWeek.equals("Wednesday")) {
                    date.setText("Today");
                } else {
                    date.setText("Wednesday");
                }
                break;
            case "Friday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.THURSDAY);
                util.execute();
                if (dayOfTheWeek.equals("Thursday")) {
                    date.setText("Today");
                } else {
                    date.setText("Thursday");
                }
                break;
            case "Saturday":
                util.cancel(true);
                util = new timeChecker(barLibraryList, Calendar.FRIDAY);
                util.execute();
                if (dayOfTheWeek.equals("Friday")) {
                    date.setText("Today");
                } else {
                    date.setText("Friday");
                }
                break;

        }
    }

    @TargetApi(19)
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       /* util.cancel(true);
        if(barLibraryList != viewAdapter.updateList())
        {
            util = new timeChecker(viewAdapter.updateList(), result);
            util.execute();
        }*/

        if (TextUtils.isEmpty(newText)) {
            barLibraryList = orig;
            viewAdapter.setList(barLibraryList);
            list.setAdapter(viewAdapter);

        } else {
            //list.setFilterText(newText);
            filter.filter(newText);
            barLibraryList = viewAdapter.updateList();
        }
        return true;
    }

    class timeChecker extends AsyncTask<Void, Void, Boolean> {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //String currentTime = (hour + ":" + minute +":00");
        int day;
        ArrayList<BarLibrary> barLib;


        public  timeChecker (ArrayList<BarLibrary> barLibrary, int selectedDay)
        {
            barLib = barLibrary;
            day = selectedDay;

        }

        public boolean checkTime(String start, String end) throws ParseException {
            int startHour = Integer.parseInt(start.split("\\:")[0]);
            int startMinute = Integer.parseInt(start.split("\\:")[1]);
            int endHour = Integer.parseInt(end.split("\\:")[0]);
            int endMinute = Integer.parseInt(end.split("\\:")[1]);

            if(endHour <= 4)
            {
                endHour = endHour + 24;
            }

            if(hour >= startHour && hour <= endHour)
            {
                if(hour == startHour)
                {
                    if(minute >= startMinute)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                if(hour == endHour)
                {
                    if(minute <= endMinute)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                return true;
            }
            else
            {
                return false;
            }
            /*Date nowTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(nowTime);
            System.out.println(currentTime);

            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(start);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time1);
            calendar2.add(Calendar.DATE, 1);
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(end);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(time2);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar1.getTime();
            if (x.after(calendar2.getTime()) && x.before(calendar3.getTime())) {
                System.out.println("Start:" + calendar2.getTime());
                System.out.println("End:" + calendar3.getTime());
                System.out.println("check works");
                return true;
            }
            else {
                System.out.println("Start:" + calendar2.getTime());
                System.out.println("End:" + calendar3.getTime());
                System.out.println("check failed");
            }*/

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JSONObject Details;
            String detail;
            String entStart = null, entEnd = null;
            String hhStart = null, hhEnd = null;

            for(int x = 0; x < barLib.size(); x++) {
                BarLibrary barLibraries = barLib.get(x);
                switch (day) {
                    case Calendar.SUNDAY:

                        try {
                            Details = new JSONObject(barLibraries.getSunday());
                            entStart = Details.getString("entStart");
                            entEnd = Details.getString("entEnd");
                            hhStart = Details.getString("happyHourStart");
                            hhEnd = Details.getString("happyHourEnd");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (hhStart.equals("999")) {
                            barLibraries.setHh(false);
                        } else {
                            try {
                                if (checkTime(hhStart, hhEnd)) {
                                    barLibraries.setHh(true);
                                } else {
                                    barLibraries.setHh(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (entStart.equals("999")) {

                            barLibraries.setEnt(false);
                        } else {
                            try {
                                if (checkTime(entStart, entEnd)) {
                                    barLibraries.setEnt(true);
                                } else {
                                    barLibraries.setEnt(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case Calendar.MONDAY:
                        try {
                            Details = new JSONObject(barLibraries.getMonday());
                            entStart = Details.getString("entStart");
                            entEnd = Details.getString("entEnd");
                            hhStart = Details.getString("happyHourStart");
                            hhEnd = Details.getString("happyHourEnd");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (hhStart.equals("999")) {
                            barLibraries.setHh(false);
                        } else {
                            try {
                                if (checkTime(hhStart, hhEnd)) {
                                    barLibraries.setHh(true);
                                } else {
                                    barLibraries.setHh(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (entStart.equals("999")) {

                            barLibraries.setEnt(false);
                        } else {
                            try {
                                if (checkTime(entStart, entEnd)) {
                                    barLibraries.setEnt(true);
                                } else {
                                    barLibraries.setEnt(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case Calendar.TUESDAY:

                        try {
                            Details = new JSONObject(barLibraries.getTuesday());
                            entStart = Details.getString("entStart");
                            entEnd = Details.getString("entEnd");
                            hhStart = Details.getString("happyHourStart");
                            hhEnd = Details.getString("happyHourEnd");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (hhStart.equals("999")) {
                            barLibraries.setHh(false);
                        } else {
                            try {
                                if (checkTime(hhStart, hhEnd)) {
                                    barLibraries.setHh(true);
                                } else {
                                    barLibraries.setHh(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (entStart.equals("999")) {

                            barLibraries.setEnt(false);
                        } else {
                            try {
                                if (checkTime(entStart, entEnd)) {
                                    barLibraries.setEnt(true);
                                } else {
                                    barLibraries.setEnt(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case Calendar.WEDNESDAY:

                        try {
                            Details = new JSONObject(barLibraries.getWednesday());
                            entStart = Details.getString("entStart");
                            entEnd = Details.getString("entEnd");
                            hhStart = Details.getString("happyHourStart");
                            hhEnd = Details.getString("happyHourEnd");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (hhStart.equals("999")) {
                            barLibraries.setHh(false);
                        } else {
                            try {
                                if (checkTime(hhStart, hhEnd)) {
                                    barLibraries.setHh(true);
                                } else {
                                    barLibraries.setHh(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (entStart.equals("999")) {

                            barLibraries.setEnt(false);
                        } else {
                            try {
                                if (checkTime(entStart, entEnd)) {
                                    barLibraries.setEnt(true);
                                } else {
                                    barLibraries.setEnt(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case Calendar.THURSDAY:

                        try {
                            Details = new JSONObject(barLibraries.getThursday());
                            entStart = Details.getString("entStart");
                            entEnd = Details.getString("entEnd");
                            hhStart = Details.getString("happyHourStart");
                            hhEnd = Details.getString("happyHourEnd");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (hhStart.equals("999")) {
                            barLibraries.setHh(false);
                        } else {
                            try {
                                if (checkTime(hhStart, hhEnd)) {
                                    barLibraries.setHh(true);
                                } else {
                                    barLibraries.setHh(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (entStart.equals("999")) {

                            barLibraries.setEnt(false);
                        } else {
                            try {
                                if (checkTime(entStart, entEnd)) {
                                    barLibraries.setEnt(true);
                                } else {
                                    barLibraries.setEnt(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case Calendar.FRIDAY:

                        try {
                            Details = new JSONObject(barLibraries.getFriday());
                            entStart = Details.getString("entStart");
                            entEnd = Details.getString("entEnd");
                            hhStart = Details.getString("happyHourStart");
                            hhEnd = Details.getString("happyHourEnd");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (hhStart.equals("999")) {
                            barLibraries.setHh(false);
                        } else {
                            try {
                                if (checkTime(hhStart, hhEnd)) {
                                    barLibraries.setHh(true);
                                } else {
                                    barLibraries.setHh(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (entStart.equals("999")) {

                            barLibraries.setEnt(false);
                        } else {
                            try {
                                if (checkTime(entStart, entEnd)) {
                                    barLibraries.setEnt(true);
                                } else {
                                    barLibraries.setEnt(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case Calendar.SATURDAY:

                        try {
                            Details = new JSONObject(barLibraries.getSaturday());
                            entStart = Details.getString("entStart");
                            entEnd = Details.getString("entEnd");
                            hhStart = Details.getString("happyHourStart");
                            hhEnd = Details.getString("happyHourEnd");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (hhStart.equals("999")) {
                            barLibraries.setHh(false);
                        } else {
                            try {
                                if (checkTime(hhStart, hhEnd)) {
                                    barLibraries.setHh(true);
                                } else {
                                    barLibraries.setHh(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (entStart.equals("999")) {

                            barLibraries.setEnt(false);
                        } else {
                            try {
                                if (checkTime(entStart, entEnd)) {
                                    barLibraries.setEnt(true);
                                } else {
                                    barLibraries.setEnt(false);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;


                }
            }
            return true;
        }



        protected void onPostExecute(Boolean x) {

            // viewAdapter.notifyDataSetChanged();
            viewAdapter = new ListViewAdapter(barLib, day, activity, currLat, currLong);
            //barLib = viewAdapter.updateList();
            filter = viewAdapter.getFilter();
            list.setAdapter(viewAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    barLib = viewAdapter.updateList();
                    Intent intent = new Intent(BarView.this, barindetail.class).putExtra("barObject",barLib.get(position));
                    intent.putExtra("day", day);

                    startActivity(intent);
                }
            });



        }
    }
}