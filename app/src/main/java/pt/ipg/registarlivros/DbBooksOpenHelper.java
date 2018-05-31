package pt.ipg.registarlivros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */

public class DbBooksOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "books.db";
    public static final int DATABASE_VERSION = 1;

    public DbBooksOpenHelper(Context context) { //context é o está tentar aceder a bd
        super(context, DATABASE_NAME,   null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){//oande é criada a base de dados

        DbTableBookss dbTableBooks = new DbTableBookss(db);
        dbTableBooks.create();

        DbTableWriter dbTableWriter = new DbTableWriter(db);
        dbTableWriter.create();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
