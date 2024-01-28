package com.example.capitalaudit;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import com.example.capitalaudit.api_response.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class api_class {
    String access_token;
    public api_response login_request(String json) throws IOException {

        try {
            Log.d("test", "json" + json);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            String url = "http://10.0.2.2:3834/login";

            URL apiUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            Log.d("Test", "Request Headers: " + connection.getRequestProperties());

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);
            connection.getOutputStream().write(json.getBytes("UTF-8"));

            int responseCode = connection.getResponseCode();
            Log.d("Test", "Response error: " + responseCode);


            if (responseCode >= 200 && responseCode < 300) {


                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                boolean success = jsonResponse.getAsJsonPrimitive("success").getAsBoolean();
                String errorMessage = jsonResponse.has("error_message") ?
                        jsonResponse.getAsJsonPrimitive("error_message").getAsString() : "";

                if (jsonResponse.has("access_token")) {
                    String accessToken = jsonResponse.getAsJsonPrimitive("access_token").getAsString();
                    setAccess_token(accessToken);
                } else {
                    Log.d("Test", "Access Token not found in JSON response");
                }

                connection.disconnect();

                if (success) {
                    return new api_response(true, response.toString());
                } else {
                    throw new IOException(errorMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Test", "Exception caused in api_class" + e);
            return new api_response(false, e.getMessage());
        }
        return new api_response(false, null);

    }


    public static boolean check_login_response()
    {
        return false;
    }

    private void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }
}