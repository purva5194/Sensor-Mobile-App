package be.hcpl.android.sensors.service;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import be.hcpl.android.sensors.R;
import be.hcpl.android.sensors.core.BaseFragment;

/**
 * Created by purvapatel on 4/21/17.
 */

public class Location extends BaseFragment {

    Button btnShowLocation;
    TextView txtlatitide, txtlongitude, txtLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnShowLocation = (Button) view.findViewById(R.id.button);
        txtlatitide = (TextView) view.findViewById(R.id.text_lati);
        txtlongitude = (TextView) view.findViewById(R.id.text_longi);
        txtLocation = (TextView) view.findViewById(R.id.text_location);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(getActivity());

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    txtlatitide.setText("Latitude : "+latitude);
                    txtlongitude.setText("Longitude : "+longitude);
                    getCompleteAddressString(latitude,longitude);

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });

        return view;
    }

    public String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                txtLocation.setText("Current loction address : " + strReturnedAddress.toString());
            } else {
                txtLocation.setText("Current loction address : "+"No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            txtLocation.setText("Current loction address : "+"Canont get Address!");
        }
        return strAdd;
    }
}
