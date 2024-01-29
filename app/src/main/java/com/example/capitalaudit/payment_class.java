package com.example.capitalaudit;



public class payment_class {


    public enum debit_credit
    {
        CREDIT,
        DEBIT
    }
    public enum CATEGORY
    {
        BILLS,
        FOOD,
        CLOTHING,
        RENT,
        MISCELLANEOUS
    }
    int id;
    double price;
    CATEGORY category;
    boolean cleared;
    debit_credit debitCredit;
    String date;
    int user_id;
    payment_class(int id, CATEGORY category, boolean cleared, debit_credit debitCredit, String date, int user_id)
    {
        this.id = id;
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

    public CATEGORY getCategory()
    {
        return category;
    }

    public boolean getCleared()
    {
        return cleared;
    }

    public debit_credit getDebitCredit()
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
