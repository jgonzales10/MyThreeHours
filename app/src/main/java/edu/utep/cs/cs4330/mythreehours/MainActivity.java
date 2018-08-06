package edu.utep.cs.cs4330.mythreehours;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView progresTextView;
    private ListView listView;
    private Button addClassButton;
    private Button refreshButton;
    private ArrayList arrayList;
    private courseDataBaseHelper myDb;
    private CustomAdapter customAdapter;
    private ProgressBar totalProgressBar;
    private double totalCompletedHours;
    private int totalDesiredHours;
    boolean blinkProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalCompletedHours = 0;
        totalDesiredHours = 0;
        myDb = new courseDataBaseHelper(this);
        arrayList = new ArrayList();

        progresTextView = findViewById(R.id.progressTextView);
        listView = findViewById(R.id.classListView);
        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setVisibility(View.GONE);
        addClassButton = findViewById(R.id.addClassButton);
        totalProgressBar = findViewById(R.id.progressBar);

        customAdapter = new CustomAdapter(this,arrayList,myDb);
        listView.setAdapter(customAdapter);
        populateListView();
        registerForContextMenu(listView);

        //totalProgressBar.setProgress(25);

        //updateProgressBar();
        new Thread(()->{
            while(true){
                this.runOnUiThread(this::updateProgressBar);
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e){}
            }
        }).start();

    }

    public void addClassClicked(View view) {
        Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
        startActivity(intent);
    }

    public void refreshClicked(View view) {
        customAdapter.notifyDataSetChanged();
    }
    /***********CONTEXT MENU Methods********************/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.add("EDIT");
        menu.add("REMOVE");
        // or use XML menu, e.g.,
        // getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    public void updateProgressBar(){
        Cursor data = myDb.getData();
        totalDesiredHours = 0;
        totalCompletedHours=0;
        while (data.moveToNext()) {
            totalDesiredHours += data.getInt(2);
            totalCompletedHours += data.getDouble(3);
            int TotalProgress = (int)((totalCompletedHours / totalDesiredHours)*100);
            if(TotalProgress >= 100){
                totalProgressBar.setScaleY(8);
                if(blinkProgress) {
                    progresTextView.setText("100%");
                    totalProgressBar.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
                    blinkProgress = false;
                }
                else{
                    totalProgressBar.getProgressDrawable().setColorFilter(Color.parseColor("#63D1FE"), PorterDuff.Mode.SRC_IN);
                    blinkProgress = true;
                }
                totalProgressBar.setProgress(TotalProgress);
            }
            else if(TotalProgress > 50){
                totalProgressBar.setScaleY(6);
                totalProgressBar.setProgress(TotalProgress);
                totalProgressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);

            }
            else{
                totalProgressBar.setScaleY(4);
                totalProgressBar.setProgress(TotalProgress);
                totalProgressBar.getProgressDrawable().setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN);

            }
            progresTextView.setText(TotalProgress + "%");

        }
        data.close();
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        super.onContextItemSelected(item);
        int index = info.position;
        String str = Integer.toString(index);
        if(item.getTitle() == "REMOVE"){
            Toast.makeText(this, "REMOVED ITEM: " + str, Toast.LENGTH_LONG).show();

            String[] parts = customAdapter.getItem(index).toString().split(":");
            String name = parts[0];
            Cursor data1 = myDb.getItemID(name);
            int itemID = -1;
            while (data1.moveToNext()) {
                itemID = data1.getInt(0);
            }
            if (itemID > -1) {
                myDb.deleteNameId(itemID,name);
            }
            data1.close();

            customAdapter.remove(customAdapter.getItem(info.position));
            ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
            listView.deferNotifyDataSetChanged();
        }
        else if(item.getTitle() == "EDIT"){
            String[] parts = customAdapter.getItem(index).toString().split(":");
            String name = parts[0];
            Cursor data1 = myDb.getItemID(name);

            int itemID = -1;
            int courseDesired = 0;
            double courseCurrWeek = 0;
            double courseTotal = 0;
            String courseId = null;
            String website = null;
            String description = null;

            while (data1.moveToNext()) {
                itemID = data1.getInt(0);
                courseDesired = data1.getInt(2);
                courseCurrWeek = data1.getDouble(3);
                courseTotal = data1.getDouble(4);
                courseId = data1.getString(5);
                website = data1.getString(6);
                description = data1.getString(7);
            }
            if (itemID > -1) {
                Intent editItemIntent = new Intent(MainActivity.this, editCourseActivity.class);
                editItemIntent.putExtra("id", itemID);
                editItemIntent.putExtra("name", name);
                editItemIntent.putExtra("courseDesired", courseDesired);
                editItemIntent.putExtra("courseCurrWeek", courseCurrWeek);
                editItemIntent.putExtra("courseTotal", courseTotal);
                editItemIntent.putExtra("courseId", courseId);
                editItemIntent.putExtra("website", website);
                editItemIntent.putExtra("description", description);
                startActivity(editItemIntent);
            }
        }
        else{
            return false;
        }
        return true;
    }

    /***********************SQLite Implementations*****************************************/

    private void populateListView() {
        Log.d("MainActivity", "populateListView: Displaying data in the ListView.");

        Cursor data = myDb.getData();

        while (data.moveToNext()) {
            arrayList.add(data.getString(1)+":"+data.getInt(2)+":"+data.getDouble(3)+":"+data.getDouble(4));
        }
        data.close();

         listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String[] parts = adapterView.getItemAtPosition(i).toString().split(":");
            String name = parts[0];
            Cursor data1 = myDb.getItemID(name);

            int itemID = -1;
            int courseDesired = 0;
            double courseCurrWeek = 0;
            double courseTotal = 0;
            String courseId = null;
            String website = null;
            String description = null;

            while (data1.moveToNext()) {
                itemID = data1.getInt(0);
                courseDesired = data1.getInt(2);
                courseCurrWeek = data1.getDouble(3);
                courseTotal = data1.getDouble(4);
                courseId = data1.getString(5);
                website = data1.getString(6);
                description = data1.getString(7);
            }
            if (itemID > -1) {
                Intent editItemIntent = new Intent(MainActivity.this, GraphActivity.class);
                editItemIntent.putExtra("id", itemID);
                editItemIntent.putExtra("name", name);
                editItemIntent.putExtra("courseDesired",courseDesired);
                editItemIntent.putExtra("courseCurrWeek",courseCurrWeek);
                editItemIntent.putExtra("courseTotal",courseTotal);
                editItemIntent.putExtra("courseId",courseId);
                editItemIntent.putExtra("website",website);
                editItemIntent.putExtra("description",description);
                startActivity(editItemIntent);
            } else {
                toastMessage("No ID associated with that name");
            }

        });
    }
    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
