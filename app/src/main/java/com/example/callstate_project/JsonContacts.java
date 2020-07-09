package com.example.callstate_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// To add contacts from json array
public class JsonContacts extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String alertDialog;
    String rawContactId;
    String name="",contact="",email="",id="";
    private Object background;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_contacts);

        listView = findViewById(R.id.list_view);

        //getting contact from json array


        String contacts = "{\"Contacts\":[{\"ID\":101,\"name\":\"DD\",\"email\":\"John@gmail.com\",\"contact\":9876543200}," +

                "{\"ID\":108,\"name\":\"BB\",\"email\":\"John@gmail.com\",\"contact\":9876543201},\n" +
                "{\"ID\":109,\"name\":\"CC\",\"email\":\"John@gmail.com\",\"contact\":123}]" + "}";


        //AlertDialog box when contacts match

         ArrayList<ContentProviderOperation> add =
               new ArrayList<ContentProviderOperation>();



        try {
            JSONObject jsonObject = new JSONObject(contacts);
            JSONArray jsonArray = jsonObject.getJSONArray("Contacts");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                id = object.getString("ID");
                name = object.getString("name");
                email = object.getString("email");
                contact = object.getString("contact");


                // calling method to check existing contacts
                boolean flag = contactExists(getApplicationContext(),contact,name,email);
                Log.d("contact","contact"+i+"name"+name+"flag"+flag);
                Log.d("contact","contact"+i+"contact"+contact+"flag"+flag);



                if(flag){

                    // if contacts exist in phone book
                   Log.d("contact","contact ifff"+i+"name"+name+"flag"+flag);
                   Log.d("contact","contact iff"+i+"contact"+contact+"flag"+flag);

                    Toast.makeText(JsonContacts.this,"Match",Toast.LENGTH_LONG).show();
                    // to show the AlertDialog
                    final AlertDialog.Builder alert = new AlertDialog.Builder(JsonContacts.this);
                    View mView = getLayoutInflater().inflate(R.layout.customdialog,null);
                    Button replace = (Button)mView.findViewById(R.id.replace);
                    Button newcontact = (Button)mView.findViewById(R.id.newcontact);

                    alert.setView(mView);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setMessage("Duplicate" +" "+ name + " "+contact+ "contact exits");

                   /* Intent intent =new Intent();
                    intent.putExtra("name",name);
                    intent.putExtra("contact",contact);*/

                    alertDialog.setCanceledOnTouchOutside(false);


                    replace.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            Toast.makeText(JsonContacts.this,"Clicked",Toast.LENGTH_LONG).show();

                            final ArrayList<ContentProviderOperation> add =
                                    new ArrayList<ContentProviderOperation>();
                            String where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND "
                                    + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] nameParams = new String[]{rawContactId,
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                            String[] contactParams = new String[]{rawContactId,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};

                            if (!name.isEmpty()) {
                                add.add(android.content.ContentProviderOperation.newUpdate(
                                        android.provider.ContactsContract.Data.CONTENT_URI)
                                        .withSelection(where, nameParams)
                                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                                name).build());
                            }

                            if (!contact.isEmpty()) {
                                add.add(android.content.ContentProviderOperation.newUpdate(
                                        android.provider.ContactsContract.Data.CONTENT_URI)
                                        .withSelection(where, contactParams)
                                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact)
                                        .build());
                            }
                        }
                    });
                    newcontact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<ContentProviderOperation> add =
                                    new ArrayList<ContentProviderOperation>();

                         /*   Intent intent = getIntent();
                            intent.getStringExtra(name);
                            intent.getStringExtra(contact);*/

                            add.add(ContentProviderOperation.newInsert(
                                    ContactsContract.RawContacts.CONTENT_URI)
                                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                                    .build());
                            Log.d("name","done"+name);

                                add.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                        .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                                        .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                        .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name)
                                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact)
                                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)

                                        // .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, name)
                                        .build());

                            Log.d("name","done"+contact);


                             /*   add.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                        .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact)
                                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                        .build());*/


                            alertDialog.dismiss();

                        }

                    });
                    alertDialog.show();



                }
                else  {

                    // to add new contacts if not exist
                   // alertDialog.dismiss();
                    Toast.makeText(JsonContacts.this,"Not match And Contacts Added ",Toast.LENGTH_LONG).show();

                    ArrayList<ContentProviderOperation> ops =
                            new ArrayList<ContentProviderOperation>();


                    ops.add(ContentProviderOperation.newInsert(
                            ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .build());

                    if (name != null) {
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name)
                                // .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, name)
                                .build());
                    }

                    if (contact != null) {
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact)
                                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                .build());
                    }

                    if (email != null) {
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .build());
                    }

                    try {
                        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                        getContentResolver().applyBatch(ContactsContract.AUTHORITY,add);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Toast.makeText(, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(JsonContacts.this, "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }


        }catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //method to check the existing contacts in phone book
    public boolean contactExists(Context context, String contact, String name,String email) {

        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(contact));
        Uri lookup = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(name));
        Uri look=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(email));

        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME,ContactsContract.PhoneLookup.DATA_ID};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        Cursor cur1=context.getContentResolver().query(lookup,mPhoneNumberProjection,null,null,null);
        Cursor cur2 = context.getContentResolver().query(look,mPhoneNumberProjection,null,null,null);

        try {
            if (cur.moveToFirst()||cur1.moveToNext()||cur2.moveToLast()) {


                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }

}