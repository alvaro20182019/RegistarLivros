package pt.ipg.registarlivros;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ÁlvaroSF on 19/06/2018.
 */

public class BooksContentProvider extends ContentProvider {

    DbBooksOpenHelper BooksOpenHelper;

    private  static final int BOOKS=100;
    private  static final int BOOKS_ID=101;
    private  static final int CATEGORIES=200;
    private  static final int CATEGORIES_ID=201;
    private  static final int WRITER=300;
    private  static final int WRITER_ID=301;

    private  static UriMatcher getBookUriMatcher(){
        UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI("pt.ipg.registarlivros","books",BOOKS);
        uriMatcher.addURI("pt.ipg.registarlivros","books/#",BOOKS_ID);
        uriMatcher.addURI("pt.ipg.registarlivros","categories",CATEGORIES);
        uriMatcher.addURI("pt.ipg.registarlivros","categories/#",CATEGORIES_ID);
        uriMatcher.addURI("pt.ipg.registarlivros","writers",WRITER);
        uriMatcher.addURI("pt.ipg.registarlivros","writers/#",WRITER_ID);
        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        BooksOpenHelper = new DbBooksOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db= BooksOpenHelper.getReadableDatabase();
        String id = uri.getLastPathSegment();
        UriMatcher matcher= getBookUriMatcher();

        switch (matcher.match(uri)){

            case BOOKS:
                return new DbTableBookss(db).query(projection, selection, selectionArgs, null, null, sortOrder);
            case BOOKS_ID:
                return new DbTableBookss(db).query(projection, DbTableBookss._ID + "=?", new String[] { id }, null, null, null);

            case CATEGORIES:
                return new DbTableCategory(db).query(projection, selection, selectionArgs, null, null, sortOrder);
            case CATEGORIES_ID:
                return new DbTableCategory(db).query(projection, DbTableCategory._ID + "=?", new String[] { id }, null, null, null);

            case WRITER:
                return new DbTableWriter(db).query(projection, selection, selectionArgs, null, null, sortOrder);
            case WRITER_ID:
                return new DbTableWriter(db).query(projection, DbTableWriter._ID + "=?", new String[] { id }, null, null, null);

            default:
               throw new UnsupportedOperationException("Invalid URI: " + uri);
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = BooksOpenHelper.getWritableDatabase();
        UriMatcher matcher=getBookUriMatcher();

        long id =-1;

        switch (matcher.match(uri)){
            case BOOKS:
                id= new DbTableBookss(db).insert(values);
                break;
            case CATEGORIES:
                id=new DbTableCategory(db).insert(values);
                break;
            case WRITER:
                id=new DbTableWriter(db).insert(values);
            default:
               throw new UnsupportedOperationException("Invalid URI: " + uri);

        }
        if(id>0){
            notifyChanges(uri);
            return Uri.withAppendedPath(uri, Long.toString(id));
        }else{
            throw new SQLException("Could not insert record");
        }
    }


    private void notifyChanges(Uri uri) {
        getContext().getContentResolver().notifyChange(uri,null);
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
