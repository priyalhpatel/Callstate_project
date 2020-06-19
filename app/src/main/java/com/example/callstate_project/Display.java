package com.example.callstate_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    TextView dt_display,cust_name,state;
    Button view;
    FloatingActionButton floatingActionButton;
     Realm realm;
    String state1,number,store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
          realm = Realm.getDefaultInstance();

          cust_name=findViewById(R.id.cust_name);
          state=findViewById(R.id.state);
          view=findViewById(R.id.btn_view);
          note = findViewById(R.id.note);
         floatingActionButton = findViewById(R.id.fab);

         Bundle extras=getIntent().getExtras();
         state1=extras.getString("state1");
         state.setText(state1);
         number=extras.getString("number");
         cust_name.setText(number);



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 save();
                 //display();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display.this,view.class);
                startActivity(intent);
                display();
            }
        });


       /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save();
               RealmResults<Person> realmResults=realm.where(Person.class).findAll();
                for(Person person: realmResults){
                    //String data=note.getText().toString();
                    //  i.putExtra("data",data);
                  // dt_display.setText("");
                   //dt_display.append(person.getFeedback());
                }
                Intent i = new Intent(Display.this,view.class);
                startActivity(i);
            }
        });*/
    }

    public void display(){

        RealmResults<Person>  guests = realm.where(Person.class).findAll();

        String op="";
        realm.beginTransaction();
        for(Person guest : guests){
            op+=guest.toString();

            Intent intent = new Intent(Display.this,view.class);
            intent.putExtra("op",op);

        }
        // dt_display.setText(op);

    }

    public void save() {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxId = realm.where(Person.class).max("feedbackid");
                int newkey = (maxId == null)? 1 :maxId.intValue()+1;

                Person Person = realm.createObject(Person.class,newkey);
                Person.setFeedback(note.getText().toString());
               // Person.setState(state.getText().toString());
                //Person.setName(cust_name.getText().toString());


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