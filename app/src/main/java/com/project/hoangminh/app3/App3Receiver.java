package com.project.hoangminh.app3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*---------------------------------------------------
Project 3 - App 3
App3Receiver class

set receiver in manifest file
----------------------------------------------------*/

public class App3Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context c, Intent i) {

        //set flag to start activity outside app
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //set intent's class name depending on intent action
        if(i.getAction().equals("com.project.hoangminh.app1.showHotels")) {
            i.setClassName("com.project.hoangminh.app3", "com.project.hoangminh.app3.HotelActivity");
        }else{
            i.setClassName("com.project.hoangminh.app3", "com.project.hoangminh.app3.RestaurantActivity");
        }
        //start the activity from context c, explicit intent since setting class name above
        c.startActivity(i);
    }
}
