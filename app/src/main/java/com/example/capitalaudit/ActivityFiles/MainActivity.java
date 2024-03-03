package com.example.capitalaudit.ActivityFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.button_classes.Login_button_class;
import com.example.capitalaudit.R;
import com.example.capitalaudit.API.api_class;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CapitalAudit capitalAudit = CapitalAudit.getInstance();
        api_class api = capitalAudit.getApi();

        Login_button_class loginButtonClass = new Login_button_class(MainActivity.this, api);
        Button loginButton = findViewById(R.id.login_btn);
        loginButtonClass.login_button(loginButton, MainActivity.this);

    }
}