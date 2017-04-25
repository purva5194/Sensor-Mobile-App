package be.hcpl.android.sensors.service;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import be.hcpl.android.sensors.R;
import be.hcpl.android.sensors.core.BaseFragment;

/**
 * Created by purvapatel on 4/21/17.
 */


public class Gyroscope extends BaseFragment implements SensorEventListener {

    TextView text;
    //the Sensor Manager
    protected SensorManager sManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gyroscope, container, false);

        text = (TextView) view.findViewById(R.id.txtData);

        //get a hook to the sensor service
        sManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);

        return view;
    }

    //when this Activity starts
    @Override
    public void onResume()
    {
        super.onResume();
        /*register the sensor listener to listen to the gyroscope sensor, use the
        callbacks defined in this class, and gather the sensor information as quick
        as possible*/
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);
    }

    //When this Activity isn't visible anymore
    @Override
    public void onStop()
    {
        //unregister the sensor listener
        sManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }

        //else it will output the Roll, Pitch and Yawn values
        text.setText("Orientation X (Roll) :"+ String.format("%.02f", event.values[2]) +"\n"+
                "Orientation Y (Pitch) :"+ String.format("%.02f", event.values[1]) +"\n"+
                "Orientation Z (Yaw) :"+ String.format("%.02f", event.values[0]));

    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing.
    }

}