package com.example.callstate_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.callstate_project.CustomAdapter.CustomAdapter;
import com.example.callstate_project.Helper.MyHelper;
import com.example.callstate_project.Model.Person;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class view extends AppCompatActivity {

    // TextView dt_display;
    //String feedback,op;
    Realm realm;

    RecyclerView rv;
    MyHelper helper;
    RealmChangeListener realmChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        realm = Realm.getDefaultInstance();
        rv = findViewById(R.id.rv);

        helper = new MyHelper(realm);
        helper.selectFromDB();


        CustomAdapter adapter = new CustomAdapter( this, helper.justRefresh());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

      /*  dt_display=findViewById(R.id.dt_display);

      // Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();
        op=intent.getStringExtra("op");
        dt_display.setText(op);

        // op=extras.getString("op");*/
      reFresh();
    }

    private void reFresh(){

        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                CustomAdapter adapter = new CustomAdapter( view.this, helper.justRefresh());
                rv.setAdapter(adapter);

            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }

}

