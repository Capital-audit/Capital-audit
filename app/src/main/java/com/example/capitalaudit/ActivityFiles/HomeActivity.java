package com.example.capitalaudit.ActivityFiles;


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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.capitalaudit.CapitalAudit;
import com.example.capitalaudit.API.FetchData;
import com.example.capitalaudit.Listeners.DataFetchListener;
import com.example.capitalaudit.R;
import com.example.capitalaudit.API.api_class;
import com.example.capitalaudit.Utility.Util;
import com.example.capitalaudit.models.PaymentStorage;
import com.example.capitalaudit.models.payment_class;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
    * HomeActivity class is responsible for displaying the home screen of the application.
    * It extends AppCompatActivity class.
    * It implements DataFetchListener interface.
    * It has the following methods:
    * 1. onCreate method
    * 2. onDataFetched method
    * 3. menuBarSetup method
    * 4. startActivity method
    * 5. FetchData method
    * 6. setUpAudit method
    * 7. welcomeNameColor method
    * 8. setHomeGraph method
    * 9. setMonthlySpend method
    * 10. populateTable method
    * 11. setTableDate method
    * 12. setTablePrice method
    * 13. setTableCategory method
    * 14. setTableIsCleared method
 */
public class HomeActivity extends AppCompatActivity implements DataFetchListener
{


    CapitalAudit capitalAudit;
    api_class api;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpAudit();
        FetchData();
        menuBarSetup();
        TextView displayTextView = findViewById(R.id.displayTextView);
        displayTextView.setText(welcomeNameColor());
    }



    /**
        * onDataFetched()
        * Parameters:
        * Return Type: void
        * Description: This method is called when the data is fetched from the server. It listens for the data fetched event.
        * It calls the setHomeGraph(), populateTable(), and setMonthlySpend() methods.
     */
    @Override
    public void onDataFetched()
    {
        setHomeGraph();
        populateLatestTransactions();
        setMonthlySpend();
    }



    /**
     * menuBarSetup()
     * @return
     */
    private boolean menuBarSetup()
    {
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.Home)
            {
                return true;
            }
            else if (itemId == R.id.Graph)
            {
                startActivity(new Intent(HomeActivity.this, GraphActivity.class));
                finish(); // Finish the current activity to prevent going back to it
                return true;
            }
            else if (itemId == R.id.New)
            {
                // Handle New click
                startActivity(new Intent(HomeActivity.this, AddPaymentActivity.class));
                finish();
                return true;
            }
            else if (itemId == R.id.View)
            {
                // Handle View click
                startActivity(new Intent(HomeActivity.this, DatasetActivity.class));
                finish();
                return true;
            }
            else if (itemId == R.id.Settings)
            {
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
    public void startActivity(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }



    /**
     * FetchData()
     * @return void
     * @return void
     * Description: This method is responsible for fetching the data from the server.
     * It calls the FetchDataFromServer() method from the FetchData class. Passing the api and HomeActivity.this as parameters.
     **/
    private void FetchData()
    {
        FetchData fetchData = new FetchData(api, HomeActivity.this);
        fetchData.setListener(this);
        fetchData.FetchDataFromServer();
    }



    /**
     * setUpAudit()
     * @return void
     * Description: This method is responsible for setting up the CapitalAudit instance.
     * It calls the CapitalAudit.getInstance() method and assigns it to the capitalAudit variable.
     */
    private void setUpAudit()
    {
        capitalAudit = CapitalAudit.getInstance();
        api = capitalAudit.getApi();

    }



    /**
     * welcomeNameColor()
     * @params void
     * @return SpannableString
     * Description: This method is responsible for setting the color of the welcome message along with the username.
     *
     */
    private SpannableString welcomeNameColor()
    {
        String username = CapitalAudit.getUsername();
        SpannableString spannableString = new SpannableString("Welcome " + username);

        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4BB73A")), 7, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }



    /**
     * setHomeGraph()
     * @params void
     * @return void
     * Description: This method is responsible for setting up the graph on the home screen.
     * It gets the data from the storage and filters it by the last 4 months.
     */
    private void setHomeGraph()
    {
        PaymentStorage ps = capitalAudit.getStorage();
        Vector<payment_class> paymentClassVector = ps.getPayments();

        if (paymentClassVector != null)
        {
            GraphView graph = (GraphView) findViewById(R.id.home_top_graph);
            GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
            gridLabelRenderer.setHorizontalAxisTitle("Month");
            gridLabelRenderer.setVerticalAxisTitle("Spend");
            gridLabelRenderer.setLabelsSpace(15);
            gridLabelRenderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);
            List<DataPoint> dataPoints = new ArrayList<>();
            List<payment_class> thisMonthsPayments = Util.filterDataByMonths(1);

            for (payment_class payment : thisMonthsPayments)
            {
                double price = payment.getPrice();
                String date = payment.getDate();
                int dayOfMonth = Integer.parseInt(date.substring(8)); // Get the day of the month

                dataPoints.add(new DataPoint(dayOfMonth, price));
            }

            DataPoint[] dataPointArray = dataPoints.toArray(new DataPoint[0]);

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArray);
            series.setColor(getResources().getColor(R.color.primary_color));
            graph.addSeries(series);

            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX)
                    {
                        // For x-axis labels (days), use integer formatting
                        return super.formatLabel(value, isValueX);
                    } else
                    {
                        // For y-axis labels, use default formatting
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
        }
        else
        {
            Log.e("setGraph", "storage is null");
        }
    }



    /**
     * setMonthlySpend()
     * @params void
     * @return void
     * Description: This method is responsible for setting the monthly spend on the home screen.
     * It gets the monthly spend from the storage and sets the text view.
     */
    private void setMonthlySpend()
    {
        TextView montlySpendTextView = findViewById(R.id.monthly_spend);
        double monthlySpend = 0;
        List<payment_class> monthstorage = Util.filterDataByMonths(1);
        for (payment_class payment : monthstorage)
        {
            double spend = payment.getPrice();
            monthlySpend += spend;
        }

        SpannableString spannableString = new SpannableString("Spent this month $" + monthlySpend);

        int startBoldIndex = 16;
        int endBoldIndex = spannableString.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4BB73A")), startBoldIndex, endBoldIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startBoldIndex, endBoldIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        montlySpendTextView.setText(spannableString);
    }



    /**
     * populateTable()
     * @params void
     * @return
     * Description: This method is responsible for populating the table on the home screen.
     * It gets the data from the storage and populates the table with the most recent 6 data points.
     */
    public void populateLatestTransactions()
    {
        List<payment_class> data = Util.filterDataByMonths(1);
        int size = data.size();
        int numTransactionsToDisplay = Math.min(size, 4);

        for (int i = 0; i < numTransactionsToDisplay; i++) {
            TextView currentTextView = getTextViewForIndex(i);
            payment_class payment = data.get(size - numTransactionsToDisplay + i);
            currentTextView.setText(payment.toString());
        }
    }

    private TextView getTextViewForIndex(int index) {
        // Determine which TextView to use based on the index
        switch (index) {
            case 0:
                return findViewById(R.id.latest_transactions_cell_1);
            case 1:
                return findViewById(R.id.latest_transactions_cell_2);
            case 2:
                return findViewById(R.id.latest_transactions_cell_3);
            default:
                throw new IllegalArgumentException("Invalid index: " + index);
        }
    }
}