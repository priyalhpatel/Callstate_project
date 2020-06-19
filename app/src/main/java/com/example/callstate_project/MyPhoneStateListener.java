package com.example.callstate_project;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MyPhoneStateListener extends PhoneStateListener {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;

    TextView textView;
     String state1;

    public void onCallStateChanged(Context context, int state, String phoneNumber){
        if(lastState == state){
            return;
        }

        System.out.println("Number inside onCallStateChange : "  + phoneNumber);
        Intent intent = new Intent(context,Display.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("number",phoneNumber);
        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();

                  state1= "Incoming call Ringing";
                  intent.putExtra("state1",state1);
                  context.startActivity(intent);
                Toast.makeText(context, "Incoming Call Ringing " , Toast.LENGTH_SHORT).show();

                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    state1= "incoming call Ringing";
                    intent.putExtra("state1",state1);
                    context.startActivity(intent);
                    Toast.makeText(context, "Outgoing Call Started " +phoneNumber , Toast.LENGTH_SHORT).show();
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                if(lastState == TelephonyManager.CALL_STATE_RINGING){

                    state1= "ring but not pick up ";
                    intent.putExtra("state1",state1);
                    context.startActivity(intent);
                    Toast.makeText(context, "Ringing but no pickup"  +phoneNumber+ " Call time " + callStartTime +" Date " + new Date() , Toast.LENGTH_SHORT).show();
                }
                else if(isIncoming){

                    state1= "Incoming call Ringing";
                    intent.putExtra("state1",state1);
                    context.startActivity(intent);
                    Toast.makeText(context, "Incoming " + phoneNumber+ " Call time " +callStartTime  , Toast.LENGTH_SHORT).show();
                }
                else{

                    state1= "outgoing call Ringing";
                    intent.putExtra("state1",state1);
                    context.startActivity(intent);

                    Toast.makeText(context, "outgoing " + phoneNumber+ " Call time " + callStartTime +" Date " + new Date() , Toast.LENGTH_SHORT).show();

                }

                break;
        }
        lastState = state;
    }
}