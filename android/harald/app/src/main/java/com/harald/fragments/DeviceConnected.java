package com.harald.fragments;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.harald.R;

public class DeviceConnected extends Fragment {
    Activity activity;
    int flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_device_connected, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
    }

    public void commandButtonPressed(View v) {
        char command;

        switch (v.getId()){
            case R.id.c1:
                command = '1';
                break;

            case R.id.c2:
                command = '2';
                break;

            case R.id.c3:
                command = '3';
                break;

            case R.id.c4:
                command = '4';
                break;

            case R.id.c5:
                command = '5';
                break;

            case R.id.start:
                //flag = 1;
                log_gyro_data(v);
                command = '6';
                break;

            case R.id.stop:
                unlog_gyro_data(v);
                command = '7';
                break;

            case R.id.close_connection:
                command = '0';
                break;

            default:
                command = '0';
        }

        if (command < '6') {
            ((OnCommandIssuedListener) v.getContext()).onCommandIssued(command);
            Log.i("Command", String.valueOf(command));
        }
    }

    public interface OnCommandIssuedListener {
        public void onCommandIssued(char command);
        public void onLogStart();
        public void onLogStop();
    }

    public void log_gyro_data(View v) {
        Log.i("DEBUG","log_gyro_data() called");
        ((OnCommandIssuedListener) v.getContext()).onLogStart();

    }

    public void unlog_gyro_data(View v) {
        ((OnCommandIssuedListener) v.getContext()).onLogStop();
    }
}
