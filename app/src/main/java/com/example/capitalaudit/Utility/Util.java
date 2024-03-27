package com.example.capitalaudit.Utility;
import android.util.Log;

import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.models.PaymentStorage;
import com.example.capitalaudit.models.payment_class;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * This class is used to provide utility functions.
 */
public class Util {

    /**
     * This function is used to hash credentials before sending them to the backend.
     * @param cred A string either username or password to be encrypted by SHA-256.
     * @return returns a string of the hashed credentials.
     */
    public static String cred_hasher(String cred)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = digest.digest(cred.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes)
            {
                String hex = Integer.toHexString(0xff & hashedByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;

        }
    }

    /**
     * This function is used to filter the data by the number of months.
     *
     * @param numMonths The number of months to filter the data by. for example 1, will relate to the current month. 2 will relate to the current month and the previous month.
     * @return returns a list of payment_class objects that are within the number of months.
     */
    public static List<payment_class> filterDataByMonths(int numMonths)
    {
        Vector<payment_class> ps = CapitalAudit.getInstance().getStorage().getPayments();
        //I need logic to somehow get data of the month number, So i have the numMonths that i want to look at.
        //Say its december and i input "2" i want december and november data. This should go all the way to twelve ( a whole year)

        List<payment_class> filteredData = new ArrayList<>();

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1;

        for (payment_class payment : ps)
        {
            String date = payment.getDate(); // Will return a String, date = "2022-05-22"
            String[] parts = date.split("-");
            Log.d("FilteredDataByMonths", "Processing payment: " + payment.toString());
            int paymentYear = Integer.parseInt(parts[0]);
            int paymentMonth = Integer.parseInt(parts[1]);
            //Compare this date with the dates i want to view.
            //Then add to fliteredData and then return.
            int monthsDifference = (currentYear - paymentYear) * 12 + (currentMonth - paymentMonth);
            Log.d("FilteredDataByMonths", "Months difference: " + monthsDifference);
            if (monthsDifference >= 0 && monthsDifference < numMonths) {
                filteredData.add(payment);
                Log.d("filtered data", payment.toString());
            }
        }

        return filteredData;
    }

    private String findMostCommonCategory(List<payment_class> payments) {
        Map<String, Integer> categoryCountMap = new HashMap<>();

        // Count the occurrences of each category
        for (payment_class payment : payments) {
            String category = payment.getCategory();
            if (categoryCountMap.containsKey(category)) {
                categoryCountMap.put(category, categoryCountMap.get(category) + 1);
            } else {
                categoryCountMap.put(category, 1);
            }
        }

        // Find the category with the highest count
        String mostCommonCategory = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : categoryCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonCategory = entry.getKey();
            }
        }
        return mostCommonCategory;
    }



}
