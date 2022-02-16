package main.ruhappy;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;


import com.backendless.*;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity  {

    ArrayList<BarLibrary> barLibrary = new ArrayList<BarLibrary>();
    ImageButton mainButton;
    Boolean isDone = false;
    Boolean isLocated = false;
    double[] location = new double[2];
    double currLat;
    double currLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarTranslucent(true);

        mainButton = (ImageButton) findViewById(R.id.imageButton);

        if(Build.VERSION.SDK_INT >= 23)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        else {
            LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationListener mlocListener = new MyLocationListener();
            try {
                Looper looper = null;
                mlocManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mlocListener, looper);
            } catch (SecurityException e) {
                // lets the user know there is a problem with the gps
            }
        }


        Backendless.initApp(this, "3E370009-9B38-8422-FF58-E5CD4CE51B00", "1D7C005C-38D2-A1D1-FF78-926FEBD72D00", "v1");
        BackendlessDataQuery paging = new BackendlessDataQuery();
        paging.setPageSize(15);
        Backendless.Persistence.of(BarLibrary.class).find(paging, new AsyncCallback<BackendlessCollection<BarLibrary>>() {
            @Override
            public void handleResponse(BackendlessCollection<BarLibrary> bar) {

                for (int x = 0; x < bar.getData().size(); x++) {
                    barLibrary.add(bar.getData().get(x));
                }
                isDone = true;

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

                Toast.makeText(MainActivity.this, "Error: Check Network Connection", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    LocationListener mlocListener = new MyLocationListener();
                    try {
                        Looper looper = null;
                        mlocManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mlocListener, looper);
                    } catch (SecurityException e) {
                        // lets the user know there is a problem with the gps
                    }
                } else {
                    Toast.makeText(this, "GPS denied", Toast.LENGTH_SHORT).show();
                    currLat = -1.0;
                    currLong = -1.0;
                    location[0] = currLat;
                    location[1] = currLong;
                    isLocated = true;
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public class MyLocationListener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location loc)

        {
           currLat = loc.getLatitude();
           currLong = loc.getLongitude();
            location[0] = currLat;
            location[1] = currLong;
            isLocated = true;
        }


        @Override

        public void onProviderDisabled(String provider)
        {
            Toast.makeText(getApplicationContext(), "GPS not enabled", Toast.LENGTH_SHORT).show();
            currLat = -1.0;
            currLong = -1.0;
            location[0] = currLat;
            location[1] = currLong;
            isLocated = true;


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
    public void mainButtonClick(View v) {

                if(isDone) {
                    if(isLocated) {
                        Intent intent = new Intent(MainActivity.this, BarView.class).putExtra("barList", barLibrary);
                        intent.putExtra("location", location);
                        MainActivity.this.finish();
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(this, "Getting Location...", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(this, "Loading...Try Again", Toast.LENGTH_SHORT).show();
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
}



