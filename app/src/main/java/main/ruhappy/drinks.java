package main.ruhappy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.Pack200;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link drinks.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link drinks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class drinks extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    BarLibrary barLibrary;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Bundle bundle;
    String[] data;
    String[] hhdata;
    ListView list, list2;
    featuredDailyAdapter adapter;
    happyHourAdapter adapter2;
    String drinkspecial;
    String hhspecial;
    String hours;
    JSONObject object;
    TextView hhours;
    private OnFragmentInteractionListener mListener;

    public drinks() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment drinks.
     */
    // TODO: Rename and change types and number of parameters
    public static drinks newInstance(String param1, String param2) {
        drinks fragment = new drinks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            bundle = getArguments();
            try {
                object = new JSONObject(bundle.getString("barObject"));
                arrangeData(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

       // adapter = new featuredDailyAdapter(null, this.getActivity());
    }
    void arrangeData(JSONObject x) throws JSONException
    {
        hours = x.getString("happyHour");
        if(hours.equalsIgnoreCase("None"))
        {
            hours = null;
        }
        drinkspecial = x.getString("dailyDrinkSpecial");
        hhspecial = x.getString("happyHourDeals");
        if(!drinkspecial.equalsIgnoreCase("None")) {
            data = drinkspecial.split("-");
        }
        else
        {
            data = null;
        }
        if(!hhspecial.equalsIgnoreCase("None")) {
            hhdata = hhspecial.split("-");
        }
        else
        {
            hhdata = null;
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);

        list = (ListView) view.findViewById(R.id.listView2);
        adapter = new featuredDailyAdapter(data, this.getActivity());
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);

        list2 = (ListView) view.findViewById(R.id.listView3);
        adapter2 = new happyHourAdapter(hhdata, this.getActivity());
        list2.setAdapter(adapter2);
        setListViewHeightBasedOnChildren(list2);

        hhours = (TextView) view.findViewById(R.id.textView8);
        if(hours != null) {
            hhours.setText(hours);
        }
            /*temp = (TextView) view.findViewById(R.id.drinkstext);
        temp.setText(selected.getMonday());*/

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = new onFragmentInteractionListener();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
