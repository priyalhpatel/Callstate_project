package com.example.callstate_project.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callstate_project.Model.Person;
import com.example.callstate_project.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
    ArrayList<Person> people;


    public CustomAdapter(Context c, ArrayList<Person> people) {
        this.c = c;
        this.people = people;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(c).inflate(R.layout.list_item, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Person p = people.get(position);
        holder.txtfeedback.setText(p.getFeedback());
        holder.txtname.setText(p.getName());
        holder.txtstate.setText(p.getState());

    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
