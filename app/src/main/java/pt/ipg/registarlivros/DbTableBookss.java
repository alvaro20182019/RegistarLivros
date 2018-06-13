package pt.ipg.registarlivros;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */

public class DbTableBookss implements BaseColumns {

    public static final String TABLE_NAME = "books";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DISCRIPTION = "discription";
    public static final String FIELD_IDWRITER = "idwriter";
    public static final String FIELD_STATE = "State";
    public static final String FIELD_IDCATEGORY = "idcategory";

    private final SQLiteDatabase db;

    public DbTableBookss(SQLiteDatabase db){
        this.db=db;

    }

    public void create(){

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_STATE + " TEXT NOT NULL," +

                FIELD_TITLE  + " TEXT NOT NULL," + FIELD_DISCRIPTION + " TEXT NOT NULL," + FIELD_IDCATEGORY+"INTEGER ," +  FIELD_IDWRITER +"INTEGER,FOREIGN KEY ("+FIELD_IDWRITER+") REFERENCES " +

                DbTableWriter.TABLE_NAME +"("+ DbTableWriter._ID+ ") , FOREIGN KEY ("+FIELD_IDCATEGORY+") REFERENCES " +

                DbTableCategory.TABLE_NAME+"("+ DbTableCategory._ID+ "))"

                );
    }

    public static ContentValues getContentValues(Book book){
        ContentValues values= new ContentValues();
        values.put(_ID,book.getId());
        values.put(FIELD_STATE,book.getState());
        values.put(FIELD_TITLE,book.getTitle());
        values.put(FIELD_DISCRIPTION,book.getDiscription());
        values.put(FIELD_IDWRITER,book.getIdwriter());
        return  values;

    }

    public long insert(ContentValues values){
        return db.insert(TABLE_NAME,null,values);
    }

    public long update(ContentValues values, String Whereclause, String[] WhereArgs) {
        return db.update(TABLE_NAME, values, Whereclause, WhereArgs);
    }

    public long delete(String Whereclause, String[] whereArgs){
        return db.delete(TABLE_NAME,Whereclause,whereArgs);
    }

}
