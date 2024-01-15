package com.ahsan.myharsystem;

//import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SendDataToServerTask {

    public static void sendDataToServer(File dataFile) {
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            boolean success = sendDataInBackground(dataFile);

            if (success) {
                System.out.println("Data sent to server successfully");
            } else {
                System.out.println("Failed to send data to server");
            }
        });
    }

    private static boolean sendDataInBackground(File dataFile) {
        boolean success = false;

        try {
            Socket socket = new Socket("192.168.100.17", 3001);

            OutputStream outputStream = socket.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(dataFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            socket.close();

            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }

        return success;
    }
}
