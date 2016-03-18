package com.example.bo.comp6442_assignment_1_2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void create(View view){
        Intent intent = new Intent(MainActivity.this,MainPage.class);
        startActivity(intent);
    }
}
