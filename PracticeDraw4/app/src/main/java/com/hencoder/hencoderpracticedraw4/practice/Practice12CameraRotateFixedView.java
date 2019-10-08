package com.hencoder.hencoderpracticedraw4.practice;

import android.bluetooth.le.ScanFilter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(750, 200);
    Camera camera = new Camera();
    Matrix matrix = new Matrix();

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /* Canvas translate */
        canvas.save();
        camera.save();
        camera.rotateX(30);

        canvas.translate(point1.x + bitmap.getWidth() / 2f,
                point1.y + bitmap.getHeight() / 2f);
        camera.applyToCanvas(canvas); // 计算与当前变换对应的矩阵，并将其应用到Canvas
        canvas.translate(-(point1.x + bitmap.getWidth() / 2f),
                         -(point1.y + bitmap.getHeight() / 2f));
        camera.restore();


        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);

        canvas.restore();

        /* Matrix translate */
        canvas.save();
        camera.save();
        camera.rotateY(30);
        matrix.reset();
        camera.getMatrix(matrix);
        camera.restore();

        matrix.postTranslate(100, 100);
        matrix.preTranslate(-(point2.x + bitmap.getWidth() / 2f),
                            -(point2.y + bitmap.getHeight() / 2f));
        matrix.postTranslate(point2.x + bitmap.getWidth() / 2f,
                             point2.y + bitmap.getHeight() / 2f);
        matrix.postTranslate(-100, -100);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
