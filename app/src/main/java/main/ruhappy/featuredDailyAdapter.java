package main.ruhappy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ypgia on 8/24/2016.
 */
public class featuredDailyAdapter extends BaseAdapter {
    String[] data = null;
    private static LayoutInflater inflater=null;

    public featuredDailyAdapter(String[] deals, Activity a)
    {
        data = deals;
        inflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data == null)
        {
            return 1;
        }
        return data.length/2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        int sadface = 0x1F622;
        int sos = 0x1F198;
        String sadfaceString = new String(Character.toChars(sadface));
        String sosString = new String(Character.toChars(sos));
        if(convertView == null)
        {
            vi = inflater.inflate(R.layout.featuredrow, null);
        }

        TextView dd = (TextView) vi.findViewById(R.id.dailydrink);
        TextView pp = (TextView) vi.findViewById(R.id.dailyprice);
        if (data == null)
        {
            dd.setText("No Deals Today");
            pp.setText(sosString + sadfaceString);
        }
        else
        {
                dd.setText(data[(position*2)+1]);
                pp.setText(data[position*2]);
        }

        return vi;
    }
}
