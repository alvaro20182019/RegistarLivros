package pt.ipg.registarlivros;

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

    public DbTableWriter(SQLiteDatabase db){
        this.db=db;

    }
    public void create(){   // objetivo é criar uma tabela na base de dabos
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + WRITER_NAME + " TEXT NOT NULL)"

        );  //comando sql para criar a tabela
    }

}
