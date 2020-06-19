package com.example.callstate_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.callstate_project.Model.Person;

import io.realm.Realm;
import io.realm.RealmResults;

public class view extends AppCompatActivity {

    TextView dt_display;
    String feedback,op;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        dt_display=findViewById(R.id.dt_display);

      // Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        op=intent.getStringExtra("op");
        dt_display.setText(op);

        // op=extras.getString("op");
    }


}

