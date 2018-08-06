package edu.utep.cs.cs4330.mythreehours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class editCourseActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView currentHoursTextView;
    private EditText editClassName;
    private EditText editCourseId;
    private EditText editWebsite;
    private EditText editClassDescription;
    private EditText editDesiredStudyHours;
    private Button confirmEditButton;
    private courseDataBaseHelper myDB;

    private int selectedID;
    private String selectedName;
    private int selectedDesired;
    private double selectedCurrent;
    private double selectedTotal;
    private String selectedcourseId;
    private String selectedWebsite;
    private String selectedDescription;
    private Course editItem;
    private String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        Course course = new Course();
        myDB = new courseDataBaseHelper(this);

        //Buttons
        confirmEditButton = findViewById(R.id.confirmEditbutton);

        //Edit Texts
        editClassName = findViewById(R.id.editClassName);
        editCourseId = findViewById(R.id.editCourseId);
        editWebsite = findViewById(R.id.editWebsite);
        editClassDescription = findViewById(R.id.editDescription);
        editDesiredStudyHours = findViewById(R.id.editDesiredHours);
        currentHoursTextView = findViewById(R.id.currentHoursTextView);

        //Textviews
        titleTextView = findViewById(R.id.titleTextView);

        //For intent passing
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");
        oldName = selectedName;
        selectedDesired = receivedIntent.getIntExtra("courseDesired", 0);
        selectedCurrent = receivedIntent.getDoubleExtra("courseCurrWeek", 0);
        selectedTotal = receivedIntent.getDoubleExtra("courseTotal",0);
        selectedcourseId = receivedIntent.getStringExtra("courseId");
        selectedWebsite = receivedIntent.getStringExtra("website");
        selectedDescription = receivedIntent.getStringExtra("description");


        editItem = new Course(selectedName,selectedDesired,selectedCurrent,selectedTotal,
                selectedcourseId,selectedWebsite,selectedDescription);

        course.setName(selectedName);
        course.setDesiredWeekHours(selectedDesired);
        course.setCurrWeekHours(selectedCurrent);
        course.setTotalHours(selectedTotal);
        course.setCourseID(selectedcourseId);
        course.setCourseWebsite(selectedWebsite);
        course.setDescription(selectedDescription);

        editClassName.setText(selectedName);
        editDesiredStudyHours.setText(Integer.toString(selectedDesired));
        titleTextView.setText("Edit " + selectedName);
        currentHoursTextView.setText("Number of hours you've studied so far: " + selectedCurrent);
        editCourseId.setText(selectedcourseId);
        editWebsite.setText(selectedWebsite);
        editClassDescription.setText(selectedDescription);
    }

    public void confirmEditClicked(View view) {
        String newName = editClassName.getText().toString();
        String newDesiredString = editDesiredStudyHours.getText().toString();
        String newCourseId = editCourseId.getText().toString();
        String newWebsite = editWebsite.getText().toString();
        String newDescription = editClassDescription.getText().toString();
        int newDesiredHours = 0;

        if(!newDesiredString.isEmpty()){
            newDesiredHours = Integer.parseInt(newDesiredString);
        }
        if (newName.isEmpty() || editDesiredStudyHours.getText().toString().isEmpty()) {//checks that URL is not blank
            if(newName.isEmpty()){
                Toast.makeText(this, "Course Name cannot be blank, please add a NAME", Toast.LENGTH_SHORT).show();
                editClassName.setText(this.selectedName);
            }
            if(editDesiredStudyHours.getText().toString().isEmpty()){
                Toast.makeText(this, "Desired hours cannot be blank, please add a value", Toast.LENGTH_SHORT).show();
                editDesiredStudyHours.setText(Integer.toString(this.selectedDesired));
            }
        }
        else{
            editItem.setName(newName);
            editItem.setCourseID(newCourseId);
            editItem.setDesiredWeekHours(newDesiredHours);
            editItem.setCourseWebsite(newWebsite);
            editItem.setDescription(newDescription);

            updateData(editItem.getName(), editItem.getDesiredWeekHours(),editItem.getCurrWeekHours(),editItem.getTotalHours(),editItem.getCourseID(),editItem.getCourseWebsite(),editItem.getDescription());
            titleTextView.setText("Edit " + editItem.getName());
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            //toastLongMessage("1v1 Me in Rust, 360 No-Scopes only");
        }
    }
    public void updateData(String name, int desiredWeekHours, double currWeekHours, double totalHours,
                                          String courseId, String website, String description){
        boolean insertData = myDB.updateData(oldName,name,desiredWeekHours,currWeekHours,totalHours,courseId,website,description);
        if (insertData) {
            toastMessage("Data Successfully updated!");
        } else {
            toastMessage("Something went wrong");
        }
    }
    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void toastLongMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
