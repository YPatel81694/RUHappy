package main.ruhappy;

import android.app.Activity;
import android.content.Context;



import android.graphics.Color;
import android.location.Location;
import android.support.v4.view.PagerTabStrip;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;





import java.util.ArrayList;


/**
 * Created by ypgia on 8/2/2016.
 */
public class ListViewAdapter extends BaseAdapter implements Filterable {

    private Activity activity;
    private ArrayList<BarLibrary> barLibraries = new ArrayList<BarLibrary>();
    private ArrayList<BarLibrary> orig = null;
    private static LayoutInflater inflater = null;
    //private Boolean[][] filtResult;
    // private ArrayList<Integer> positions = new ArrayList<Integer>();
    private int day = 0;
    double currLong;
    double currLat;
    boolean isFiltered = false;

    public ListViewAdapter(ArrayList<BarLibrary> barLibrary, int currentDay, Activity a, double lat, double longit) {
        day = currentDay;
        activity = a;
        barLibraries = barLibrary;
        orig = barLibrary;
        currLong = longit;
        currLat = lat;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //filtResult = checker;


    }

    @Override
    public int getCount() {
        return barLibraries.size();
    }

    @Override
    public Object getItem(int position) {
        if (isFiltered == false) {

            return barLibraries.get(position);
        } else {
            return orig.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getDay() {
        return day;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final int currentPosition = position;

        if (barLibraries.size() == 0) {
            return vi;
        }

        if (convertView == null) {
            vi = inflater.inflate(R.layout.listviewrow, null);
        }
        ImageView hh = (ImageView) vi.findViewById(R.id.hh);
        ImageView ent = (ImageView) vi.findViewById(R.id.ent);

        if (barLibraries.get(position).getHh()) {
            hh.setVisibility(View.VISIBLE);
        } else {
            hh.setVisibility(View.INVISIBLE);
        }
        if (barLibraries.get(position).getEnt()) {
            ent.setVisibility(View.VISIBLE);
        } else {
            ent.setVisibility(View.INVISIBLE);
        }
        String milesNumber = setMiles(barLibraries.get(position).getLatitude(), barLibraries.get(position).getLongitude());
        TextView barName = (TextView) vi.findViewById(R.id.barName);
        TextView miles = (TextView) vi.findViewById(R.id.miles);
       /* if(milesNumber != null && (currLat != 0.0 && currLong !=0.0))
        {
            miles.setText(milesNumber + " miles");
            miles.setTextColor(Color.BLACK);
        }
        else {
            miles.setText("getting miles");
            miles.setTextColor(Color.BLACK);
        }*/
        if(barLibraries.get(position).getDistance() != -1.0) {
            miles.setText(Double.toString(barLibraries.get(position).getDistance()) + " miles");
            miles.setTextColor(Color.BLACK);
        }
        else
        {
            miles.setText("GPS not enabled");
            miles.setTextColor(Color.BLACK);
        }
        final ImageView barPhoto = (ImageView) vi.findViewById(R.id.barPhoto);
        barName.setText(barLibraries.get(position).getName());
        if (currentPosition < barLibraries.size()) {

          Glide.with(activity.getApplicationContext()).load(barLibraries.get(position).getBarImages()).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                  .into(barPhoto);


           /* Picasso.with(activity.getApplicationContext()).load(barLibraries.get(position).getBarImages()).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.loading).fit().centerCrop().into(barPhoto, new Callback() {

                @Override
                public void onSuccess() {


                }

                @Override
                public void onError() {

                    if (currentPosition < barLibraries.size()) {
                        Picasso.with(activity.getApplicationContext()).load(barLibraries.get(currentPosition).getBarImages())
                                .placeholder(R.drawable.loading).fit().centerCrop().into(barPhoto);
                    }

                }
            });*/

        }


        return vi;
    }


    String setMiles(double lat, double lon) {
        float[] distance = new float[1];
        Location.distanceBetween(lat, lon, currLat, currLong, distance);
        double miles = Math.round(((distance[0] * 0.00062137) * 100.0)/100.0);
        String meters = Double.toString(miles);
        return meters;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<BarLibrary> results = new ArrayList<BarLibrary>();
                if (orig == null)
                    orig = barLibraries;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {

                        for (int x = 0; x < orig.size(); x++) {
                            if (orig.get(x).getName().toLowerCase()
                                    .contains(constraint.toString().toLowerCase())) {

                                results.add(orig.get(x));
                                isFiltered = true;
                            }
                        }
                    }
                    oReturn.values = results;
                }
                //orig = null;

                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                barLibraries = (ArrayList<BarLibrary>) results.values;
               /* filtResult = new Boolean[barLibraries.size()][2];
                for(int x = 0; x < (filtResult.length-1); x++)
                {
                    System.out.println("not coming here");
                    filtResult[x][0] = result[positions.get(x)][0];
                    filtResult[x][1] = result[positions.get(x)][1];
                }
                result = filtResult;
                positions = new ArrayList<Integer>();*/

                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public ArrayList<BarLibrary> updateList(){
        return barLibraries;
    }

    void setList (ArrayList<BarLibrary> x)
    {
        barLibraries = x;
        orig = null;
        super.notifyDataSetChanged();
    }

    void setCurrLat(double lat)
    {
        currLat = lat;
    }
    void setCurrLong(double lon)
    {
        currLong = lon;
    }



}
