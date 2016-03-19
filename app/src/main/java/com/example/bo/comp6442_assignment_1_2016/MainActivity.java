package com.example.bo.comp6442_assignment_1_2016;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    ArrayList<String> noteList = new ArrayList<String>();
//    ListView listView;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        listView = (ListView) findViewById(R.id.mylist_view);
//        EditText EditTitle = (EditText)findViewById(R.id.editText_title);
//
//        ArrayAdapter<String> aa;
//        aa = new ArrayAdapter<String>(this,R.layout.activity_main,noteList);
//        listView.setAdapter(aa);
        insertNote("New note");

        Cursor cursor = getContentResolver().query(NotesProvider.CONTENT_URI,
                mySimpleDB.ALL_COLUMNS, null, null, null, null);
        String[] from = {mySimpleDB.NOTE_TEXT};
        int[] to = {android.R.id.text1};
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,cursor, from,to,0);

        ListView list = (ListView)findViewById(R.id.list1);
        list.setAdapter(cursorAdapter);


    }

    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(mySimpleDB.NOTE_TEXT, noteText);
        Uri noteUri = getContentResolver().insert(NotesProvider.CONTENT_URI, values);
        Log.d("MainActivity", "Inserted note" + noteUri.getLastPathSegment());
    }

    public void create(View view){
        Intent intent = new Intent(MainActivity.this,MainPage.class);
        startActivity(intent);
    }
}
