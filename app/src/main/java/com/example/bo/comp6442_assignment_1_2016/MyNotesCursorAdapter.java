package com.example.bo.comp6442_assignment_1_2016;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Bo on 21/03/2016.
 */

//to display dynamically instead of simpleCursorAdapter
public class MyNotesCursorAdapter extends CursorAdapter{
    public MyNotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
