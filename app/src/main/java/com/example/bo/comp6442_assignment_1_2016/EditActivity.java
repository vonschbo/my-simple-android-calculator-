package com.example.bo.comp6442_assignment_1_2016;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//refer to the http://www.lynda.com/Android-tutorials,Building a Note-Taking App for Android
//this is the edittext page, to create, edit and delete note
public class EditActivity extends AppCompatActivity {

    //to know what is doing
    private String action;

    private EditText editor;

    // a where clause in SQL statement
    private String noteFilter;

    //contain the existing text
    private String oldText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor = (EditText)findViewById(R.id.editText);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(NotesProvider.Content_Item_Type);

        //set the title of EditActivity that means I am creating a new note or not
        if(uri == null){
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_note));
        }else {
            action = Intent.ACTION_EDIT;
            noteFilter = mySimpleDB.NOTE_ID +"=" + uri.getLastPathSegment();

            //retrieve from database
            Cursor cursor = getContentResolver().query(uri, mySimpleDB.ALL_COLUMNS, noteFilter, null, null);
            //move to the one and the only row, get the selected note
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(mySimpleDB.NOTE_TEXT));
            editor.setText(oldText);
            //move the cursor to the end of existing text
            editor.requestFocus();

        }



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(action.equals(Intent.ACTION_EDIT)){
            getMenuInflater().inflate(R.menu.menu_edit, menu);
        }
//        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    //the menu option
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (item.getItemId()){
            case android.R.id.home:
                finishEdit();
                break;
            case R.id.action_delete:
                deleteNote();
                break;
        }
        return true;

    }

    //delete the note from db
    private void deleteNote() {
        getContentResolver().delete(NotesProvider.CONTENT_URI, noteFilter, null);
        Toast.makeText(this, R.string.note_deleted,Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    //to save the persistent note
    private void finishEdit(){
        String newText = editor.getText().toString().trim();

        switch (action){
            case Intent.ACTION_INSERT:
                if(newText.length()==0){
                    setResult(RESULT_CANCELED);
                }else {
                    insertNote(newText);
                }break;

            //if the user delete the note
            case Intent.ACTION_EDIT:
                if(newText.length()==0){
                    deleteNote();

                    //if user not change the note, remain the same
                }else if(oldText.equals(newText)){
                    setResult(RESULT_CANCELED);
                }else {
                    updateNote(newText);
                }
        }finish();
    }

    //update the content in database, make sure only update the selected row
    private void updateNote(String noteText) {
        ContentValues values = new ContentValues();
        values.put(mySimpleDB.NOTE_TEXT, noteText);
        getContentResolver().update(NotesProvider.CONTENT_URI, values, noteFilter, null);
        Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    //create the new note
    private void insertNote(String noteText) {

        //create the value object, and put text into it,update the database
        ContentValues values = new ContentValues();
        values.put(mySimpleDB.NOTE_TEXT, noteText);
        getContentResolver().insert(NotesProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    //save the status when user press the back button
    @Override
    public void onBackPressed(){
        finishEdit();
    }

}
