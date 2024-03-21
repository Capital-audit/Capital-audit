package com.example.capitalaudit.ActivityFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.capitalaudit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
    * This class is the activity for the dataset page.
    * It is used to display the dataset page.
    * It extends AppCompatActivity class.
    * It overrides the onCreate method.
    * It has a method setUpMenuBar which is used to set up the bottom navigation bar.
    * It has a method startActivity which is used to start the activity.
    * It has a method onCreate which is used to create the activity.
    * It has a method setUpMenuBar which is used to set up the bottom navigation bar.
    * It has a BottomNavigationView object.
    * It has a method setUpMenuBar which is used to set up the bottom navigation bar.
    *
 */
public class DatasetActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpMenuBar();

    }



    @Override
    public void startActivity (Intent intent){
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }



        /*
        * This method is used to set up the bottom navigation bar.
        *
         */
        private boolean setUpMenuBar() {
            setContentView(R.layout.activity_dataset);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.View);
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.Home) {
                    // Handle Home click
                    startActivity(new Intent(DatasetActivity.this, HomeActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.Graph) {
                    startActivity(new Intent(DatasetActivity.this, GraphActivity.class));
                    finish(); // Finish the current activity to prevent going back to it
                    return true;
                } else if (itemId == R.id.New) {
                    // Handle New click
                    startActivity(new Intent(DatasetActivity.this, AddPaymentActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.View) {
                    return true;
                } else if (itemId == R.id.Settings) {
                    // Handle Settings click
                    startActivity(new Intent(DatasetActivity.this, SettingsActivity.class));
                    finish();
                    return true;
                }

                return false;

            });
            overridePendingTransition(0, 0);
            return false;
        }

}