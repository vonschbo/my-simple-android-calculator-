package com.example.bo.comp6442_assignment_1_2016;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
   private CursorAdapter cursorAdapter;
//    mySimpleDB myDb;
//    EditText editid,edittext,edittime;
//    Button adda;
//    Button btnShow;
//    Button btnupdate;
//    Button btnDelete;
    private static final int Create_New_Note = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//            insertNode("New note");

//        String[] from = {mySimpleDB.NOTE_TEXT};
            //int[] to = {android.R.id.text1};
//            int[] to = {R.id.tvNote};

            //cursorAdapter = new SimpleCursorAdapter(this, R.layout.note_list_item, null, from, to, 0);
            cursorAdapter = new MyNotesCursorAdapter(this, null,0);

            ListView list = (ListView)findViewById(android.R.id.list);
            list.setAdapter(cursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Uri uri = Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
                intent.putExtra(NotesProvider.Content_Item_Type, uri);
                startActivityForResult(intent, Create_New_Note);
            }
        });

            getLoaderManager().initLoader(0,null,this);



//        myDb = new mySimpleDB(this);
//            SQLiteDatabase database = myDb.getWritableDatabase();

//            editid = (EditText)findViewById(R.id.editText);
//            edittext = (EditText)findViewById(R.id.editText2);
//            edittime = (EditText)findViewById(R.id.editText3);
//            adda = (Button)findViewById(R.id.button_add);
//            btnShow = (Button)findViewById(R.id.button_show);
//            btnupdate = (Button)findViewById(R.id.button_update);
//            btnDelete = (Button)findViewById(R.id.button_de);

//        insertNote("New note");
//


    }

    private void insertNode(String noteText) {
        ContentValues values = new ContentValues();
        values.put(mySimpleDB.NOTE_TEXT, noteText);
        Uri noteUri = getContentResolver().insert(NotesProvider.CONTENT_URI, values);
        Log.d("MainActivity", "Inserted note " + noteUri.getLastPathSegment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_create_sample:
                insertSampleData();
            break;

            case R.id.action_delete_all:
                deleteAllNotes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int button) {
                        if (button == DialogInterface.BUTTON_POSITIVE) {
                            //Insert Data management code here
                            getContentResolver().delete(NotesProvider.CONTENT_URI, null, null);
                            restartLoader();
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.all_deleted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(android.R.string.yes), dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener)
                .show();
    }

    private void insertSampleData() {
        insertNode("Simple note");
        insertNode("Multi-line\nnote");
        insertNode("Very long note with a lot of text that exceeds the width of the screen");
        restartLoader();
    }

    //restarts the loader from the loader manager and refills the data from the database and displays it in the list
    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, NotesProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    public void createNewNote(View view){
        Intent intent = new Intent(this, EditActivity.class);
        startActivityForResult(intent, Create_New_Note);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Create_New_Note && resultCode == RESULT_OK){
            restartLoader();
        }
    }

    //    public void addad(View view){
//        boolean insertData = myDb.insertData(edittext.getText().toString(), edittime.getText().toString());
//        if(insertData ==true){
//            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
//        }
//    }

//    public void UpdateData(View view){
//        boolean updated = myDb.updataData(editid.getText().toString(),
//                edittext.getText().toString(), edittime.getText().toString());
//        if(updated == true){
//            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
//        }
//
//    }

//    public void showAll(View view){
//        Cursor res = myDb.getAllData();
//        if(res.getCount()==0){
//            // show message
//            showMessage("Error", "Nothing found");
//            return;
//        }else {
//            StringBuffer buffer = new StringBuffer();
//            while (res.moveToNext()){
//                buffer.append("id: "+ res.getString(0)+"\n");
//                buffer.append("noteText: "+ res.getString(1)+"\n");
//                buffer.append("noteCreated: "+ res.getString(2)+"\n\n");
//            }
//            //show all the data
//            showMessage("Data", buffer.toString());
//        }
//    }

//    public void showMessage(String title,String Message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(Message);
//        builder.show();
//    }

// public void deleteData(View view){
//        Integer deleteRow = myDb.deleteData(editid.getText().toString());
//        if(deleteRow > 0){
//            Toast.makeText(MainActivity.this,"Data Delete",Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(MainActivity.this,"Data not Delete",Toast.LENGTH_LONG).show();
//        }
//
//
//    }




}
