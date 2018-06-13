package pt.ipg.registarlivros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DBtest {
    public void setUp() {
        getContext().deleteDatabase(DbBooksOpenHelper.DATABASE_NAME);
    }
    @Test
    public void OpenBooksDbTest() {
        // Context of the app under test.
        Context appContext = getContext();
        DbBooksOpenHelper dbBooksOpenHelper  =new DbBooksOpenHelper(appContext);


        SQLiteDatabase db = dbBooksOpenHelper.getReadableDatabase();
        assertTrue("impossivel abrir bd books",db.isOpen());
        db.close();
    }

    public void WriterCRUDtest(){
        DbBooksOpenHelper dbBooksOpenHelper = new DbBooksOpenHelper(getContext());
        SQLiteDatabase db = dbBooksOpenHelper.getWritableDatabase();
        DbTableWriter tableWriter= new DbTableWriter(db);

        Writer writer= new Writer();
        writer.setName("Fernando Pessoa");

        // inserir (C)RUD
        long id=tableWriter.insert(
                DbTableWriter.getContentValues(writer));
        assertNotEquals("erro no insert",-1,id);

        // ler C(R)UD
        Cursor cursor= tableWriter.query(tableWriter.ALL_COLUMNS,null,null,null,null,null);
        assertEquals("ERRO AO LER TABELA WRITER",1,cursor.getColumnCount());


    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
