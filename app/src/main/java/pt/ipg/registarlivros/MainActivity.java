package pt.ipg.registarlivros;

import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int BOOKS_CURSOR_LOADER_ID = 0;
    public static final String BOOK_ID = "BOOK_ID";

    private BooksCursorAdapter booksCursorAdapter;
    private RecyclerView recyclerViewLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewLivros= (RecyclerView) findViewById(R.id.recyclerViewLivro);

        recyclerViewLivros.setLayoutManager(new LinearLayoutManager(this));
        booksCursorAdapter = new BooksCursorAdapter(this);
        recyclerViewLivros.setAdapter(booksCursorAdapter);

        booksCursorAdapter.setViewHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editBook();
            }
        });

        getSupportLoaderManager().initLoader(BOOKS_CURSOR_LOADER_ID, null, this);
    }

    private void editBook() {
        int id = booksCursorAdapter.getLastBookClicked();

        Intent intent = new Intent(this, editBookActivity.class);

        intent.putExtra(BOOK_ID, id);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(BOOKS_CURSOR_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == BOOKS_CURSOR_LOADER_ID) {
            return new CursorLoader(this, BooksContentProvider.BOOKSS_URI, DbTableBookss.ALL_COLUMNS, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        booksCursorAdapter.atualizarDados(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        booksCursorAdapter.atualizarDados(null);
    }
}
