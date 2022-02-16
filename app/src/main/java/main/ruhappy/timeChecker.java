package main.ruhappy;


import android.app.Activity;
import android.os.AsyncTask;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ypgia on 8/13/2016.
 */
public class timeChecker extends AsyncTask<Void, Void, Boolean[]> {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    String currentTime = (hour + ":" + minute +":00");
    int day;
    BarLibrary barLibraries;
    Activity activity;
    Boolean[] result = new Boolean[2];



    public  timeChecker (BarLibrary barLibrary, int selectedDay, Activity activity1)
    {
        barLibraries = barLibrary;
        day = selectedDay;
        activity = activity1;
    }

    public boolean checkTime(String start, String end) throws ParseException {
        Date nowTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(nowTime);

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
            System.out.println("Start:" + calendar3.getTime());
            System.out.println("check works");
            return true;
        }
        else {
            System.out.println("check failed");
        }
        return false;
    }

    @Override
    protected Boolean[] doInBackground(Void... params) {
        JSONObject Details;
        String entStart = null, entEnd = null;
        String hhStart = null, hhEnd = null;


        switch (day) {
            case Calendar.SUNDAY:
                try {
                    Details = new JSONObject(barLibraries.getSunday());
                    entStart = Details.getString("entStart") + ":00";
                    entEnd = Details.getString("entEnd") + ":00";
                    hhStart = Details.getString("happyHourStart") + ":00";
                    hhEnd = Details.getString("happyHourEnd") + ":00";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (hhStart.equals("999:00")) {
                    result[0] = false;
                } else {
                    try {
                        if (checkTime(hhStart, hhEnd)) {
                            result[0] = true;
                        } else {
                            result[0] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entStart.equals("999:00")) {

                    result[1] = false;
                } else {
                    try {
                        if (checkTime(entStart, entEnd)) {
                            result[1] = true;
                        } else {
                            result[1] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            case Calendar.MONDAY:
                try {
                    Details = new JSONObject(barLibraries.getMonday());
                    entStart = Details.getString("entStart") + ":00";
                    entEnd = Details.getString("entEnd") + ":00";
                    hhStart = Details.getString("happyHourStart") + ":00";
                    hhEnd = Details.getString("happyHourEnd") + ":00";

                    System.out.println(entStart);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (hhStart.equals("999:00")) {
                    result[0] = false;
                } else {
                    try {
                        if (checkTime(hhStart, hhEnd)) {
                            result[0] = true;
                        } else {
                            result[0] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entStart.equals("999:00")) {

                    result[1] = false;
                } else {
                    try {
                        if (checkTime(entStart, entEnd)) {
                            result[1] = true;
                        } else {
                            result[1] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            case Calendar.TUESDAY:
                try {
                    Details = new JSONObject(barLibraries.getTuesday());
                    entStart = Details.getString("entStart") + ":00";
                    entEnd = Details.getString("entEnd") + ":00";
                    hhStart = Details.getString("happyHourStart") + ":00";
                    hhEnd = Details.getString("happyHourEnd") + ":00";

                    System.out.println(entStart);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (hhStart.equals("999:00")) {
                    result[0] = false;
                } else {
                    try {
                        if (checkTime(hhStart, hhEnd)) {
                            result[0] = true;
                        } else {
                            result[0] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entStart.equals("999:00")) {

                    result[1] = false;
                } else {
                    try {
                        if (checkTime(entStart, entEnd)) {
                            result[1] = true;
                        } else {
                            result[1] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            case Calendar.WEDNESDAY:
                try {
                    Details = new JSONObject(barLibraries.getWednesday());
                    entStart = Details.getString("entStart") + ":00";
                    entEnd = Details.getString("entEnd") + ":00";
                    hhStart = Details.getString("happyHourStart") + ":00";
                    hhEnd = Details.getString("happyHourEnd") + ":00";

                    System.out.println(entStart);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (hhStart.equals("999:00")) {
                    result[0] = false;
                } else {
                    try {
                        if (checkTime(hhStart, hhEnd)) {
                            result[0] = true;
                        } else {
                            result[0] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entStart.equals("999:00")) {

                    result[1] = false;
                } else {
                    try {
                        if (checkTime(entStart, entEnd)) {
                            result[1] = true;
                        } else {
                            result[1] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            case Calendar.THURSDAY:
                try {
                    Details = new JSONObject(barLibraries.getThursday());
                    entStart = Details.getString("entStart") + ":00";
                    entEnd = Details.getString("entEnd") + ":00";
                    hhStart = Details.getString("happyHourStart") + ":00";
                    hhEnd = Details.getString("happyHourEnd") + ":00";

                    System.out.println(entStart);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (hhStart.equals("999:00")) {
                    result[0] = false;
                } else {
                    try {
                        if (checkTime(hhStart, hhEnd)) {
                            result[0] = true;
                        } else {
                            result[0] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entStart.equals("999:00")) {

                    result[1] = false;
                } else {
                    try {
                        if (checkTime(entStart, entEnd)) {
                            result[1] = true;
                        } else {
                            result[1] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            case Calendar.FRIDAY:
                try {
                    Details = new JSONObject(barLibraries.getFriday());
                    entStart = Details.getString("entStart") + ":00";
                    entEnd = Details.getString("entEnd") + ":00";
                    hhStart = Details.getString("happyHourStart") + ":00";
                    hhEnd = Details.getString("happyHourEnd") + ":00";

                    System.out.println(entStart);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (hhStart.equals("999:00")) {
                    result[0] = false;
                } else {
                    try {
                        if (checkTime(hhStart, hhEnd)) {
                            result[0] = true;
                        } else {
                            result[0] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entStart.equals("999:00")) {

                    result[1] = false;
                } else {
                    try {
                        if (checkTime(entStart, entEnd)) {
                            result[1] = true;
                        } else {
                            result[1] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            case Calendar.SATURDAY:
                try {
                    Details = new JSONObject(barLibraries.getSaturday());
                    entStart = Details.getString("entStart") + ":00";
                    entEnd = Details.getString("entEnd") + ":00";
                    hhStart = Details.getString("happyHourStart") + ":00";
                    hhEnd = Details.getString("happyHourEnd") + ":00";

                    System.out.println(entStart);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (hhStart.equals("999:00")) {
                    result[0] = false;
                } else {
                    try {
                        if (checkTime(hhStart, hhEnd)) {
                            result[0] = true;
                        } else {
                            result[0] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (entStart.equals("999:00")) {

                    result[1] = false;
                } else {
                    try {
                        if (checkTime(entStart, entEnd)) {
                            result[1] = true;
                        } else {
                            result[1] = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


        }
    return result;
    }

    @Override
    protected void onPostExecute(Boolean[] result1) {
        super.onPostExecute(result);
        result = result1;

        ImageView ent = (ImageView) activity.findViewById(R.id.ent);
        ImageView hh = (ImageView) activity.findViewById(R.id.hh);


        if(result[0] == false)
        {
            hh.setVisibility(View.INVISIBLE);
            System.out.println("getting rid of hh");
        }
        else
        {
            hh.setVisibility(View.VISIBLE);
        }
        if(result[1] == false)
        {
            ent.setVisibility(View.INVISIBLE);
            System.out.println("getting rid of ent");
        }
        else
        {
            ent.setVisibility(View.VISIBLE);
        }
    }
}





