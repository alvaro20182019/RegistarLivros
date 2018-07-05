package pt.ipg.registarlivros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
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
    @Before
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
@Test
public void CategoryCRUDtest(){
    DbBooksOpenHelper dbBooksOpenHelper = new DbBooksOpenHelper(getContext());
    SQLiteDatabase db = dbBooksOpenHelper.getWritableDatabase();
    DbTableCategory tableCategory= new DbTableCategory(db);

    Category category= new Category();
    category.setNome("Aventura");

    // inserir (C)RUD
    long id=tableCategory.insert(DbTableCategory.getContentValues(category));
    assertNotEquals("erro no insert",-1,id);

    // ler C(R)UD
    Cursor cursor= tableCategory.query(tableCategory.ALL_COLUMNS,null,null,null,null,null);
    assertEquals("ERRO AO LER TABELA category",2,cursor.getColumnCount());
    assertTrue("impossivel ler categoria", cursor.moveToNext());

    Category category1 = DbTableCategory.getCurrentCategoryFromCursor(cursor);
    assertEquals("Nome de categoria incorreta","Aventura" , category.getNome());
    assertEquals("id incorreto", id, category1.getId());

    // update CR(U)D
    category.setNome("Drama");
    long rowsAffected =  tableCategory.update(
            DbTableCategory.getContentValues(category),
            DbTableCategory._ID + "=?",
            new String[]{Long.toString(id)}
    );
    assertEquals("erro no update", 1, rowsAffected);

    // delete CRU(D)
    rowsAffected = tableCategory.delete(
            DbTableCategory._ID + "=?",
            new String[]{Long.toString(id)}
    );
    assertEquals("erro ao apagar", 1, rowsAffected);

    Cursor cursor2 = tableCategory.query(DbTableCategory.ALL_COLUMNS, null, null, null, null, null);
    assertEquals("numero de linhas apos delete ", 0, cursor.getCount());
}

   /* public void WriterCRUDtest(){
        DbBooksOpenHelper dbBooksOpenHelper = new DbBooksOpenHelper(getContext());
        SQLiteDatabase db = dbBooksOpenHelper.getWritableDatabase();
        DbTableWriter tableWriter= new DbTableWriter(db);

        Writer writer= new Writer();
        writer.setName("Fernando Pessoa");

        // inserir (C)RUD
        long id=tableWriter.insert(DbTableWriter.getContentValues(writer));
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
    }*/

    @Test
    public void booksCRUDtest(){
        DbBooksOpenHelper dbBooksOpenHelper = new DbBooksOpenHelper(getContext());
        SQLiteDatabase db = dbBooksOpenHelper.getWritableDatabase();
        DbTableCategory tableCategory = new DbTableCategory(db);
        Category category=new Category();
        category.setNome("Fabula");
        long idcategory = tableCategory.insert(DbTableCategory.getContentValues(category));


        // Insert/create (C)RUD
        DbTableBookss tableBookss= new DbTableBookss(db);
        Book book= new Book();
        book.setDiscription("comprado em Braga");
        book.setState("Não Lido");
        book.setTitle("Era uma vez");
        book.setIdcategory((int)idcategory);

         long id = tableBookss.insert(DbTableBookss.getContentValues(book));
        assertNotEquals("Failed to insert book", -1, id);

        // query/read C(R)UD
       /* Cursor cursor = tableBookss.query(DbTableBookss.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read books", 1, cursor.getCount());



        assertEquals("Incorrect book title", "Era uma vez", book.getTitle());
        assertEquals("Incorrect book category", idcategory, book.getIdcategory());

        assertEquals("Incorrect book id", id, book.getId());

        // update CR(U)D
        book.setTitle("Era");


        long rowsAffected = tableBookss.update(
                DbTableBookss.getContentValues(book),
                DbTableBookss._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to update an book", 1, rowsAffected);

        //delete CRU(D)
        rowsAffected = tableBookss.delete(
                DbTableBookss._ID + "=?",
                new String[]{Long.toString(id)}
        );
        assertEquals("Failed to delete an book", 1, rowsAffected);
        */
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
