package com.example.capitalaudit;
import java.util.Vector;

public class PaymentStorage {
    private Vector<payment_class> payments;

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


}
