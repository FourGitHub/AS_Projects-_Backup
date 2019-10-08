package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice02ClipPathView extends View {
    private static final String TAG = "Practice02ClipPathView";
    Paint paint = new Paint();
    Bitmap bitmap;

    Path path1 = new Path();
    Path path2 = new Path();

    Point point1;
    Point point2;

    public Practice02ClipPathView(Context context) {
        super(context);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
        // 此处 point 用于定位 Bitmap 中心的位置
        point1 = new Point(200, 200);
        point2 = new Point(600, 200);

        path1.addCircle(point1.x + 200,point1.y + 200,150, Path.Direction.CW);

        /*         EVEN_ODD -> 奇图偶不图 （相交次数，与CW/CCW无关）
           INVERSE_EVEN_ODD -> 偶涂奇不图 （相交次数，与CW/CCW无关）*/

        /*         WINDING -> o图ō不图 （CW +1 / CCW -1）
           INVERSE_WINDING -> ō图o不图 （CW +1 / CCW -1）  */
        path2.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        path2.addCircle(point2.x + 200,point2.y + 200,150, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.clipPath(path1);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        canvas.save();
        canvas.clipPath(path2);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
