package com.example.four.mymeitu;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private ImageView iv;
    private SeekBar sb_red;
    private SeekBar sb_green;
    private SeekBar sb_blue;
    private SeekBar sb_rgb;
    private static int r_progress = 0;
    private static int g_progress = 0;
    private static int b_progress = 0;
    private static int rgb_progress = 0;

    private float[] values = new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0,
    };

    private ColorFilter colorFilter = new ColorMatrixColorFilter(values);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView)findViewById(R.id.iv);
        sb_red = (SeekBar)findViewById(R.id.sb_red);
        sb_green = (SeekBar)findViewById(R.id.sb_green);
        sb_blue= (SeekBar)findViewById(R.id.sb_blue);
        sb_rgb = (SeekBar)findViewById(R.id.sb_rgb);

        sb_red.setOnSeekBarChangeListener(this);
        sb_green.setOnSeekBarChangeListener(this);
        sb_blue.setOnSeekBarChangeListener(this);
        sb_rgb.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_red:
                values[4] = progress ;
                break;
            case R.id.sb_green:
                values[9] = progress;
                break;
            case R.id.sb_blue:
                values[14] = progress;
                break;
            case R.id.sb_rgb:
                values[4] = values[9] = values[14] = progress;
                break;
        }
        colorFilter = new ColorMatrixColorFilter(values);
        iv.setColorFilter(colorFilter);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                saveImage();
                break;
        }
        return true;
    }

    public void saveImage(){

    }
}
