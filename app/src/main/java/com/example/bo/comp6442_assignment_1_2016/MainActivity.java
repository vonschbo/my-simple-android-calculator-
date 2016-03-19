package com.example.bo.comp6442_assignment_1_2016;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    mySimpleDB myDb;
    EditText editid,edittext,edittime;
    Button adda;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new mySimpleDB(this);

            editid = (EditText)findViewById(R.id.editText);
            edittext = (EditText)findViewById(R.id.editText2);
            edittime = (EditText)findViewById(R.id.editText3);
            adda = (Button)findViewById(R.id.button_add);

//        insertNote("New note");
//
//        Cursor cursor = getContentResolver().query(NotesProvider.CONTENT_URI,
//                mySimpleDB.ALL_COLUMNS, null, null, null, null);
//        String[] from = {mySimpleDB.NOTE_TEXT};
//        int[] to = {android.R.id.text1};
//        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,cursor, from,to,0);
//
//        ListView list = (ListView)findViewById(R.id.list1);
//        list.setAdapter(cursorAdapter);

    }

    public void addad(View view){
        boolean insertData = myDb.insertData(edittext.getText().toString(),edittime.getText().toString());
        if(insertData ==true){
            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
        }
    }


    private void insertNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(mySimpleDB.NOTE_TEXT, noteText);
        Uri noteUri = getContentResolver().insert(NotesProvider.CONTENT_URI, values);
        Log.d("MainActivity", "Inserted note" + noteUri.getLastPathSegment());
    }


}
