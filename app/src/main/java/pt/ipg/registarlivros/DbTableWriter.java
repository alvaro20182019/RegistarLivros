package pt.ipg.registarlivros;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */
public class DbTableWriter implements BaseColumns {
    public static final String TABLE_NAME = "writers";
    public static final String FIELD_NAME = "name";

    public static final String [] ALL_COLUMNS = new String[] { _ID, FIELD_NAME };

    private SQLiteDatabase db;

    public DbTableWriter(SQLiteDatabase db) {
        this.db = db;
    }

    public void create() {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_NAME + " TEXT NOT NULL" +
                        ")"
        );
    }

    public static ContentValues getContentValues(Writer writer) {
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, writer.getName());

        return values;
    }

    public static Writer getCurrentCategoryFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posName = cursor.getColumnIndex(FIELD_NAME);

            Writer writer = new Writer();

        writer.setId(cursor.getInt(posId));
        writer.setName(cursor.getString(posName));

        return writer;
    }

    public long insert(ContentValues values) {
        return db.insert(TABLE_NAME, null, values);
    }


    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLE_NAME, values, whereClause, whereArgs);
    }


    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLE_NAME, whereClause, whereArgs);
    }


    public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
