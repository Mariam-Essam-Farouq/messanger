package messenger.iti.helpers;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="mydb.db";
    private static final int DATABASE_VERSION=2;
    private static final String TABLE_NAME="MYTABLE";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_NUMBER= "NUMBER";

    private static final String TABLE_NAME2="MYTABLE2";
    private static final String KEY_NAME2 = "NAME2";
    private static final String KEY_NUMBER2= "NUMBER2";
    private static final String KEY_MESSAGE= "MESSAGE";

    private static final String TABLE_NAME3="MYTABLE3";
    private static final String KEY_NAME3 = "NAME3";
    private static final String KEY_NUMBER3= "NUMBER3";
    private static final String KEY_MESSAGE2= "MESSAGE2";
    private static final String KEY_YEAR= "YEAR";
    private static final String KEY_MONTH= "MONTH";
    private static final String KEY_DAY= "DAY";
    private static final String KEY_HOUR= "HOUR";
    private static final String KEY_MINUTE= "MINUTE";


    private static final String TABLE_NAME5="MYTABLE5";
    private static final String KEY_NAME4 = "NAME4";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_NUMBER4= "NUMBER4";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MYTABLE (Name VARCHAR(50) , Number VARCHAR(15));");
        db.execSQL("CREATE TABLE MYTABLE2 (Name2 VARCHAR(50) , Number2 VARCHAR(15), Message VARCHAR(250));");
        db.execSQL("CREATE TABLE MYTABLE3 (Name3 VARCHAR(50) , Number3 VARCHAR(15), Message2 VARCHAR(250) , Year INT(4) , Month INT(2) , Day INT(2) , Hour INT(2) , Minute INT(2));");
        db.execSQL("CREATE TABLE MYTABLE5 ( Name4 VARCHAR(600) ,Title VARCHAR(25) , Number4 VARCHAR(600));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        onCreate(db);
    }

    public boolean insertData(String Name,String Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,Name);
        contentValues.put(KEY_NUMBER,Number);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData3(String Name3,String Number3,String Message2 , int year , int month , int day , int hour , int minute) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME3,Name3);
        contentValues.put(KEY_NUMBER3,Number3);
        contentValues.put(KEY_MESSAGE2,Message2);
        contentValues.put(KEY_YEAR,year);
        contentValues.put(KEY_MONTH,month);
        contentValues.put(KEY_DAY,day);
        contentValues.put(KEY_HOUR,hour);
        contentValues.put(KEY_MINUTE,minute);
        long result = db.insert(TABLE_NAME3,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData2(String Name2,String Number2,String Message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME2,Name2);
        contentValues.put(KEY_NUMBER2,Number2);
        contentValues.put(KEY_MESSAGE,Message);
        long result = db.insert(TABLE_NAME2,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData5( String Name,String Title,String Number) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE,Title);
        contentValues.put(KEY_NAME4,Name);
        contentValues.put(KEY_NUMBER4,Number);
        long result = db.insert(TABLE_NAME5,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NUMBER + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteMessage(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME2, KEY_MESSAGE + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
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

    public Cursor getAllData3() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME3,null);
        return res;
    }

    public Cursor getAllData5() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME5 ,null);
        return res;
    }
}
