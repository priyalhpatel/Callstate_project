package com.example.callstate_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Contact_Adapter extends BaseAdapter {
    public Filter getFilter;
    private Context context;
    private ArrayList<Contact_Model> arrayList;
    private Filter Filter;

    public Contact_Adapter(Context context, ArrayList<Contact_Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {

        return arrayList.size();
    }

    @Override
    public Contact_Model getItem(int position) {

        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Contact_Model model = arrayList.get(position);
        ViewHodler holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_view, parent, false);
            holder = new ViewHodler();
            holder.contactImage = (ImageView) convertView
                    .findViewById(R.id.contactImage);
            holder.contactName = (TextView) convertView
                    .findViewById(R.id.contactName);
            holder.contactEmail = (TextView) convertView
                    .findViewById(R.id.contactEmail);
            holder.contactNumber = (TextView) convertView
                    .findViewById(R.id.contactNumber);
            holder.contactOtherDetails = (TextView) convertView
                    .findViewById(R.id.contactOtherDetails);

            convertView.setTag(holder);
        } else {


            holder = (ViewHodler) convertView.getTag();

        }

        // Set items to all view
        if (!model.getContactName().equals("")
                && model.getContactName() != null) {
            holder.contactName.setText(model.getContactName());
        } else {
            holder.contactName.setText("No Name");
        }
        if (!model.getContactEmail().equals("")
                && model.getContactEmail() != null) {
            holder.contactEmail.setText("EMAIL ID - n"
                    + model.getContactEmail());
        } else {
            holder.contactEmail.setText("EMAIL ID - n" + "No EmailId");
        }

        if (!model.getContactNumber().equals("")
                && model.getContactNumber() != null) {
            holder.contactNumber.setText("CONTACT NUMBER - n"
                    + model.getContactNumber());
        } else {
            holder.contactNumber.setText("CONTACT NUMBER - n"
                    + "No Contact Number");
        }

        if (!model.getContactOtherDetails().equals("")
                && model.getContactOtherDetails() != null) {
            holder.contactOtherDetails.setText("OTHER DETAILS - n"
                    + model.getContactOtherDetails());
        } else {
            holder.contactOtherDetails.setText("OTHER DETAILS - n"
                    + "Other details are empty");
        }

        // Bitmap for imageview
        Bitmap image = null;
        if (!model.getContactPhoto().equals("")
                && model.getContactPhoto() != null) {
            image = BitmapFactory.decodeFile(model.getContactPhoto());// decode
            // the
            // image
            // into
            // bitmap
            if (image != null)
                holder.contactImage.setImageBitmap(image);// Set image if bitmap
                // is not null
            else {
                image = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.ic_launcher_foreground);// if bitmap is null then set
                // default bitmap image to
                // contact image
                holder.contactImage.setImageBitmap(image);
            }
        } else {
            image = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_launcher_foreground);
            holder.contactImage.setImageBitmap(image);
        }
        return convertView;
    }



    public Filter getFilter() {
        if(Filter==null) {

            Filter= new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                  // FilterResults results=new FilterResults();
                    //if(constraint!=null && constraint.length()>0){
                      //  ArrayList<Contact_Model> filterList=new ArrayList<Contact_Model>();
                        //for(int i=0;i<arrayList.size();i++){
                          //  if((arrayList.get(i).getContactName().toUpperCase())
                            //        .contains(constraint.toString().toUpperCase())) {
                              //  Contact_Model Contacts = new Contact_Model();
                                //Contacts.setContactName(arrayList.get(i).getContactName());
                                //Contacts.setContactNumber(arrayList.get(i).getContactNumber());
                                //filterList.add(Contacts);
                            //}
                        //}
                        //results.count=filterList.size();
                        //results.values=filterList;
                   // }else{
                     //   results.count=arrayList.size();
                       // results.values=arrayList;
                    //}
                    //return results;
                    return null;
                }
               // @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    //Contacts =(ArrayList<Contact_Model>) results.values;
                    //notifyDataSetChanged();
                }
            };

        }

        return Filter;
    }

  /* public Filter getFilter() {
        return null;
    }
*/
    // View holder to hold views
    private class ViewHodler {
        ImageView contactImage;
        TextView contactName, contactNumber, contactEmail, contactOtherDetails;
    }
}