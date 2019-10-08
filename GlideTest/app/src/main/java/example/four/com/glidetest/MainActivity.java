package example.four.com.glidetest;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_view);
        view = findViewById(R.id.padding_view);

        fusionStatusBar();
    }

    private void fusionStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = getStatusBarHeight();
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void loadImage(View view) {
        String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String urlGif = "http://p1.pstatp.com/large/166200019850062839d3";
//        Glide.with(this).load(url).into(imageView) // 最简单的三步走

//        Glide.with(this).load(url).placeholder(R.drawable.icon_android).into(imageView); // 新增加入占位图功能

//        Glide.with(this).load(url).placeholder(R.drawable.icon_android).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView); // 新增禁用缓存功能

//        Glide.with(this).load(url).placeholder(R.drawable.icon_android).diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.icon_error).into(imageView);// 新增异常占位图

//        Glide.with(this).load(url).asBitmap().placeholder(R.drawable.icon_android).error(R.drawable.icon_error)
//                .diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView); // 新增指定图片格式，只能加载静态图片

//        Glide.with(this).load(urlGif).asGif().placeholder(R.drawable.icon_android).error(R.drawable.icon_error)
//                .diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView); // 新增指定图片格式，只能加载动态图片

        Glide.with(this).load(urlGif).asGif().placeholder(R.drawable.icon_android).error(R.drawable.icon_error)
                .diskCacheStrategy(DiskCacheStrategy.NONE).override(100,100).into(imageView); // 新增指定图片大小
    }
}
