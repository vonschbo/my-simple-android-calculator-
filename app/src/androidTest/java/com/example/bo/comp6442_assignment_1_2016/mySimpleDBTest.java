package com.example.bo.comp6442_assignment_1_2016;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import junit.framework.TestCase;



/**
 * Created by Bo on 25/03/2016.
 */
public class mySimpleDBTest extends AndroidTestCase {

    private mySimpleDB db;

    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new mySimpleDB(context);
    }

    public void testConstructor(){
        assertEquals(mySimpleDB.NOTE_ID, "_id");
        assertFalse(mySimpleDB.NOTE_TEXT == "text");
        assertEquals(mySimpleDB.NOTE_TEXT, "noteText");
        assertFalse(mySimpleDB.NOTE_CREATED == "oteCreated");
        assertTrue(mySimpleDB.NOTE_CREATED == "noteCreated");
        assertTrue(db.getDatabaseName() == "MyNotes.db");

        SQLiteDatabase mdb = db.getWritableDatabase();
        assertEquals(mdb.getVersion(), 1);
        Cursor dbCursor = mdb.query(mySimpleDB.TABLE_NOTES, null,null,null,null, null,null);
        int columnName1 = dbCursor.getColumnIndex("_id");
        assertEquals(columnName1,0);

        int columnName2 = dbCursor.getColumnIndex("noteText");
        assertEquals(columnName2,1);


    }



    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }



}