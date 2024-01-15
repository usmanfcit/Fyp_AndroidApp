package com.ahsan.myharsystem;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isRecording = false;
    private File file;
    private FileWriter fileWriter;
    private Button startButton;
    private Button stopButton;
    private Button sendDataButton;

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        sendDataButton = findViewById(R.id.sendDataButton);

        startButton.setOnClickListener(view -> startRecording());
        stopButton.setOnClickListener(view -> stopRecording());
        sendDataButton.setOnClickListener(view -> sendDataToServer());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                //showToast("Permission granted. You can start recording.");
            } else {
                // Permission denied
                //showToast("Permission denied. Cannot record data without permission.");
            }
        }
    }

    private void startRecording() {
        if (accelerometer == null) {
            showToast("Your device has not accelerometer, try another device.");
            return;
        }

        if (!isRecording) {
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm ss", Locale.getDefault()).format(new Date());
                String fileName = "sensor_data_" + timeStamp + ".txt";
                //file = new File(Environment.getExternalStorageDirectory(), fileName);
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
                fileWriter = new FileWriter(file, true);
                isRecording = true;
                showToast("Recording is started");
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Error occur starting recording");
                isRecording = false;
            }
        } else {
            showToast("Already recording");
        }
    }

    private void stopRecording() {
        if (isRecording) {
            isRecording = false;
            sensorManager.unregisterListener(this);
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                    showToast("Recording is stopped");
                }
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Error occur stopping recording");
            }
        } else {
            showToast("Not currently recording");
        }
    }

    private void sendDataToServer() {
        if (file != null && file.exists()) {
            SendDataToServerTask.sendDataToServer(file);
        } else {
            showToast("No recorded data found");
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (isRecording && sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float accelerometerX = sensorEvent.values[0];
            float accelerometerY = sensorEvent.values[1];
            float accelerometerZ = sensorEvent.values[2];

            // Hardcoded values for person id and activity
            String data = "PersonID,Activity," + accelerometerX + "," + accelerometerY + "," + accelerometerZ + "\n";

            try {
                fileWriter.write(data);
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Error writing data to file");
                stopRecording(); // Stop recording if there's an error in writing data
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // no need for this method
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRecording) {
            stopRecording();
        }
    }

}
