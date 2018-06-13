package pt.ipg.registarlivros;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */

public class DbTableWriter implements BaseColumns {


    public static final String _ID = "id";
    public static final String WRITER_NAME = "nome";
    public static final String TABLE_NAME = "writer";
    private final SQLiteDatabase db;
    public static final String [] ALL_COLUMNS = new String[] { _ID, WRITER_NAME };
    public DbTableWriter(SQLiteDatabase db){
        this.db=db;

    }
    public void create(){   // objetivo é criar uma tabela na base de dabos
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + WRITER_NAME + " TEXT NOT NULL)"

        );  //comando sql para criar a tabela
    }

    public static ContentValues getContentValues(Writer writer){
        ContentValues values= new ContentValues();
        values.put(_ID,writer.getId());
        values.put(WRITER_NAME,writer.getName());

        return  values;

    }
    public static Writer getCurrentCategoryFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posName = cursor.getColumnIndex(WRITER_NAME);

        Writer writer = new Writer();

        writer.setId(cursor.getInt(posId));
        writer.setName(cursor.getString(posName));

        return writer;
    }

    public long insert(ContentValues values){
       return db.insert(TABLE_NAME,null,values);
    }
    public long update(ContentValues values, String Whereclause, String[] WhereArgs){
      return   db.update(TABLE_NAME,values,Whereclause,WhereArgs);
    }
    public long delete(String Whereclause, String[] whereArgs){
       return db.delete(TABLE_NAME,Whereclause,whereArgs);
    }
    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
