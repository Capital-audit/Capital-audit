package com.example.capitalaudit.models;
import android.util.Log;

import com.example.capitalaudit.models.payment_class;

import java.util.Vector;

/**
 * This class is used to store the payment data.
 * It contains the vector of payments.
 * It also contains the getter functions for the payments.
 * It also contains the addPaymentToList function to add a payment to the vector.
 * It also contains the clearVector function to clear the vector.
 */
public class PaymentStorage {
    private Vector<payment_class> payments;

    public PaymentStorage()
    {
        payments = new Vector<>();
    }

    /**
     * This function is used to get the payments vector.
     *
     * @return returns a payment_class vector.
     */
    public Vector<payment_class> getPayments()
    {

        return payments;
    }

    /**
     * This function is used to add a payment to the payments vector.
     * @params payment
     */
    public void addPaymentToList(payment_class payment)
    {
        if(payments != null)
        {
            this.payments.add(payment);
        }
        else
        {
            Log.e("paymentStorage", "vector is null");
        }
    }


    /**
     * This function is used to clear the payments vector.
     */
    public void clearVector()
    {
        if(payments != null)
        {
            payments.clear();
        }
    }
}
