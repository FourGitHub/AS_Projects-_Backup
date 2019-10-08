package com.example.four.filestorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button getFile;
    private TextView showFile;

    private Button getCache;
    private TextView showCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFile = (Button) findViewById(R.id.get_file);
        showFile = (TextView) findViewById(R.id.show_file);
        getFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showFile.getText().equals("")) {
                    showFile.setText(getFilesDir().getPath());
                } else {
                    showFile.setText("");
                    showFile.setText(getFilesDir().getPath());
                }
            }
        });

        getCache = (Button) findViewById(R.id.get_cache);
        showCache = (TextView) findViewById(R.id.show_cache);
        getCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCache.getText().equals("")) {
                    showCache.setText(getCacheDir().getPath());
                } else {
                    showCache.setText("");
                    showCache.setText(getCacheDir().getPath());
                }
            }
        });

        Intent intent = new Intent(this,MainActivity.class);

    }
}

















