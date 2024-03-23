package com.example.capitalaudit.API;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.Utility.json_class;
import com.example.capitalaudit.models.PaymentStorage;
import com.example.capitalaudit.models.payment_class;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * api_class is the class that is used to make the API calls to the server.
 * Functions in this class are used to make the login request, add payment request and fetch data request.
 * Functions:
 * 1. login_request: This function is used to make the login request to the server.
 * 2. add_payment_api: This function is used to make the add payment request to the server.
 * 3. fetchDataFromServer: This function is used to fetch the data from the server.
 * 4. setAccess_token: This function is used to set the access token.
 * 5. api_class: This is the constructor for the api_class.
 * Variables:
 * 1. access_token: This is the access token that is used to authenticate the user.
 */
public class api_class {


    private String access_token;


    /**
     * This is the constructor for the api_class.
     * It takes the access token as input.
     * @param access_token
     */
    public api_class(String access_token)
    {
        this.access_token = access_token;
    }



    /**
     * This function is used to make the login request to the server.
     * It takes a json string as input and returns an api_response object.
     * The api_response object contains a boolean value which is true if the request was successful and false if the request failed.
     * The api_response object also contains a string which is the response from the server.
     * @param json
     * @return
     * @throws IOException
     */
    public api_response login_request(String json) throws IOException
    {

        try
        {
            Log.d("test", "json" + json);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            String url = "http:///192.168.56.1:8080/login";

            URL apiUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet())
                {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            Log.d("Test", "Request Headers: " + connection.getRequestProperties());

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);
            connection.getOutputStream().write(json.getBytes("UTF-8"));

            int responseCode = connection.getResponseCode();
            Log.d("Test", "Response error: " + responseCode);


            if (responseCode >= 200 && responseCode < 300)
            {


                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null)
                {
                    response.append(line);
                }

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                boolean success = jsonResponse.getAsJsonPrimitive("success").getAsBoolean();
                String errorMessage = jsonResponse.has("error_message") ?
                        jsonResponse.getAsJsonPrimitive("error_message").getAsString() : "";

                if (jsonResponse.has("access_token"))
                {
                    String accessToken = jsonResponse.getAsJsonPrimitive("access_token").getAsString();
                    setAccess_token(accessToken);
                }
                else
                {
                    Log.d("Test", "Access Token not found in JSON response");
                }

                connection.disconnect();

                if (success)
                {
                    return new api_response(true, response.toString());
                }
                else
                {
                    throw new IOException(errorMessage);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.d("Test", "Exception caused in api_class" + e);
            return new api_response(false, e.getMessage());
        }
        return new api_response(false, null);

    }



    /**
     * This function is used to set the access token.
     * @param access_token
     */
    private void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }



    /**
     * This function is used to make the add payment request to the server.
     * It takes a json string as input and returns an api_response object.
     * The api_response object contains a boolean value which is true if the request was successful and false if the request failed.
     * @param json
     * @return
     * @throws IOException
     */
    public api_response add_payment_api(String json) throws IOException
    {
        try
        {
            Log.d("test", "json" + json);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("access_token", this.access_token);
            String url = "http://192.168.56.1:8080/postPaymentData";
            URL apiUrl = new URL(url);
            Log.d("test", "0");

            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            if (!headers.isEmpty()) {
                Log.d("test", "failing here");
                for (Map.Entry<String, String> entry : headers.entrySet())
                {
                    Log.d("test", "2");

                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            else
            {
                Log.d("test", "headers empty");
            }

            Log.d("add data", "Request Headers: " + connection.getRequestProperties());

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.getOutputStream().write(json.getBytes("UTF-8"));

            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            Log.d("add data", "Response error: " + responseCode);
            Log.d("add data", "Response message: " + responseMessage);

            if (responseCode >= 200 && responseCode < 300)
            {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    response.append(line);
                }
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                boolean success = jsonResponse.getAsJsonPrimitive("success").getAsBoolean();
                String errorMessage = jsonResponse.has("error_message") ? jsonResponse.getAsJsonPrimitive("error_message").getAsString() : "";
                connection.disconnect();

                if (success)
                {
                    Vector<payment_class> paymentClassVector = new Vector<>();
                    CapitalAudit cp = CapitalAudit.getInstance();
                    PaymentStorage ps = cp.getStorage();
                    paymentClassVector = json_class.json_to_payment(String.valueOf(jsonResponse));
                    ps.clearVector();
                    assert paymentClassVector != null;

                    for (payment_class payment : paymentClassVector) {
                        ps.addPaymentToList(payment);
                    }
                    return new api_response(true, response.toString());

                }
                else
                {
                    throw new IOException(errorMessage);
                }
            }
            else
            {
                Log.d("AddDataAPI", String.valueOf(responseCode));
                return new api_response(false, responseMessage);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.d("Test", "Exception caused in api_class" + e);
            return new api_response(false, e.getMessage());
        }
    }



    /**
     * This function is used to fetch the data from the server.
     * It returns an api_response object.
     * The api_response object contains a boolean value which is true if the request was successful and false if the request failed.
     * The api_response object also contains a string which is the response from the server.
     *
     * @return
     * @throws IOException
     */
    public api_response fetchDataFromServer() throws IOException
    {
        try
        {
            //This is sending the request, it sends the headers and a get request to the correct endpoint
            Log.d("data request", "Refresh data");
            String url = "http://192.168.56.1:8080/getPaymentData";
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("access_token", this.access_token);

            //Checking if the response code is between 200 and 300, 200 means that it was a success.
            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300)
            {

                //We then read the response from the sever, and use a stringbuilder to to create the json string in java.
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
                {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }

                    //We then return a api_response, which is true along with the response.toString.
                    connection.disconnect();
                    return new api_response(true, response.toString());
                }
            }
            else
            {
                //If the response code is outside of 200-300 we reply with this api_response code.
                return new api_response(false, "HTTP error: " + responseCode);
            }
        }
        catch (IOException e)
        {
            //Any exceptions within the try block get caught here. Printing the stack trace and then logging.
            e.printStackTrace();
            Log.e("Test", "Exception caused in api_class" + e);
            return new api_response(false, e.getMessage());
        }
    }
}

