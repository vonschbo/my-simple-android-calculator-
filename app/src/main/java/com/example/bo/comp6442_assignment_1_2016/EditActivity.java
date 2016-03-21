package com.example.bo.comp6442_assignment_1_2016;

import android.content.ContentValues;
import android.content.Intent;
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

public class EditActivity extends AppCompatActivity {

    //to know what is doing
    private String action;

    private EditText editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor = (EditText)findViewById(R.id.editText);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(NotesProvider.Content_Item_Type);

        //that means I am creating a new note
        if(uri == null){
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_note));
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (item.getItemId()){
            case android.R.id.home:
                finishEdit();
                break;
        }
        return true;

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
                }
        }finish();
    }

    //create the new note
    private void insertNote(String noteText) {

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
