package edu.utep.cs.cs4330.mythreehours;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourse extends AppCompatActivity {
    //Database
    private courseDataBaseHelper myDb;

    //UI features
    private EditText editNameText;
    private EditText editDesiredHours;
    private Button confirmButton;

    //Course Object implementations
    private Course course;
    private String className;
    private int numDesiredHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        myDb = new courseDataBaseHelper(this);
        editNameText = findViewById(R.id.editNameText);
        editDesiredHours = findViewById(R.id.editDesiredHours);
        confirmButton = findViewById(R.id.confirmButton);

    }
    public void confirmClicked(View view) {

        className = editNameText.getText().toString();
        String hoursValue = editDesiredHours.getText().toString();
        numDesiredHours = Integer.parseInt(hoursValue);

        if (className.isEmpty() || hoursValue.isEmpty()) {//checks that URL is not blank
            toastMessage("Please enter values in both fields!");
        }
        else{
            course = new Course(className,numDesiredHours);
            addData(course.getName(),course.getDesiredWeekHours(),course.getCurrWeekHours(),course.getTotalHours());
        }
    }
    public void addData(String name, int numDesiredHours, double currWeekHours, double numTotalHours){
        boolean insertData = myDb.addData(name, numDesiredHours,currWeekHours,numTotalHours);
        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
