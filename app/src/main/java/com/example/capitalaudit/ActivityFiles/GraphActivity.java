package com.example.capitalaudit.ActivityFiles;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.R;
import com.example.capitalaudit.Utility.Util;
import com.example.capitalaudit.models.PaymentStorage;
import com.example.capitalaudit.models.payment_class;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/*
    * GraphActivity class is responsible for displaying the graph of the data.
    * It extends AppCompatActivity class.
 */
public class GraphActivity extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setUpNavBar();
    }



    /*
        * setUpNavBar method is responsible for setting up the bottom navigation bar.
        * It returns a boolean value.
     */
    private boolean setUpNavBar()
    {
        setContentView(R.layout.activity_graph);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Graph);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Home)
            {
                // Handle Home click
                startActivity(new Intent(GraphActivity.this, HomeActivity.class));
                finish();
                return true;
            }
            else if (itemId == R.id.Graph)
            {
                startActivity(new Intent(GraphActivity.this, GraphActivity.class));
                finish(); // Finish the current activity to prevent going back to it
                return true;
            }
            else if (itemId == R.id.New)
            {
                // Handle New click
                startActivity(new Intent(GraphActivity.this, AddPaymentActivity.class));
                finish();
                return true;
            }
            else if (itemId == R.id.View)
            {
                return true;
            }
            else if (itemId == R.id.Settings)
            {
                // Handle Settings click
                startActivity(new Intent(GraphActivity.this, SettingsActivity.class));
                finish();
                return true;
            }

            return false;

        });
        overridePendingTransition(0, 0);
        return false;
    }



    @Override
    public void startActivity(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }



    public void TransactionFrequencySetup() {
        CapitalAudit capitalAudit = CapitalAudit.getInstance();
        PaymentStorage ps = capitalAudit.getStorage();
        Vector<payment_class> paymentClassVector = ps.getPayments();

        if (paymentClassVector != null) {
            GraphView graph = findViewById(R.id.TransactionFrequencyGraph);
            GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
            gridLabelRenderer.setHorizontalAxisTitle("Month");
            gridLabelRenderer.setVerticalAxisTitle("Number of Transactions");
            gridLabelRenderer.setLabelsSpace(15);
            gridLabelRenderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>();

            for (int i = 0; i < 12; i++) {
                List<payment_class> monthPayments = Util.filterDataByMonths(i + 1); // Filtering data for each of the past 12 months
                int numTransactions = monthPayments.size(); // Counting the number of transactions for the month
                series.appendData(new DataPoint(i + 1, numTransactions), true, 12); // Adding data point for each month
            }

            graph.addSeries(series);
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        // For x-axis labels (months), use integer formatting
                        return String.valueOf((int) value);
                    } else {
                        // For y-axis labels, use default formatting
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
        } else {
            Log.e("setGraph", "storage is null");
        }
    }


    // Helper method to find the most common category in a list of payments

    public void SpentEachMonthGraphSetup() {
        CapitalAudit capitalAudit = CapitalAudit.getInstance();
        PaymentStorage ps = capitalAudit.getStorage();

        if (ps != null) {
            GraphView graph = findViewById(R.id.SpentEachMonthGraph);
            GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
            gridLabelRenderer.setHorizontalAxisTitle("Month");
            gridLabelRenderer.setVerticalAxisTitle("Spend");
            gridLabelRenderer.setLabelsSpace(15);
            gridLabelRenderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);

            // Initialize an array to store total spending for each month
            double[] totalSpendingPerMonth = new double[12];

            // Loop over each month (1-12)
            for (int i = 1; i <= 12; i++) {
                // Filter payments for the current month
                List<payment_class> paymentsForMonth = Util.filterDataByMonths(i);

                // Calculate total spending for the current month
                double totalSpending = 0.0;
                for (payment_class payment : paymentsForMonth) {
                    totalSpending += payment.getPrice();
                }

                // Store total spending for the current month
                totalSpendingPerMonth[i - 1] = totalSpending;
            }

            // Create data points for each month's total spending
            List<DataPoint> dataPoints = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                dataPoints.add(new DataPoint(i, totalSpendingPerMonth[i - 1]));
            }

            // Create series and add it to the graph
            DataPoint[] dataPointArray = dataPoints.toArray(new DataPoint[0]);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArray);
            series.setColor(getResources().getColor(R.color.primary_color));
            graph.addSeries(series);

            // Customize label formatter
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        // For x-axis labels (months), use integer formatting
                        return super.formatLabel(value, isValueX);
                    } else {
                        // For y-axis labels, use default formatting
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
        } else {
            Log.e("setGraph", "storage is null");
        }
    }


//
//    public void CategoryTotalSpend() {
//        CapitalAudit capitalAudit = CapitalAudit.getInstance();
//        PaymentStorage ps = capitalAudit.getStorage();
//        Vector<payment_class> paymentClassVector = ps.getPayments();
//
//        if (paymentClassVector != null) {
//            // Create a map to store the total spent for each category
//            Map<String, Double> categoryTotalMap = new HashMap<>();
//
//            // Calculate the total spent for each category
//            for (payment_class payment : paymentClassVector) {
//                String category = payment.getCategory();
//                double price = payment.getPrice();
//                Double currentTotal = categoryTotalMap.get(category);
//                if (currentTotal == null) {
//                    currentTotal = 0.0;
//                }
//                categoryTotalMap.put(category, currentTotal + price);
//            }
//
//            // Prepare data points for the graph
//            List<DataPoint> dataPoints = new ArrayList<>();
//            for (Map.Entry<String, Double> entry : categoryTotalMap.entrySet()) {
//                String category = entry.getKey();
//                double totalSpent = entry.getValue();
//                dataPoints.add(new DataPoint(category, totalSpent));
//            }
//
//            // Create the graph and add series
//            GraphView graph = (GraphView) findViewById(R.id.SpentEachMonthGraph);
//            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints.toArray(new DataPoint[0]));
//            graph.addSeries(series);
//
//            // Customize the graph appearance
//            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//                @Override
//                public String formatLabel(double value, boolean isValueX) {
//                    if (!isValueX) {
//                        // Format y-axis labels with currency symbol and two decimal places
//                        return super.formatLabel(value, isValueX) + " $";
//                    }
//                    return super.formatLabel(value, isValueX);
//                }
//            });
//            graph.getViewport().setXAxisBoundsManual(true);
//            graph.getViewport().setMinX(-0.5);
//            graph.getViewport().setMaxX(dataPoints.size() - 0.5);
//        } else {
//            Log.e("SpentEachMonthGraphSetup", "Payment data is null");
//        }
//    }


}