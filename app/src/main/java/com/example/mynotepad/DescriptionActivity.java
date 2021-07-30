package com.example.mynotepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DescriptionActivity extends AppCompatActivity implements DescriptionFragment.Controller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            DescriptionFragment fragment = new DescriptionFragment();
            fragment.setArguments(getIntent().getExtras());


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.description_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_description_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_note:
                Toast.makeText(this, "Вызываю фрагмент для редактирования записи",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return true;
    }

    @Override
    public void changeTitleToolbar(String titleToolbar) {
        getSupportActionBar().setTitle(titleToolbar);
    }
}