package com.harald;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
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


public class Main extends Activity implements DeviceSelector.OnDeviceSelectedListener, DeviceConnected.OnCommandIssuedListener {

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
    public void onCommandIssued(int command) {
        if (command > 0) writeToSocket(String.valueOf(command));
        else {
            writeToSocket("exit");

            BTWiz.closeAllOpenSockets();
            BTWiz.cleanup(Main.this);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new DeviceListing())
                    .commit();
        }
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
