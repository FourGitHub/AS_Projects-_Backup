package com.example.four.uiwidgettest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageViewTest extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_test_layout);
        imageView = (ImageView) findViewById(R.id.imageview);
        Button button2 = (Button) findViewById(R.id.button_2);
        Button button3 = (Button) findViewById(R.id.button_3);
        button2.setOnClickListener(new View.OnClickListener() {
            int i = 1;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_2:
                        if (i % 2 == 1)
                            imageView.setImageResource(R.drawable.img_2);
                        else
                            imageView.setImageResource(R.drawable.img_1);
                        i++;
                        break;
                    default:
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageViewTest.this, ProgressBarActivity.class);
                startActivity(intent);
            }
        });
    }

}
