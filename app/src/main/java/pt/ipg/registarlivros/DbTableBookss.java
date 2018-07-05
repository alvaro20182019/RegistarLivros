package pt.ipg.registarlivros;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */

public class DbTableBookss implements BaseColumns {

    public static final String TABLE_NAME = "books";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DISCRIPTION = "discription";
  //  public static final String FIELD_ID_WRITER = "idwriter";
    public static final String FIELD_STATE = "State";
    public static final String FIELD_ID_CATEGORY = "idCategory";
    public static final String [] ALL_COLUMNS = new String[] { _ID, FIELD_TITLE, FIELD_STATE, FIELD_TITLE,FIELD_DISCRIPTION,FIELD_ID_CATEGORY };
    private final SQLiteDatabase db;

    public DbTableBookss(SQLiteDatabase db){
        this.db=db;

    }

    public void create(){

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_STATE + " TEXT NOT NULL," +

                FIELD_TITLE  + " TEXT NOT NULL," + FIELD_DISCRIPTION + " TEXT NOT NULL," + FIELD_ID_CATEGORY+ " INTEGER , FOREIGN KEY ("+FIELD_ID_CATEGORY+") REFERENCES " +
                 DbTableCategory.TABLE_NAME +"("+ DbTableCategory._ID+ "))"

                );
    }
public static Book getCurrentBookFromCursor(Cursor cursor){
        final int posId = cursor.getColumnIndex(_ID);
        final int posState=cursor.getColumnIndex(FIELD_STATE);
        final int posTitle=cursor.getColumnIndex(FIELD_TITLE);
        final int posDiscription=cursor.getColumnIndex(FIELD_DISCRIPTION);
        //final int posIdwriter=cursor.getColumnIndex(FIELD_ID_WRITER);
        final int posIdCategory=cursor.getColumnIndex(FIELD_ID_CATEGORY);
             Book book = new Book();
             book.setId(cursor.getInt(posId));
             book.setState(cursor.getString(posState));
             book.setTitle(cursor.getString(posTitle));
             book.setDiscription(cursor.getString(posDiscription));
               book.setIdcategory(cursor.getInt(posIdCategory));
      //       book.setIdwriter(cursor.getInt(posIdwriter));

             return book;
}
    public static ContentValues getContentValues(Book book){
        ContentValues values= new ContentValues();

        values.put(FIELD_STATE,book.getState());
        values.put(FIELD_TITLE,book.getTitle());
        values.put(FIELD_DISCRIPTION,book.getDiscription());
        values.put(FIELD_ID_CATEGORY,book.getIdcategory());
    //    values.put(FIELD_ID_WRITER,book.getIdwriter());

        return  values;

    }

    public long insert(ContentValues values){
        return db.insert(TABLE_NAME,null,values);
    }

    public long update(ContentValues values, String WhereClause, String[] WhereArgs) {
        return db.update(TABLE_NAME, values, WhereClause, WhereArgs);
    }

    public long delete(String WhereClause, String[] whereArgs){
        return db.delete(TABLE_NAME,WhereClause,whereArgs);
    }
    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
        // return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
