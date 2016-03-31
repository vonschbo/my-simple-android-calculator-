package com.example.bo.comp6442_assignment_1_2016;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * @author Bo
 * Refer to the tutorial http://www.lynda.com/Android-tutorials//
 */
//to display dynamically instead of simpleCursorAdapter,
    //handle nodes that have line feeds
public class MyNotesCursorAdapter extends CursorAdapter{
    public MyNotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    //the View will be created based on the layout defined the list item
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.note_list_item, parent,false);

    }

    //When bind the view, it will receive an instance of the cursor object,
    // and it will already point to the particular row of the database that's supposed to be displayed.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //the text of note to display
        String noteText = cursor.getString(cursor.getColumnIndex(mySimpleDB.NOTE_TEXT));

        //position, 10 is ASC2 value of a line feed character, reset the note text
        int pos = noteText.indexOf(10);
        if(pos != -1){
            noteText = noteText.substring(0, pos) + " ...";
        }

        TextView tv = (TextView)view.findViewById(R.id.tvNote);
        tv.setText(noteText);
    }
}
