package com.example.capitalaudit;

import android.app.Application;

import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.models.PaymentStorage;

/**
 * This class is used to store the application data.
 * It contains the api and storage objects.
 * It also contains the getter functions for the api and storage objects.
 */
public class CapitalAudit extends Application
{
    private static CapitalAudit capitalAudit;
    private api_class api;
    private PaymentStorage storage;

    private static String username;
    @Override
    public void onCreate()
    {
        super.onCreate();
        capitalAudit = this;
        this.api = new api_class(null);
        this.storage = new PaymentStorage();
    }

    public static CapitalAudit getInstance()
    {
        return capitalAudit;
    }

    public api_class getApi()
    {
        return this.api;
    }

    public PaymentStorage getStorage()
    {
        return storage;
    }

    public static void setUsername(String username) {
        CapitalAudit.username = username;
    }

    public static String getUsername()
    {
        return CapitalAudit.username;
    }
}

