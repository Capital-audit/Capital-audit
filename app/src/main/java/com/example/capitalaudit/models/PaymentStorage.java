package com.example.capitalaudit.models;
import com.example.capitalaudit.models.payment_class;

import java.util.Vector;

public class PaymentStorage {
    private  Vector<payment_class> payments;

    public PaymentStorage()
    {

    }

    public void setPayments(Vector<payment_class> payments)
    {

        this.payments = payments;
    }

    public Vector<payment_class> getPayments()
    {

        return payments;
    }

    public void addPaymentToList(payment_class payment)
    {
        this.payments.add(payment);
    }

    public void deletePaymentFromList(payment_class payment)
    {

    }


}