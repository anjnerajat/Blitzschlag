package com.example.dell.blitzschlag.Wishlist;

import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.dell.blitzschlag.Classes.Wish;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String EVENTS_TABLE_NAME = "events";
    public static final String EVENTS_COLUMN_ID = "id";
    public static final String EVENTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_VENUE = "venue";
    public static final String CONTACTS_COLUMN_START = "start";
    public static final String CONTACTS_COLUMN_DATE = "date";
    public static final String CONTACTS_COLUMN_CATEGORY = "category";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table events " +
                        "(id integer primary key, name text,venue text,start text, date text,category text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }

    public boolean insertEvent  (String name,String venue,String start,String date,String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("venue", venue);
        contentValues.put("start", start);
        contentValues.put("date", date);
        contentValues.put("category",category);
        db.insert("events", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from events where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, EVENTS_TABLE_NAME);
        return numRows;
    }

    public Integer deleteEvent (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("events",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public List<Wish> getAllEvents()
    {
        List<Wish> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from events", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Wish event = new Wish(res.getString(res.getColumnIndex(EVENTS_COLUMN_NAME)),res.getString(res.getColumnIndex(CONTACTS_COLUMN_VENUE)),res.getString(res.getColumnIndex(CONTACTS_COLUMN_CATEGORY)),res.getString(res.getColumnIndex(CONTACTS_COLUMN_DATE)),res.getString(res.getColumnIndex(CONTACTS_COLUMN_START)));
            array_list.add(event);
            res.moveToNext();
        }
        return array_list;
    }

    public boolean isEventRemembered(String fieldValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "select * from " + EVENTS_TABLE_NAME+ " where " + EVENTS_COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(Query, new String[]{fieldValue});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
