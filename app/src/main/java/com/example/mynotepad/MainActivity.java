package com.example.mynotepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_note:
                Toast.makeText(this, "Вызываю фрагмент для создания записи",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.edit_note:
                Toast.makeText(this, "Вызываю фрагмент для редактирования",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.activity_settings:
                Toast.makeText(this, "Вызываю фрагмент/активити настроек",
                        Toast.LENGTH_SHORT).show();
                return true;
                case R.id.about:
                Toast.makeText(this, "Вызываю фрагмент Об авторах",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return true;
    }
}