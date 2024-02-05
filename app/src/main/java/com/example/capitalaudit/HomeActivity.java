package com.example.capitalaudit;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HomeActivity extends AppCompatActivity {

    CapitalAudit capitalAudit;
    api_class api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpAudit();
        FetchData();
        menuBarSetup();
        TextView displayTextView = findViewById(R.id.displayTextView);
        displayTextView.setText(welcomeNameColor());
        TextView montlySpendTextView = findViewById(R.id.monthly_spend);
        montlySpendTextView.setText(setMonthlySpend());
        setHomeGraph();
        setHomeTable(this);

    }

    private boolean menuBarSetup() {
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Home) {

                return true;
            } else if (itemId == R.id.Graph) {
                startActivity(new Intent(HomeActivity.this, GraphActivity.class));
                finish(); // Finish the current activity to prevent going back to it
                return true;
            } else if (itemId == R.id.New) {
                // Handle New click
                startActivity(new Intent(HomeActivity.this, AddPaymentActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.View) {
                // Handle View click
                startActivity(new Intent(HomeActivity.this, DatasetActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.Settings) {
                // Handle Settings click
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                finish();
                return true;
            }

            return false;

        });
        return false;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void FetchData() {
        FetchData fetchData = new FetchData(api, HomeActivity.this);
        fetchData.FetchDataFromServer();
    }

    private void setUpAudit() {
        capitalAudit = CapitalAudit.getInstance();
        api = capitalAudit.getApi();
    }

    private SpannableString welcomeNameColor() {
        String username = CapitalAudit.getUsername();

        SpannableString spannableString = new SpannableString("Welcome " + username);

        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4BB73A")), 7, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private void setHomeGraph() {
        GraphView graph = (GraphView) findViewById(R.id.home_top_graph);
        GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
        gridLabelRenderer.setHorizontalAxisTitle("Month");
        gridLabelRenderer.setVerticalAxisTitle("Spend");
        gridLabelRenderer.setLabelsSpace(15);
        gridLabelRenderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);



        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        series.setColor(getResources().getColor(R.color.primary_color));
        graph.addSeries(series);
    }


    private SpannableString setMonthlySpend() {

        String monthly = "192.20";
        SpannableString spannableString = new SpannableString("Spent this month $" + monthly);

        int startBoldIndex = 16;
        int endBoldIndex = spannableString.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4BB73A")), startBoldIndex, endBoldIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startBoldIndex, endBoldIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    private void setHomeTable(Context context) {
        Log.d("TableDebug", "setHomeTable called");

        String[][] tableData = {
                {"Date", "Amount", "Category"},
                {"2022-01-01", "$50.00", "Bills"},
                {"2022-01-02", "$30.00", "Bills"},
                {"2022-01-03", "$20.00", "Bills"},
                // Add more rows as needed
        };

        Log.d("TableDebug", "Table data length: " + tableData.length);

        TableLayout tableLayout = ((Activity) context).findViewById(R.id.tableLayout);

        for (String[] row : tableData) {
            TableRow tableRow = new TableRow(this);


            for (String cell : row) {
                TextView textView = new TextView(this);
                textView.setText(cell);
                textView.setTextSize(20);
                textView.setPadding(16, 8, 16, 8);
                textView.setBackgroundResource(R.drawable.cell_background);
                tableRow.addView(textView);
            }

            tableLayout.addView(tableRow);
        }
    }
}