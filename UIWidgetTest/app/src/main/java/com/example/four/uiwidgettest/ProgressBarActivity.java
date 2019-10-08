package com.example.four.uiwidgettest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ProgressBarActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar_layout);
        final Button button1 = (Button) findViewById(R.id.button_1);
        Button button2 = (Button) findViewById(R.id.button_2);
        progressBar = (ProgressBar) findViewById(R.id.prgross_bar);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_1:
                        if(progressBar.getVisibility() == View.GONE){
                            progressBar.setVisibility(View.VISIBLE);
                            button1.setText("Stop ProgressBar Test");
                        }else{
                            progressBar.setVisibility(View.GONE);
                            button1.setText("Start ProgressBar Test");
                        }
                        break;
                    default:
                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressBarActivity.this, AlertDialogActivity.class);
                startActivity(intent);
            }
        });
    }
}
