package edu.utep.cs.cs4330.mythreehours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button addClassButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addClassClicked(View view) {
        Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
        startActivity(intent);
    }
}
