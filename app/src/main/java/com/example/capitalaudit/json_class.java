package com.example.capitalaudit;
import android.util.Log;

import com.google.gson.Gson;

public class json_class {

    public static String login_to_json(String username, String password)
    {
        Gson gson = new Gson();
        java.util.Map<String, String> userData = new java.util.HashMap<>();
        userData.put("username", username);
        userData.put("password", password);
        String jsonString = gson.toJson(userData);
        Log.d("test", "JSON Query: " + jsonString);
        return jsonString;
    }

    public static boolean payement_to_json()
    {
        return false;
    }
}
