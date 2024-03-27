package com.example.capitalaudit.Utility;
import static java.lang.String.valueOf;

import android.util.Log;

import com.example.capitalaudit.models.payment_class;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Vector;

/**
 * This class is used to convert the data to and from JSON format.
 */
public class json_class
{

    /**
     * This function is used to convert the login data to JSON format.
     * @param username
     * @param password
     * @return
     */
    public static String login_to_json(String username, String password)
    {
        Gson gson = new Gson();
        java.util.Map<String, String> userData = new java.util.HashMap<>();
        userData.put("username", username);
        userData.put("password", password);
        return gson.toJson(userData);
    }

    /**
     * This function is used to convert the payment data to JSON format.
     * @param transactionPriceInt
     * @param selectedCategorry
     * @param isCleared
     * @param selectedDebitCredit
     * @param enteredDate
     * @return
     */
    public static String payment_to_json(int transactionPriceInt, String selectedCategorry, boolean isCleared, boolean selectedDebitCredit, String enteredDate)
    {
        Gson gson = new Gson();
        java.util.Map<String, String> paymentData = new java.util.HashMap<>();
        paymentData.put("price", String.valueOf(transactionPriceInt));
        paymentData.put("category", selectedCategorry); // Assuming getCategory() returns an enum
        paymentData.put("cleared", String.valueOf(isCleared));
        paymentData.put("debitCredit", String.valueOf(selectedDebitCredit)); // Assuming getDebitCredit() returns an enum
        paymentData.put("date", enteredDate);
        return gson.toJson(paymentData);
    }

    /**
     * This function is used to convert the payment data from JSON format.
     * @param payments
     * @return
     */
    public static Vector<payment_class> json_to_payment(String payments)
    {
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(payments).getAsJsonObject();
        if (jsonObject.has("success") && jsonObject.get("success").getAsBoolean()) {
            // This is not an array, but a successful response
            Log.d("json_to_payment", "Success response received");
            return null; // or return an empty vector, depending on your logic
        }
        else
        {
            // This is an array of payment_class objects
            JsonArray jsonArray = jsonObject.getAsJsonArray("paymentDataList");

            Vector<payment_class> paymentClassVector = new Vector<>();

            for (JsonElement element : jsonArray)
            {
                payment_class payment = jsonElementToPayment(element);
                paymentClassVector.add(payment);
            }

            return paymentClassVector;
        }
    }

    /**
     * This function is used to convert the JSON element to a payment_class object.
     * @param element
     * @return
     */
    private static payment_class jsonElementToPayment(JsonElement element)
    {
        // Extract data from the JSON element and create a new payment_class object
        // Replace the following lines with your logic to extract data
        int ID = element.getAsJsonObject().get("ID").getAsInt();
        double price = element.getAsJsonObject().get("price").getAsDouble();
        String category = element.getAsJsonObject().get("category").getAsString();
        boolean cleared = element.getAsJsonObject().get("cleared").getAsBoolean();
        boolean debit_credit = element.getAsJsonObject().get("debit_credit").getAsBoolean();
        String date = element.getAsJsonObject().get("date").getAsString();

        return new payment_class(ID, price, category, cleared, debit_credit, date);
    }
}
