package com.example.capitalaudit.models;



public class payment_class {



    int id;
    double price;
    String category;
    boolean cleared;
    String debitCredit;
    String date;
    int user_id;
    public payment_class(int id, double price, String category, boolean cleared, String debitCredit, String date, int user_id)
    {
        this.id = id;
        this.price = price;
        this.category = category;
        this.cleared = cleared;
        this.debitCredit = debitCredit;
        this.date = date;
        this.user_id = user_id;
    }

    public int get_payment_id()
    {
        return id;
    }

    public String getCategory()
    {
        return category;
    }

    public boolean getCleared()
    {
        return cleared;
    }

    public String getDebitCredit()
    {
        return debitCredit;
    }

    public String getDate()
    {
        return date;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public double getPrice() { return this.price;}

}
