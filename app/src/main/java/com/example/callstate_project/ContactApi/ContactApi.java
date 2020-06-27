package com.example.callstate_project.ContactApi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.callstate_project.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi extends AppCompatActivity {

    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_api);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonplaceholderapi jsonplaceholderapi = retrofit.create(jsonplaceholderapi.class);

        Call<List<post>> call = jsonplaceholderapi.getposts();

        call.enqueue(new Callback<List<post>>() {
            @Override
            public void onResponse(Call<List<post>> call, Response<List<post>> response) {

                if (!response.isSuccessful()){
                    textViewResult.setText("code: " + response.code());
                    return;
                }
                List<post> posts = response.body();

                for (post post :posts){
                    String content = "";
                    content += "ID: " +post.getId() +"\n\n";
                    content += "User id: " +post.getId() +"\n\n";
                    content += "Title " +post.getTitle() +"\n\n";
                    content += "Text " +post.getText() +"\n\n\n";

                    textViewResult.append(content);

                }


            }

            @Override
            public void onFailure(Call<List<post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });


    }
}
