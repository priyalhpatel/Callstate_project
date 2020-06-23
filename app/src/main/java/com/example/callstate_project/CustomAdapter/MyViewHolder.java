package com.example.callstate_project.CustomAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callstate_project.R;


public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView txtfeedback;
    TextView txtname;
    TextView txtstate;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        txtfeedback = itemView.findViewById(R.id.txt_feedback);
        txtname=itemView.findViewById(R.id.txt_name);
        txtstate=itemView.findViewById(R.id.txt_state);
    }
}
