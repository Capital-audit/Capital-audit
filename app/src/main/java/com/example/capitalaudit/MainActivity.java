package com.example.capitalaudit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.capitalaudit.json_class.*;
import android.util.Log;


import java.io.IOException;

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