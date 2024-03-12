package com.example.capitalaudit.Utility;
import static java.lang.String.valueOf;

import com.example.capitalaudit.models.payment_class;
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

    public static String payment_to_json(int transactionPriceInt, String selectedCategorry, boolean isCleared, String selectedDebitCredit, String enteredDate)
    {
        Gson gson = new Gson();
        java.util.Map<String, String> paymentData = new java.util.HashMap<>();
        paymentData.put("price", String.valueOf(transactionPriceInt));
        paymentData.put("category", selectedCategorry); // Assuming getCategory() returns an enum
        paymentData.put("cleared", String.valueOf(isCleared));
        paymentData.put("debitCredit", selectedDebitCredit); // Assuming getDebitCredit() returns an enum
        paymentData.put("date", enteredDate);
        return gson.toJson(paymentData);
    }

    public static Vector<payment_class> json_to_payment(String payments)
    {

        return new Vector<>();
    }
}
