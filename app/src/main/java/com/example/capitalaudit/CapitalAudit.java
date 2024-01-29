package com.example.capitalaudit;

import android.app.Application;
import android.content.Context;

public class CapitalAudit extends Application
{
    private button_class buttonClass;

    @Override
    public void onCreate() {
        super.onCreate();
        buttonClass = new button_class();
    }

    public button_class getButtonClass() {
        return buttonClass;
    }
}

