package edu.utep.cs.cs4330.mythreehours;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        /****************ADD & SUBTRACT TIME BUTTONS*******************/

        addButton.setOnClickListener(v -> {
            // String.valueOf(getItem(position))
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

        });
        subtractButton.setOnClickListener(v -> {
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
            if(progress >= 100){
                progressBar.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
            }
            else{
                progressBar.getProgressDrawable().setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN);
            }
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

}
