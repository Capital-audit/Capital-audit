package com.example.capitalaudit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.capitalaudit.json_class.*;
import com.example.capitalaudit.button_class.*;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signInButton = findViewById(R.id.login_btn);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    button_class.login_button(
                            ((EditText) findViewById(R.id.username_input)).getText().toString(),
                            ((EditText) findViewById(R.id.password_input)).getText().toString()
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}