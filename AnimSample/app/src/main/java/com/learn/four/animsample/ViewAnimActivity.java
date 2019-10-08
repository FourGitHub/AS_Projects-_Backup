package com.learn.four.animsample;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ViewAnimActivity extends AppCompatActivity {

    @BindView(R.id.img_me)
    ImageView imgMe;
    @BindView(R.id.img_wifi)
    ImageView imgWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        overridePendingTransition(R.anim.ac_enter_trans_anim, R.anim.ac_exit_trans_anim);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        Animation alphaAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        /* 任何View组件都可以运行动画 ，然后都可以调用 imgMe.clearAnimation() 清除自身动画效果 */
        imgMe.startAnimation(alphaAnim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgWifi.setImageResource(R.drawable.frame_anim);
        AnimationDrawable frameWifiAnim = (AnimationDrawable) imgWifi.getDrawable();
        imgWifi.setOnClickListener(v -> {
            if (!frameWifiAnim.isRunning()) {
                frameWifiAnim.start();
                imgWifi.postDelayed(() -> {
                    if (frameWifiAnim.isRunning()) {
                        frameWifiAnim.stop();
                    }
                }, 2900);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toPropertyAc:
                Intent i = new Intent(this, PropertyAnimActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.ac_enter_trans_anim, R.anim.ac_exit_trans_anim);
                break;
            case R.id.toLayoutAc:
                Intent i1 = new Intent(this, LayoutAnimActivity.class);
                startActivity(i1);
                overridePendingTransition(R.anim.ac_enter_trans_anim, R.anim.ac_exit_trans_anim);
                break;
        }
        return true;
    }

    @OnClick(R.id.img_wifi)
    public void onViewClicked() {
        imgWifi.setImageResource(R.drawable.frame_anim);
        AnimationDrawable frameWifiAnim = (AnimationDrawable) imgMe.getDrawable();
        frameWifiAnim.start();
        imgWifi.postDelayed(() -> {
            if (frameWifiAnim.isRunning()) {
                frameWifiAnim.stop();
            }
        }, 2900);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void aMethod() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ViewAnimActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void userDenied() {
    }
}
