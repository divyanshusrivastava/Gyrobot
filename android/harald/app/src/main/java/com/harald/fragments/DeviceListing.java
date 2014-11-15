package com.harald.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.btwiz.library.BTWiz;
import com.harald.R;
import com.harald.common.Helper;
import com.harald.listners.DeviceListing.DeviceSelector;

import java.util.ArrayList;
import java.util.List;

public class DeviceListing extends Fragment {

    ArrayAdapter<String> deviceNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_listing, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();

        ListView deviceList = (ListView) activity.findViewById(R.id.device_list);
        deviceNames = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1);

        List<BluetoothDevice> devices =
                new ArrayList<BluetoothDevice>(BluetoothAdapter.getDefaultAdapter().getBondedDevices());

        (new Helper(activity)).ensureBTOn();
        deviceList.setAdapter(deviceNames);

        for (BluetoothDevice d : devices) {
            deviceNames.add(d.getName());
        }

        DeviceSelector deviceSelector = new DeviceSelector(activity, devices);
        deviceList.setOnItemClickListener(deviceSelector);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
