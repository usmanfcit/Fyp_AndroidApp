package com.example.fyp_app_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button infoButton;
    Button collectDataButton;
    Button registerDeviceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    infoButton=findViewById(R.id.volunteerInfoButton);
    collectDataButton=findViewById(R.id.collectDataButton);
    registerDeviceButton=findViewById(R.id.RegisterDeviceButton);

        infoButton.setOnClickListener(new  View.OnClickListener(){
        @Override
        public void onClick(View view){
            Intent intent = new Intent(MainActivity.this,VolunterInfo.class);
            startActivity(intent);
        }
    });
    collectDataButton.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,CollectData.class);
                startActivity(intent);

            }
        });
    registerDeviceButton.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,RegisterBlueToothDevice.class);
                startActivity(intent);

            }
        });
    }
}