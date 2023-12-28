package com.example.fyp_app_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


public class VolunterInfo extends AppCompatActivity {
    private Button okButtonInfoClass;
    private EditText editTextName;
    private EditText editTextAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunter_info);

        okButtonInfoClass=findViewById(R.id.btnOk);
        editTextName=findViewById(R.id.etName);
        editTextAge=findViewById(R.id.etAge);

        okButtonInfoClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save data or perform other actions here...

                // Display a Toast message to indicate that data is saved successfully
                Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
            }

        });



    }
}