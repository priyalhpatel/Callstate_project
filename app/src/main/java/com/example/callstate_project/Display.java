package com.example.callstate_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.callstate_project.Model.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class Display extends AppCompatActivity {
    EditText note;
    TextView cust_name;
    FloatingActionButton floatingActionButton;
     Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
          realm = Realm.getDefaultInstance();

         // cust_name.setText("+phone_number");

        note = findViewById(R.id.note);
         floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(Display.this,"click",Toast.LENGTH_LONG).show();
                 save();
            }
        });
    }

    public void save() {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxId = realm.where(Person.class).max("feedbackid");
                int newkey = (maxId == null)? 1 :maxId.intValue()+1;

                Person Person = realm.createObject(Person.class,newkey);
                Person.setFeedback(note.getText().toString());

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(Display.this, "Added", Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(Display.this,"Not added",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}