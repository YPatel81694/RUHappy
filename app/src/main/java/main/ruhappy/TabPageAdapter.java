package main.ruhappy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


/**
 * Created by ypgia on 8/15/2016.
 */
public class TabPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    BarLibrary barObject;
    JSONObject data;
    private final String[] PAGE_TITLES =
            {
                    "Drinks",
                    "Entertainment",
                    "Information"
            };




    @Override
    public CharSequence getPageTitle(int position)
    {
        return PAGE_TITLES[position];
    }
    public TabPageAdapter(FragmentManager fm, BarLibrary bL, JSONObject x) {
        super(fm);
        barObject = bL;
        data = x;
    }

    @Override
    public Fragment getItem(int position) {
        JSONObject hours = null;
        String address = null;
        try {
           hours = new JSONObject(barObject.getHoursOfBarOperation());
            address = barObject.getAddress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch(position){
            case 0:
                Bundle bundle  = new Bundle();
                bundle.putString("barObject", data.toString());
                drinks drinks = new drinks();
                drinks.setArguments(bundle);
                return drinks;
            case 1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("entertainment", data.toString());
                entertainment entertainment = new entertainment();
                entertainment.setArguments(bundle1);
                return entertainment;
            case 2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("hours", hours.toString());
                bundle2.putString("address", address);
                Info info = new Info();
                info.setArguments(bundle2);
                return info;

        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
