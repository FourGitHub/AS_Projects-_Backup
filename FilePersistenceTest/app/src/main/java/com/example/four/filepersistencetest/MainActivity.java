package com.example.four.filepersistencetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private BufferedWriter writer;
    private FileOutputStream out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        String dataInput = load();
        if(!TextUtils.isEmpty(dataInput)){
            editText.setText(dataInput);
            editText.setSelection(dataInput.length());
        }
        button = (Button) findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editText.getText().toString();
                editText.setText("");
                save(msg);
            }
        });

    }

    private void save(String msg){
        try {
            out = openFileOutput("TextSavedData",MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(msg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String load(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(openFileInput("TextSavedData")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder data = new StringBuilder();
        String line;
        try {
            while((line = reader.readLine()) != null){
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data.toString();
    }
}
