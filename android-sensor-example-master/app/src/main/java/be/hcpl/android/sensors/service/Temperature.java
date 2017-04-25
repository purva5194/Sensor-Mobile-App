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

import be.hcpl.android.sensors.R;
import be.hcpl.android.sensors.core.BaseFragment;

/**
 * Created by purvapatel on 4/21/17.
 */


public class Temperature extends BaseFragment {

    TextView textTEMPERATURE_available, textTEMPERATURE_reading;
    TextView textAMBIENT_TEMPERATURE_available, textAMBIENT_TEMPERATURE_reading;
    //the Sensor Manager
    protected SensorManager sManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);

        textTEMPERATURE_available
                = (TextView)view.findViewById(R.id.TEMPERATURE_available);
        textTEMPERATURE_reading
                = (TextView)view.findViewById(R.id.TEMPERATURE_reading);
        textAMBIENT_TEMPERATURE_available
                = (TextView)view.findViewById(R.id.AMBIENT_TEMPERATURE_available);
        textAMBIENT_TEMPERATURE_reading
                = (TextView)view.findViewById(R.id.AMBIENT_TEMPERATURE_reading);


        SensorManager mySensorManager = (SensorManager)getActivity().getSystemService(getActivity().SENSOR_SERVICE);

        Sensor TemperatureSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);

        if(TemperatureSensor != null){
            textTEMPERATURE_available.setText("Sensor.TYPE_TEMPERATURE Available");
            mySensorManager.registerListener(
                    TemperatureSensorListener,
                    TemperatureSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }else{
            textTEMPERATURE_available.setText("Sensor.TYPE_TEMPERATURE NOT Available");
        }

        Sensor AmbientTemperatureSensor
                = mySensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(AmbientTemperatureSensor != null){
            textAMBIENT_TEMPERATURE_available.setText("Sensor.TYPE_AMBIENT_TEMPERATURE Available");
            mySensorManager.registerListener(
                    AmbientTemperatureSensorListener,
                    AmbientTemperatureSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            textAMBIENT_TEMPERATURE_available.setText("Sensor.TYPE_AMBIENT_TEMPERATURE NOT Available");
        }
        return view;
    }

    private final SensorEventListener TemperatureSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_TEMPERATURE){
                textTEMPERATURE_reading.setText("TEMPERATURE: " + event.values[0]);
            }
        }

    };

    private final SensorEventListener AmbientTemperatureSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
                textAMBIENT_TEMPERATURE_reading.setText("AMBIENT TEMPERATURE: " + event.values[0]);
            }
        }

    };

}