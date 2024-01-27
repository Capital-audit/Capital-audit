package com.example.capitalaudit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;

public class api_class {
    String access_token;
    public String login_request(String json) throws IOException {
        try {

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            String url = "http://localhost:3834/login";

            URL apiUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setRequestMethod("POST");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode >= 200 && responseCode < 300) {
                String accessToken = connection.getHeaderField("access_token");

                if (accessToken != null && !accessToken.isEmpty()) {
                    setAccess_token(accessToken);
                } else {
                    System.out.println("Access Token not found in headers");
                }
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            connection.disconnect();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
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