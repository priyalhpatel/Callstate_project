package com.example.callstate_project.Helper;

import com.example.callstate_project.Model.Person;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyHelper {

    Realm realm;
    RealmResults<Person> person;



    public MyHelper(Realm realm) {
        this.realm = realm;
    }
    public void selectFromDB(){

        person = realm.where(Person.class).findAll();
    }

    public ArrayList<Person> justRefresh(){

        ArrayList<Person> listitem = new ArrayList<>();
        for (Person p : person){
            listitem.add(p);
        }

        return listitem;

    }
}
