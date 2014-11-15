package com.harald.listners.DeviceListing;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.btwiz.library.BTSocket;

import java.util.List;

/**
 * Created by shivekkhurana on 18/10/14.
 */
public class DeviceSelector implements AdapterView.OnItemClickListener {

    List<BluetoothDevice> devices;
    Context context;
    BTSocket socket;

    public DeviceSelector(Context c, List<BluetoothDevice> d) {
        context = c;
        devices = d;
    }

    @Override
    public void onItemClick(final AdapterView parent, View v, int position, long id) {
        final BluetoothDevice selectedDevice = devices.get(position);
        ((OnDeviceSelectedListener) context).onDeviceSelected(selectedDevice);

        //start loading
    }

    public void setSocket(BTSocket s) {
        socket = s;
    }

    public BTSocket getSocket() {
        return  socket;
    }

    public interface OnDeviceSelectedListener {
        public void onDeviceSelected(BluetoothDevice device);
    }
}
