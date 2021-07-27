package com.example.mynotepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean isLandscape = getResources().getConfiguration().
                orientation == Configuration.ORIENTATION_LANDSCAPE;

        getMenuInflater().inflate(isLandscape ? R.menu.title_menu_land :
                R.menu.title_menu_port, menu);
        return true;
    }
}