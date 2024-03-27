//package com.example.capitalaudit.button_classes;
//
//
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import com.example.capitalaudit.CapitalAudit;
//import com.example.capitalaudit.R;
//import com.example.capitalaudit.Utility.Util;
//import com.example.capitalaudit.models.PaymentStorage;
//import com.example.capitalaudit.models.payment_class;
//import com.jjoe64.graphview.DefaultLabelFormatter;
//import com.jjoe64.graphview.GraphView;
//import com.jjoe64.graphview.GridLabelRenderer;
//import com.jjoe64.graphview.series.DataPoint;
//import com.jjoe64.graphview.series.LineGraphSeries;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
///**
// * This class is used to create graphs.
// */
//public class graph
//{
//
//    public graph()
//    {
//
//    }
//
//    public void CategoryBarGraphSetup()
//    {
//        CapitalAudit capitalAudit = CapitalAudit.getInstance();
//        PaymentStorage ps = capitalAudit.getStorage();
//        Vector<payment_class> paymentClassVector = ps.getPayments();
//
//        if (paymentClassVector != null)
//        {
//            GraphView graph = (GraphView) findViewById(R.id.CategoryBarGraph);
//            GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
//            gridLabelRenderer.setHorizontalAxisTitle("Month");
//            gridLabelRenderer.setVerticalAxisTitle("Spend");
//            gridLabelRenderer.setLabelsSpace(15);
//            gridLabelRenderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);
//            List<DataPoint> dataPoints = new ArrayList<>();
//            List<payment_class> thisMonthsPayments = Util.filterDataByMonths(1);
//
//            for (payment_class payment : thisMonthsPayments)
//            {
//                double price = payment.getPrice();
//                String date = payment.getDate();
//                int dayOfMonth = Integer.parseInt(date.substring(8)); // Get the day of the month
//
//                dataPoints.add(new DataPoint(dayOfMonth, price));
//            }
//
//            DataPoint[] dataPointArray = dataPoints.toArray(new DataPoint[0]);
//
//            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointArray);
//            series.setColor(getResources().getColor(R.color.primary_color));
//            graph.addSeries(series);
//            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//                @Override
//                public String formatLabel(double value, boolean isValueX) {
//                    if (isValueX)
//                    {
//                        // For x-axis labels (days), use integer formatting
//                        return super.formatLabel(value, isValueX);
//                    } else
//                    {
//                        // For y-axis labels, use default formatting
//                        return super.formatLabel(value, isValueX);
//                    }
//                }
//            });
//        }
//        else
//        {
//            Log.e("setGraph", "storage is null");
//        }
//    }
//
//
//    public void GraphButton(Button button, int months)
//    {
//
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Util.filterDataByMonths(months);
//
//            }
//        });
//
//    }
//}
//
////Graph activity, sets up graphs with basic data for 2 months.
////If a button is clicked the data needs to get filtered, and then the graph needs to be changed
////I need a way to pass the data from the onclick listener to the graph's functions.