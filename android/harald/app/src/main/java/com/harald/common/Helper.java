package com.harald.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.btwiz.library.BTWiz;
import com.btwiz.library.DeviceNotSupportBluetooth;

/**
 * Created by shivekkhurana on 18/10/14.
 */
public class Helper {

    Context context;

    public Helper(Context c) {
        context = c;
    }

    public void ensureBTOn() {
        try {
            if (!BTWiz.isEnabled(context)) {
                Intent btEnableIntent = BTWiz.enableBTIntent();
                btEnableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(btEnableIntent);
            }
        } catch (DeviceNotSupportBluetooth e) {
            e.printStackTrace();
        }
    }

}
