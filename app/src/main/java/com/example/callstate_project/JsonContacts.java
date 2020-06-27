package com.example.callstate_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonContacts extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String Displayname="name",contact="contact",email="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_contacts);

        listView=findViewById(R.id.list_view);

        String contacts = "{\"Contacts\":[{\"ID\":101,\"name\":\"Doe\",\"email\":\"John@gmail.com\",\"contact\":9876543200}," +
                "{\"ID\":102,\"name\":\"Doe\",\"email\":\"John@gmail.com\",\"contact\":9874532201},\n" +
                "{\"ID\":103,\"name\":\"Eoe\",\"email\":\"John@gmail.com\",\"contact\":9874563201},\n" +
                "{\"ID\":104,\"name\":\"Foe\",\"email\":\"John@gmail.com\",\"contact\":9870987601},\n" +
                "{\"ID\":105,\"name\":\"Goe\",\"email\":\"John@gmail.com\",\"contact\":9876543211},\n" +
                "{\"ID\":106,\"name\":\"Hoe\",\"email\":\"John@gmail.com\",\"contact\":9876543455},\n" +
                "{\"ID\":107,\"name\":\"Ioe\",\"email\":\"John@gmail.com\",\"contact\":9876543200},\n" +
                "{\"ID\":108,\"name\":\"Joe\",\"email\":\"John@gmail.com\",\"contact\":9876543201},\n" +
                "{\"ID\":109,\"name\":\"Koe\",\"email\":\"John@gmail.com\",\"contact\":9873423201}]"+"}";

        try {
            JSONObject jsonObject = new JSONObject(contacts);
            JSONArray jsonArray = jsonObject.getJSONArray("Contacts");
            for(int i=0; i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                String Id = object.getString("ID");
                String name = object.getString("name");
                String email = object.getString("email");
                String contact = object.getString("contact");
                arrayList.add(Id + ")"+name+"\n"+email+"\n"+contact);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        arrayAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);

        ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        if (Displayname != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            Displayname).build());
        }

        if (contact != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
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
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(JsonContacts.this,"Exception"+e.getMessage(),Toast.LENGTH_LONG).show();
        }



    }
}
