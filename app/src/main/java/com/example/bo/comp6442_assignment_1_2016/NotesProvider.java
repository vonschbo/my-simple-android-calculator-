package com.example.bo.comp6442_assignment_1_2016;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * @author Bo
 * Refer to the tutorial http://www.lynda.com/Android-tutorials/Managing-database-content-provider/
 */

//acess and manage data, define CRUD operations
//content provider as the mid layer from UI to database deal with requests
    //use uri to access the content
public class NotesProvider extends ContentProvider {

    //global unique string to identify this noteprovider
    private static final String AUTHORITY = "com.example.bo.comp6442_assignment_1_2016.notesprovider";
    //base path
    private static final String BASE_PATH = "notes";
    //identify the note provider
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

    //show action, initialize and parse the uri
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String Content_Item_Type = "Note";

    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH,NOTES);
        uriMatcher.addURI(AUTHORITY,BASE_PATH+"/#",NOTES_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        mySimpleDB myDB = new mySimpleDB(getContext());
        database = myDB.getWritableDatabase();
        return true;
    }

    //use queries to do the operation, a Cursor object, manage asynchronous calls to the content provider
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //return the use selected note,return a single note
        if(uriMatcher.match(uri)==NOTES_ID){
            selection = mySimpleDB.NOTE_ID + "=" + uri.getLastPathSegment();

        }
        return database.query(mySimpleDB.TABLE_NOTES,mySimpleDB.ALL_COLUMNS,selection,null,null,null,
                mySimpleDB.NOTE_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(mySimpleDB.TABLE_NOTES, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(mySimpleDB.TABLE_NOTES, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(mySimpleDB.TABLE_NOTES, values, selection, selectionArgs);
    }
}
