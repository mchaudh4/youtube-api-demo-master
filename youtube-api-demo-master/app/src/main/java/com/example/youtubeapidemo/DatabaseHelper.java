package com.example.youtubeapidemo;

/**
 * Created by Manu Chaudhary on 2/3/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student3.db";

    public static final String TABLE_NAME = "student1_table";
    //public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";
    public static final String COL_5 = "IMAGE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"(NAME TEXT,SURNAME TEXT,MARKS TEXT,IMAGE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String surname,String marks,String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);   // Name here denotes rating
        contentValues.put(COL_3,surname);   // surname here denotes  video_title
        contentValues.put(COL_4,marks);        // marks here denotes    video_id
        contentValues.put(COL_5,image);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor res = db.rawQuery("select * from "+TABLE_NAME ,null);
       Cursor res = db.rawQuery("select * from "+TABLE_NAME ,null);
      // Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where name like '5%';",null);

        return res;
    }


}