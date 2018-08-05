package edu.utep.cs.cs4330.mythreehours;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class courseDataBaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "course.db";
    private static final String TABLE_NAME = "course_table";

    private static final String COL_1 = "id";
    private static final String COL_2 = "name";
    private static final String COL_3 = "desiredWeekHours";
    private static final String COL_4 = "currWeekHours";
    private static final String COL_5 = "totalHours";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DONE = "done";

    public courseDataBaseHelper(Context context){
        super(context, TABLE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT unique, "
                + COL_3 + " INTEGER, "
                + COL_4 + " REAL, "
                + COL_5 + " REAL, "
                + KEY_DESCRIPTION + " TEXT, "
                + KEY_DONE + " INTEGER" + ")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, int desiredWeekHours, double currWeekHours, double totalHours) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, desiredWeekHours);
        contentValues.put(COL_4, currWeekHours);
        contentValues.put(COL_5, totalHours);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean addData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);

        long result = db.insertOrThrow(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void deleteUsingIdName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_1 +
                " = '" + id + "'" + " AND " + COL_2 + " = '" + name + "'";
        db.execSQL(query);
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public boolean updateData(String name, int desiredWeekHours, double currWeekHours, double totalHours){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, desiredWeekHours);
        contentValues.put(COL_4, currWeekHours);
        contentValues.put(COL_5, totalHours);
        db.update(TABLE_NAME,contentValues, "name = ?", new String[]{name});
        return true;
    }
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public void deleteNameId(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_1 +
                " = '" + id + "'" + " AND " + COL_2 + " = '" + name + "'";
        db.execSQL(query);
    }
}
