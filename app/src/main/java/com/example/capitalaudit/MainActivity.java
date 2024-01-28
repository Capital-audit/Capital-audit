package com.example.capitalaudit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.capitalaudit.json_class.*;
import com.example.capitalaudit.button_class.*;
import android.util.Log;


import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signInButton = findViewById(R.id.login_btn);
        button_class.login_button(signInButton, MainActivity.this);    }
}