package com.example.bo.comp6442_assignment_1_2016;

import android.net.Uri;

import junit.framework.TestCase;

/**
 * Created by Bo on 25/03/2016.
 */
public class NotesProviderTest extends TestCase {

    public void testConstructor(){

        assertEquals(NotesProvider.Content_Item_Type, "Note");
        assertEquals(NotesProvider.CONTENT_URI, Uri.parse("content://com.example.bo.comp6442_assignment_1_2016.notesprovider/notes"));


    }
}