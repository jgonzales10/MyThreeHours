package edu.utep.cs.cs4330.mythreehours;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {
    LineChart lineChart;
    TextView graphTitle;
    int selectedID;
    String selectedName;
    int selectedDesired;
    double selectedCurrent;
    double selectedTotal;
    String selectedcourseId;
    String selectedWebsite;
    String selectedDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        lineChart = findViewById(R.id.lineChart);
        graphTitle = findViewById(R.id.graphTitleText);
        //For intent passing
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");
        selectedDesired = receivedIntent.getIntExtra("courseDesired", 0);
        selectedCurrent = receivedIntent.getDoubleExtra("courseCurrWeek", 0);
        selectedTotal = receivedIntent.getDoubleExtra("courseTotal",0);
        selectedcourseId = receivedIntent.getStringExtra("courseId");
        selectedWebsite = receivedIntent.getStringExtra("website");
        selectedDescription = receivedIntent.getStringExtra("description");

        graphTitle.setText(selectedName + " Graph");
        ArrayList<String> xAXES = new ArrayList<>();
        List<Entry> entries = new ArrayList<Entry>();
        //ArrayList<Entry> yAXESstudyTime = new ArrayList<>();
        //ArrayList<Entry> yAXESgrades= new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            float currentFloat = (float)(selectedCurrent + ((Math.random()*((20-10)+1))+10));
            float x = (float)i;
            // turn your data into Entry objects
            entries.add(new Entry(x,currentFloat));
        }

        String[] xaxes = new String[xAXES.size()];
        for(int i=0; i<xAXES.size();i++){
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(entries, "Weekly Hours Spent Studying");
        //LineDataSet lineDataSet2 = new LineDataSet(yAXESstudyTime,"Weekly Hours Spent Studying");
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setColor(Color.BLUE);

        /*
        LineDataSet lineDataSet2 = new LineDataSet(yAXESgrades,"sin");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);
        */
        lineDataSets.add(lineDataSet1);
        //lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets));

        lineChart.setVisibleXRangeMaximum(65f);
    }
}
