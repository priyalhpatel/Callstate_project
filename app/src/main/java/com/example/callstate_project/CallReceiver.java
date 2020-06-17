package com.example.callstate_project;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Output;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;
import static android.content.Intent.getIntent;
import static androidx.core.content.ContextCompat.getCodeCacheDir;
import static androidx.core.content.ContextCompat.getSystemService;

public class CallReceiver extends BroadcastReceiver {
    TextView cust_name;
    String p1="+919429704118";
    String p2="+919427960325";
    String p3="+919484507696";
    String p4="+919878472827";


    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener customPhoneListener = new MyPhoneStateListener();



        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Bundle bundle = intent.getExtras();
        String phone_number = bundle.getString("incoming_number");



        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);

        int state = 0;
        if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            state = TelephonyManager.CALL_STATE_IDLE;
        }
        else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            state = TelephonyManager.CALL_STATE_OFFHOOK;
        }
        else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            state = TelephonyManager.CALL_STATE_RINGING;
        }
        if (phone_number == null || "".equals(phone_number)) {

            return;

        }
        customPhoneListener.onCallStateChanged(context, state, phone_number);

        Toast.makeText(context, "This is " +phone_number , Toast.LENGTH_SHORT).show();


        if(phone_number.equals(p1)|| phone_number.equals(p2) || phone_number.equals(p3) || phone_number.equals(p4)){

            Toast.makeText(context,"Match"+phone_number,Toast.LENGTH_LONG).show();


            Intent i = new Intent(context, Display.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);


          // String name = phone_number.getText().toString();
           // cust_name.setText(name);

        }
        else{

            Toast.makeText(context,"notmatch"+phone_number,Toast.LENGTH_LONG).show();
        }

    }}