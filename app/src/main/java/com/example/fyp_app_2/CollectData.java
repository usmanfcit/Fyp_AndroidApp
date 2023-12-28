package com.example.fyp_app_2;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.fyp_app_2.Sensor_Manager;


import androidx.appcompat.app.AppCompatActivity;

public class CollectData extends AppCompatActivity {
    private Sensor_Manager mySensorManager;
    private Button startButton;
    private Button stopButton;
    TextView scrollViewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_data);

        startButton=findViewById(R.id.startButton);
        stopButton=findViewById(R.id.stopButton);
        scrollViewTextView=findViewById(R.id.scrollableTextView);

        mySensorManager = new Sensor_Manager(this,scrollViewTextView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySensorManager.registerSensors();
                scrollViewTextView.setText(scrollViewTextView.getText()+"\n"+"Data Collection Started");
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySensorManager.unregisterSensors();
                scrollViewTextView.setText(scrollViewTextView.getText()+"\n"+"Data Collection Stopped");
            }
        });




    }

}
