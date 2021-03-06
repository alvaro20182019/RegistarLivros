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
    private static final String AUTHORITY="pt.ipg.registarlivros";
    private static final String MULTIPLE_ITEMS = "vnd.android.cursor.dir";
    private static final String SINGLE_ITEM = "vnd.android.cursor.item";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri BOOKSS_URI = Uri.withAppendedPath(BASE_URI, DbTableBookss.TABLE_NAME);
    public static final Uri CATEGORY_URI = Uri.withAppendedPath(BASE_URI, DbTableCategory.TABLE_NAME);
    DbBooksOpenHelper BooksOpenHelper;

    private  static final int BOOKS=100;
    private  static final int BOOKS_ID=101;
    private  static final int CATEGORIES=200;
    private  static final int CATEGORIES_ID=201;
    private  static final int WRITER=300;
    private  static final int WRITER_ID=301;

    private  static UriMatcher getBookUriMatcher(){
        UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY,"books",BOOKS);
        uriMatcher.addURI(AUTHORITY,"books/#",BOOKS_ID);
        uriMatcher.addURI(AUTHORITY,"categories",CATEGORIES);
        uriMatcher.addURI(AUTHORITY,"categories/#",CATEGORIES_ID);
       // uriMatcher.addURI(AUTHORITY,"writers",WRITER);
        //uriMatcher.addURI(AUTHORITY,"writers/#",WRITER_ID);
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
/*
            case WRITER:
                return new DbTableWriter(db).query(projection, selection, selectionArgs, null, null, sortOrder);
            case WRITER_ID:
                return new DbTableWriter(db).query(projection, DbTableWriter._ID + "=?", new String[] { id }, null, null, null);
*/
            default:
               throw new UnsupportedOperationException("Invalid URI: " + uri);
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        UriMatcher matcher = getBookUriMatcher();

                 switch (matcher.match(uri)) {

                     case BOOKS:
                            return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DbTableBookss.TABLE_NAME;
                     case BOOKS_ID:
                         return SINGLE_ITEM + "/" + AUTHORITY + "/" + DbTableBookss.TABLE_NAME;

                     case CATEGORIES:
                              return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DbTableCategory.TABLE_NAME;

                     case CATEGORIES_ID:
                             return SINGLE_ITEM  + "/" + AUTHORITY + "/" + DbTableCategory.TABLE_NAME;
                    /* case WRITER:
                         return MULTIPLE_ITEMS + "/" + AUTHORITY + "/" + DbTableWriter.TABLE_NAME;

                     case WRITER_ID:
                         return SINGLE_ITEM  + "/" + AUTHORITY + "/" + DbTableWriter.TABLE_NAME;
                              */
                              default:
                               throw new UnsupportedOperationException("Unknown URI: " + uri);
                     }
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
        //    case WRITER:
        //        id=new DbTableWriter(db).insert(values);
            default:
               throw new UnsupportedOperationException("Invalid URI: " + uri);

        }
        if(id>0){
            notifyChanges(uri);
            return Uri.withAppendedPath(uri, Long.toString(id));
        }else{
            throw new SQLException("erro ao inserir");
        }
    }


    private void notifyChanges(Uri uri) {
        getContext().getContentResolver().notifyChange(uri,null);
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db= BooksOpenHelper.getWritableDatabase();
        String id = uri.getLastPathSegment();
        UriMatcher matcher= getBookUriMatcher();

        int rows=0;
        switch (matcher.match(uri)){
            case BOOKS_ID:
                rows = (int) new DbTableBookss(db).delete(DbTableBookss._ID +"=?", new String [] { id });
                break;
            case CATEGORIES_ID:
                rows = (int) new DbTableCategory(db).delete(DbTableCategory._ID +"=?", new String [] { id });
                break;
         /*   case WRITER_ID:
                rows = (int) new DbTableWriter(db).delete(DbTableWriter._ID +"=?", new String [] { id });
                break;
*/
            default:
               throw new UnsupportedOperationException("Invalid URI: " + uri);
        }
        if(rows>0){
            notifyChanges(uri);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db= BooksOpenHelper.getWritableDatabase();
        String id = uri.getLastPathSegment();
        UriMatcher matcher= getBookUriMatcher();

        int rows=0;
        switch (matcher.match(uri)){


            case BOOKS_ID:
               rows = (int) new DbTableBookss(db).update(values, DbTableBookss._ID +"=?", new String [] { id });
                       break;

            case CATEGORIES_ID:
                rows = (int) new DbTableCategory(db).update(values, DbTableCategory._ID +"=?", new String [] { id });
                break;


          /*  case WRITER_ID:
                rows= (int) new DbTableWriter(db).update(values, DbTableWriter._ID +"=?", new String [] { id });
*/
            default:
                throw new UnsupportedOperationException("Invalid URI: " + uri);
        }

        if(rows>0) {
            notifyChanges(uri);
        }

        return rows;
    }
}
