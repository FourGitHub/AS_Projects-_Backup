package com.example.four.sharepreferencetest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.input_text);
        Button button = (Button) findViewById(R.id.save_data);
        Button button1 = (Button) findViewById(R.id.get_data);


        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editText.getText().toString();
                editText.setText("");
                editor = getSharedPreferences("SavedData",MODE_PRIVATE).edit();
                editor.putString("data_input",data);
                editor.apply();
            }
        });

        button1.setOnClickListener(view -> {
            preferences = getSharedPreferences("SavedData",MODE_PRIVATE);
            String data = preferences.getString("data_input","");
            editText.setText(data);
            editText.setSelection(data.length());
        });
    }
}
