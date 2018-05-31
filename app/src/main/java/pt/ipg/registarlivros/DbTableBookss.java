package pt.ipg.registarlivros;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */

public class DbTableBookss implements BaseColumns {
    public static final String _ID = "id";
    public static final String TABLE_NAME = "books";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DISCRIPTION = "discription";
    public static final String FIELD_IDWRITER = "idwriter";
    private final SQLiteDatabase db;

    public DbTableBookss (SQLiteDatabase db){
        this.db=db;

    }

    public void create(){

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                FIELD_TITLE  + " TEXT NOT NULL," + FIELD_DISCRIPTION + " TEXT NOT NULL," + FIELD_IDWRITER +"INTEGER,"+"FOREIGN KEY ("+FIELD_IDWRITER+") REFERENCES " +

                DbTableWriter.TABLE_NAME +"("+DbTableWriter._ID+")"+")"

                );
    }

}
