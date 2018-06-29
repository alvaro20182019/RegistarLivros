package pt.ipg.registarlivros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ÁlvaroSF on 30/05/2018.
 */
//teste
public class DbBooksOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "books.db";
    public static final int DATABASE_VERSION = 1;
    private static final boolean PRODUCAO=false;

    public DbBooksOpenHelper(Context context) { //context é o está tentar aceder a bd
        super(context, DATABASE_NAME,   null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){//oande é criada a base de dados

       DbTableBookss dbTableBooks = new DbTableBookss(db);
        dbTableBooks.create();

        DbTableCategory dbTableCategory = new DbTableCategory(db);
        dbTableCategory.create();

        DbTableWriter dbTableWriter = new DbTableWriter(db);
        dbTableWriter.create();

        if(!PRODUCAO){
            InserirDB(db);
        }
    }

    public  void  InserirDB(SQLiteDatabase db){
        DbTableWriter dbTableWriter= new DbTableWriter(db);

        Writer writer= new Writer();
        writer.setName("Enid Blyton");
        int idWriter1 = (int) dbTableWriter.insert(DbTableWriter.getContentValues(writer));

         writer= new Writer();
        writer.setName(" Bram Stoker");
        int idWriter2 = (int) dbTableWriter.insert(DbTableWriter.getContentValues(writer));

        writer= new Writer();
        writer.setName(" Herbert George Wells");
        int idWriter3 = (int) dbTableWriter.insert(DbTableWriter.getContentValues(writer));

        DbTableCategory dbTableCategories = new DbTableCategory(db);

        Category category = new Category();
        category.setNome("Aventura");
        int idCategory1 = (int) dbTableCategories.insert(DbTableCategory.getContentValues(category));

        category = new Category();
        category.setNome("Terror");
        int idCategory2= (int) dbTableCategories.insert(DbTableCategory.getContentValues(category));

        category = new Category();
        category.setNome("Sci-fi");
        int idCategory3= (int) dbTableCategories.insert(DbTableCategory.getContentValues(category));


        DbTableBookss dbTableBookss= new DbTableBookss(db);

        Book book=new Book();

        book.setTitle("A ilha secreta");
        book.setIdcategory(idCategory1);
        book.setIdwriter(idWriter1);
        book.setState("Lido");
        dbTableBookss.insert(DbTableBookss.getContentValues(book));

        book=new Book();
        book.setTitle("Drácula");
        book.setIdcategory(idCategory2);
        book.setIdwriter(idWriter2);
        book.setState("Não Lido");
        dbTableBookss.insert(DbTableBookss.getContentValues(book));

        book=new Book();
        book.setTitle("A Guerra do Mundos");
        book.setIdcategory(idCategory3);
        book.setIdwriter(idWriter3);
        book.setState("Lido");
        dbTableBookss.insert(DbTableBookss.getContentValues(book));
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
