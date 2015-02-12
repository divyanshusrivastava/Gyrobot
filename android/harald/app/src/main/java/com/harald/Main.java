package com.harald;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.btwiz.library.BTSocket;
import com.btwiz.library.BTWiz;
import com.btwiz.library.IDeviceConnectionListener;
import com.harald.fragments.DeviceConnected;
import com.harald.fragments.DeviceListing;
import com.harald.listners.DeviceListing.DeviceSelector;

import java.io.IOException;


public class Main extends Activity implements SensorEventListener, DeviceSelector.OnDeviceSelectedListener, DeviceConnected.OnCommandIssuedListener {

    private boolean flag = false;
    private SensorManager sensorManager;
    private Sensor msensor;
    BTSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) return;

        getFragmentManager()
            .beginTransaction()
            .add(R.id.container, new DeviceListing())
            .commit()
        ;


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        msensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
        {
            Log.i("DEBUG","sensor found");
            // Sensor FOUND
        }
        else
        {
            Log.i("DEBUG","sensor missing");
            //Sensor NOT FOUND
        }

        //sensorManager.registerListener(this, msensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDeviceSelected(final BluetoothDevice device){

        IDeviceConnectionListener connectionListener = new IDeviceConnectionListener() {
            @Override
            public void onConnectSuccess(BTSocket btSocket) {
               socket = btSocket;
               getFragmentManager()
                       .beginTransaction()
                       .replace(R.id.container, new DeviceConnected())
                       .commit();
            }

            @Override
            public void onConnectionError(Exception e, String s) {
                e.printStackTrace();
            }
        };

        BTWiz.connectAsClientAsync(Main.this, device, connectionListener);
    }

    @Override
    public void onCommandIssued(char command) {
        if (command != '0')  {
            writeToSocket(String.valueOf(command));
            Log.i("Command", String.valueOf(command));

        }
        else {
            writeToSocket("exit");
            Log.i("Command", String.valueOf(command));

            BTWiz.closeAllOpenSockets();
            BTWiz.cleanup(Main.this);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new DeviceListing())
                    .commit();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        String str = "";

        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            if (flag) {
                //Log.i("GyroData", String.valueOf(x));
                str = "";
                str = "\n"+String.valueOf(x)+"\t "+String.valueOf(y)+"\t "+String.valueOf(z);

                writeToSocket(str);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, msensor, SensorManager.SENSOR_DELAY_NORMAL);
    }



    @Override
    public void onLogStart() {
        Log.i("DEBUG","onLogStart() called");
        sensorManager.registerListener(this, msensor, SensorManager.SENSOR_DELAY_NORMAL);
        flag = true;
    }

    @Override
    public void onLogStop() {
        sensorManager.unregisterListener(this);
        flag = false;
    }

    public void writeToSocket(String s) {
        try {
            socket.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////
    public void commandButtonPressed(View v) {
        (new DeviceConnected()).commandButtonPressed(v);
    }

}
