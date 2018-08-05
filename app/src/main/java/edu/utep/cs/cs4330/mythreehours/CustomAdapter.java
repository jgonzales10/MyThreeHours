package edu.utep.cs.cs4330.mythreehours;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    private courseDataBaseHelper myDb;
    private TextView courseName;
    private Button addButton;
    private Button subtractButton;
    private ProgressBar progressBar;

    private Context context;
    private ArrayList courseList;
    private String cName;
    private int desiredHours;
    private double currHours;
    private double totalHours;
    private int progress;
    private View listItem;
    private View view;
    private int position;
    private String[] tempDescription;

    public CustomAdapter(Context context, ArrayList list, courseDataBaseHelper db) {
        super(context, 0 , list);
        this.context = context;
        this.courseList = list;
        this.myDb = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(this.context).inflate(R.layout.custom_layout,parent,false);
        }
        String courseObject = (String) courseList.get(position);
        //toastMessage(courseObject);
        String[] courseDescription = courseObject.split(":");
        cName = courseDescription[0];
        desiredHours = Integer.parseInt(courseDescription[1]);
        currHours = Double.parseDouble(courseDescription[2]);
        totalHours = Double.parseDouble(courseDescription[3]);

        //Course currentCourse = courseList.get(position);
        courseName = listItem.findViewById(R.id.courseNameTextView);
        addButton = listItem.findViewById(R.id.addButton);
        subtractButton = listItem.findViewById(R.id.subtractButton);
        progressBar = listItem.findViewById(R.id.individualProgressBar);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // String.valueOf(getItem(position))
                toastMessage("Adding 15 minutes of study time!");
                String tempObject = (String) courseList.get(position);
                tempDescription = tempObject.split(":");
                String tempName = tempDescription[0];
                int tempDesiredHours = Integer.parseInt(tempDescription[1]);
                double tempCurrHours = Double.parseDouble(tempDescription[2]);
                double tempTotalHours = Double.parseDouble(tempDescription[3]);
                tempCurrHours += .25;
                tempTotalHours += .25;

                String newObject = tempName +":"+ tempDesiredHours +":"+ tempCurrHours +":"+ tempTotalHours;
                courseList.set(position, newObject);
                myDb.updateData(tempName, tempDesiredHours, tempCurrHours, tempTotalHours);
                notifyDataSetChanged();
            }//notifyDataSetChanged();
        });
        subtractButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toastMessage("Subtracting 15 minutes of study time!");
                String tempObject = (String) courseList.get(position);
                tempDescription = tempObject.split(":");
                String tempName = tempDescription[0];
                int tempDesiredHours = Integer.parseInt(tempDescription[1]);
                double tempCurrHours = Double.parseDouble(tempDescription[2]);
                double tempTotalHours = Double.parseDouble(tempDescription[3]);
                tempCurrHours -= .25;
                tempTotalHours -= .25;

                String newObject = tempName +":"+ tempDesiredHours +":"+ tempCurrHours +":"+ tempTotalHours;
                courseList.set(position, newObject);
                myDb.updateData(tempName, tempDesiredHours, tempCurrHours, tempTotalHours);
                notifyDataSetChanged();
            }
        });

        updateList();

        return listItem;
    }
    public void updateList(){
        if(listItem != null){
            //String currCourse = currentCourse.getName();
            courseName.setText(cName);

            progress = (int)((currHours / desiredHours)*100);
            //progressBar.setProgress(progress);
            progressBar.setProgress(progress);
        }
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    public void toastMessage(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }


    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_layout,null);

        TextView courseNameTextView = convertView.findViewById(R.id.courseNameTextView);
        Button addButton = convertView.findViewById(R.id.addButton);
        Button subtractButton = convertView.findViewById(R.id.subtractButton);
        ProgressBar progressBar = convertView.findViewById(R.id.individualProgressBar);
        courseNameTextView.setText("TEST");
        progressBar.setProgress(50);

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    */
}
