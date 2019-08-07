package com.eecso.powersystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class EELoad extends EEView {


    public EELoad(Context context) {
        super(context);
    }
    public EELoad(Context context,CirCuit cirCuit) {
        super(context,cirCuit);
    }

    public EELoad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EELoad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EELoad(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initConfig() {
        portnum=1;
        oriental=2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //之前画的就不怕旋转
        super.onDraw(canvas);
        drawTransf(canvas);
    }

    private void drawTransf(Canvas canvas) {
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.CENTER);

        paint.setStrokeWidth(28);
        float cenX=getWidth()/2f;
        float cenY=getHeight()/2f;

        canvas.drawLine(getWidth(),cenY,0.6f*cenX,cenY,paint);




        paint.setAntiAlias(true);


        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(3);
        Path mpath=new Path();

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Path path1 = new Path();

        path1.moveTo(30, cenY);

        path1.lineTo(0.6f*cenX, cenY-28);

        path1.lineTo(0.6f*cenX, cenY+28);

        path1.close();

        //根据Path进行绘制，绘制三角形

        canvas.drawPath(path1, paint);
        //canvas.drawCircle(cenX-50,cenY,0.3f*getHeight(),paint);
        //canvas.drawCircle(cenX+50,cenY,0.3f*getHeight(),paint);
    }
}
