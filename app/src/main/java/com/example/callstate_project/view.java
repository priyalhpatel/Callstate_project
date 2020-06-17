package com.example.callstate_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class view extends AppCompatActivity {

    TextView dt_display;
    String feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        dt_display=findViewById(R.id.dt_display);

        Bundle extras = getIntent().getExtras();
        feedback=extras.getString("feedback");
        dt_display.setText(feedback);
    }
}
