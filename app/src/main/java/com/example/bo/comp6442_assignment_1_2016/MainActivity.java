package com.example.bo.comp6442_assignment_1_2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.mylist_view);
    }

    public void create(View view){
        Intent intent = new Intent(MainActivity.this,MainPage.class);
        startActivity(intent);
    }
}
