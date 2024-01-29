package com.example.capitalaudit;
import static java.lang.String.valueOf;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Vector;

public class json_class {

    public static String login_to_json(String username, String password)
    {
        Gson gson = new Gson();
        java.util.Map<String, String> userData = new java.util.HashMap<>();
        userData.put("username", username);
        userData.put("password", password);
        return gson.toJson(userData);
    }

    public static String payement_to_json(payment_class payment)
    {
        Gson gson = new Gson();
        java.util.Map<String, String> paymentData = new java.util.HashMap<>();
        paymentData.put("payment_id", valueOf(payment.get_payment_id()));
        paymentData.put("price", valueOf(payment.getPrice()));
        paymentData.put("category", payment.getCategory().toString()); // Assuming getCategory() returns an enum
        paymentData.put("cleared", valueOf(payment.getCleared()));
        paymentData.put("debitCredit", payment.getDebitCredit().toString()); // Assuming getDebitCredit() returns an enum
        paymentData.put("date", payment.getDate());
        paymentData.put("user_id", valueOf(payment.getUser_id()));
        return gson.toJson(paymentData);
    }

    public static Vector<payment_class> json_to_payment(String payments)
    {

        return new Vector<>();
    }
}
