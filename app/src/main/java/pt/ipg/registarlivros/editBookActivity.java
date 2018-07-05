package pt.ipg.registarlivros;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class editBookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText editTextTitulo;
    private EditText editTextEstado;
    private Spinner spinnerCategoria;
    private Book book;
    private static final int CATEGORIES_CURSOR_LOADER_ID = 0;

    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_books);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        int id_book= intent.getIntExtra(MainActivity.BOOK_ID,-1);

        if(id_book==-1){
            finish();
            return;
        }

        Cursor cursorbook=getContentResolver().query(Uri.withAppendedPath(BooksContentProvider.BOOKSS_URI,Integer.toString(id_book)),DbTableBookss.ALL_COLUMNS,null,null,null);

        if(!cursorbook.moveToNext()){
            Toast.makeText(this,"Livro não encontrado",Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        editTextTitulo= (EditText) findViewById(R.id.editTextTitulo);
        spinnerCategoria=(Spinner) findViewById(R.id.spinnerCategoria);
        editTextEstado= (EditText) findViewById(R.id.textInputLayoutState);
        book=DbTableBookss.getCurrentBookFromCursor(cursorbook);

        editTextTitulo.setText(book.getTitle());
        editTextEstado.setText(book.getState());
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportLoaderManager().initLoader(CATEGORIES_CURSOR_LOADER_ID,null,this);
    }
    protected void onResume() {

        super.onResume();
        getSupportLoaderManager().restartLoader(CATEGORIES_CURSOR_LOADER_ID,null,this);
    }
    public void save(View view){
        book.setTitle(editTextTitulo.getText().toString());
        book.setIdcategory((int) spinnerCategoria.getSelectedItemId());
        int registrosafetados= getContentResolver().update(Uri.withAppendedPath(BooksContentProvider.BOOKSS_URI,Integer.toString(book.getId())),DbTableBookss.getContentValues(book),null,null);
    if(registrosafetados>0){
        Toast.makeText(this,"Guardado com sucesso",Toast.LENGTH_LONG).show();
        finish();
        return;
    }
    Toast.makeText(this,"Erro ao Guardar",Toast.LENGTH_LONG).show();

    }
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id==CATEGORIES_CURSOR_LOADER_ID) {
            return new CursorLoader(this,BooksContentProvider.CATEGORY_URI,DbTableCategory.ALL_COLUMNS,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        SimpleCursorAdapter cursorAdapterCategoria =
                new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,data,new String[]{DbTableCategory.FIELD_NAME},new int[]{android.R.id.text1});
        spinnerCategoria.setAdapter(cursorAdapterCategoria);
        int idCategoria=book.getIdcategory();

        for (int i=0;i<spinnerCategoria.getCount();i++){
            Cursor cursor=(Cursor) spinnerCategoria.getItemAtPosition(i);
            final int posID= cursor.getColumnIndex(DbTableCategory._ID);
            if(idCategoria==cursor.getInt(posID)){
                spinnerCategoria.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
