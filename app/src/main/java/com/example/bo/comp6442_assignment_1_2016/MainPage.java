package com.example.bo.comp6442_assignment_1_2016;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainPage extends AppCompatActivity {

    EditText textEnteredTitle;
    EditText textEnteredContent;
    String saved;
    String filename;
    FileInputStream inputStream;
    FileOutputStream outputStream;
    File persistentFile;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        textEnteredTitle = (EditText) findViewById(R.id.editText_title);
        textEnteredContent = (EditText)findViewById(R.id.editText_contents);
        filename = textEnteredTitle.getText().toString();

        /* Read data from the persistent file */
        persistentFile = new File(getFilesDir(),filename);
        if (persistentFile.exists()){
            try{
                inputStream = openFileInput(filename);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder buffer = new StringBuilder();
                while ((line = input.readLine()) != null){
                    buffer.append(line);
                }
                saved = buffer.toString();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void save(View view){
        String textBuffer = saved;
        String appendText = textEnteredContent.getText().toString();
        textBuffer += "..";
        textBuffer += appendText;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(textBuffer.getBytes());
            outputStream.close();
            textEnteredContent.setText(textBuffer);
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void edit(View view){

    }

//    public void delete(View view){
//        new AlertDialog.Builder(this).setTitle("Confirm action").setMessage("Are you sure to delete this?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        })
//
//    }
}
