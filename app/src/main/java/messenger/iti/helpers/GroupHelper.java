package messenger.iti.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroupHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="my.db";
    private static final int DATABASE_VERSION=2;
    private static final String TABLE_NAME="MYGROUBTABLE";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_NUMBER= "NUMBER";

    private static final String TABLE_NAME2="MYGROUBTABLE2";
    private static final String KEY_NAME2 = "NAME2";
    private static final String KEY_NUMBER2= "NUMBER2";

    public GroupHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE MYGROUBTABLE ( title VARCHAR(50) , Name VARCHAR(1000) , Number VARCHAR(1000));");
        sqLiteDatabase.execSQL("CREATE TABLE MYGROUBTABLE2 ( Name2 VARCHAR(1000) , Number2 VARCHAR(1000));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }

    public boolean insertData( String Title ,String Name,String Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,Title);
        contentValues.put(KEY_NAME,Name);
        contentValues.put(KEY_NUMBER,Number);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData2(String Name2,String Number2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME2,Name2);
        contentValues.put(KEY_NUMBER2,Number2);

        long result = db.insert(TABLE_NAME2,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Cursor getAllData2() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME2,null);
        return res;
    }

    public void insertInRow(String id , String name , String number) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+" SET name = "+"'"+name+"' "+  "WHERE title = "+"'"+id+"'");
        db.execSQL("UPDATE "+TABLE_NAME+" SET number = "+"'"+number+"' "+  "WHERE title = "+"'"+id+"'");
    }

    public void deleteGroup(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_TITLE + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

}
