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
        assertTrue("Failed to read the first category", cursor.moveToNext());

        Writer writer1 = DbTableWriter.getCurrentCategoryFromCursor(cursor);
        assertEquals("Incorrect writer name","Fernando Pessoa" , writer.getName());
        assertEquals("Incorrect writer id", id, writer.getId());

        // update CR(U)D
        writer.setName("Fernando");
        long rowsAffected =  tableWriter.update(
                DbTableWriter.getContentValues(writer),
                DbTableWriter._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update writer", 1, rowsAffected);

        // delete CRU(D)
        rowsAffected = tableWriter.delete(
                DbTableWriter._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete writer", 1, rowsAffected);

        Cursor cursor2 = tableWriter.query(DbTableWriter.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("number writer after delete ", 0, cursor.getCount());
    }

    public void booksCRUDtest(){
        DbBooksOpenHelper dbBooksOpenHelper = new DbBooksOpenHelper(getContext());
        SQLiteDatabase db = dbBooksOpenHelper.getWritableDatabase();
        DbTableCategory tableCategory = new DbTableCategory(db);
        Category category=new Category();
        category.setNome("Fabula");
        long idcategory = tableCategory.
        // Insert/create (C)RUD
        DbTableBookss tableBookss= new DbTableBookss(db);
        Book book= new Book();
        book.setDiscription("comprado em Braga");
        book.setState("Não Lido");
        book.setTitle("Era uma vez");
         long id = tableBookss.insert(DbTableBookss.getContentValues(book));
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
