package pt.ipg.registarlivros;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by ÁlvaroSF on 13/06/2018.
 */

public class DbTableCategory implements BaseColumns {

    public static final String TABLE_NAME = "category";
    public static final String FIELD_NAME = "nome";
    private final SQLiteDatabase db;
    public static final String [] ALL_COLUMNS = new String[] { _ID, FIELD_NAME };
    public DbTableCategory (SQLiteDatabase db){
        this.db=db;

    }

    public void create(){

        db.execSQL("CREATE TABLE " + TABLE_NAME + "("+_ID+" INTEGER  PRIMARY KEY AUTOINCREMENT, " + FIELD_NAME + " TEXT NOT NULL)");
    }
    public static ContentValues getContentValues(Category category) {
        ContentValues values = new ContentValues();

        values.put(_ID, category.getId());
        values.put(FIELD_NAME, category.getNome());

        return values;
    }

    public static Category getCurrentCategoryFromCursor(Cursor cursor) {
        final int posId = cursor.getColumnIndex(_ID);
        final int posName = cursor.getColumnIndex(FIELD_NAME);

        Category category = new Category();

        category.setId(cursor.getInt(posId));
        category.setNome(cursor.getString(posName));

        return category;
    }

}
