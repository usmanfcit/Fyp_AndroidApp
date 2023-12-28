package com.example.fyp_app_2;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendDataToServer extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {
        try {
            String serverIp = "192.168.10.2"; // Replace with your laptop's IP address
            int serverPort = 49152; // Use the same port as the server

            Socket socket = new Socket(serverIp, serverPort);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(params[0]);

            writer.close();
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            // Handle UnknownHostException (e.g., incorrect IP address)
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException (e.g., network error)
        }
        return null;
    }
}
