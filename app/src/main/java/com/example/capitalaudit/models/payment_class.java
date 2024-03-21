package com.example.capitalaudit.models;


/**
 * This class is used to store the payment data.
 * It contains the ID, price, category, cleared, debit_credit, date, and user_id of the payment.
 * It also contains the getter functions for each of the fields.
 * It also contains the toString function to print the payment data.
 * @params id The ID of the payment.
 * @params price The price of the payment.
 * @params category The category of the payment.
 * @params cleared The cleared status of the payment.
 * @params debit_credit The debit/credit status of the payment.
 * @params date The date of the payment.
 *
 */
public class payment_class {

    int ID;
    double price;
    String category;
    boolean cleared;
    boolean debit_credit;
    String date;
    public payment_class(int id, double price, String category, boolean cleared, boolean debit_credit, String date)
    {
        this.ID = id;
        this.price = price;
        this.category = category;
        this.cleared = cleared;
        this.debit_credit = debit_credit;
        this.date = date;
    }

    public int get_payment_id()
    {
        return ID;
    }

    public String getCategory()
    {
        return category;
    }

    public boolean getCleared()
    {
        return cleared;
    }

    public boolean getDebitCredit()
    {
        return debit_credit;
    }

    public String getDate()
    {
        return date;
    }

    public double getPrice() { return this.price;}

    @Override
    public String toString()
    {
        return "PaymentData[ID=" + ID + ", Price=" + price + ", Category=" + category + ", Debit/Credit=" + debit_credit + ", Cleared=" + cleared + ", Date=" + date + "]";

    }

}
