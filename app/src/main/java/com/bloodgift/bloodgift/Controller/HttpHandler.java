package com.bloodgift.bloodgift.Controller;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            Log.e("InfoBlood", response);
        } catch (MalformedURLException e) {
            Log.e("InfoBlood", "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e("InfoBlood", "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e("InfoBlood", "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e("InfoBlood", "Exception: " + e.getMessage());
        }
        return response;
    }

    /**
     * Load Json file from stream and return it as String
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}