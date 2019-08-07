package com.eecso.powersystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.ArrayList;

public class EETrans extends EEView {


    public EETrans(Context context) {
        super(context);
    }
    public EETrans(Context context,CirCuit cirCuit) {
        super(context,cirCuit);
    }

    public EETrans(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EETrans(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EETrans(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initConfig() {
        portnum=2;
        widthNum=5;
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

        paint.setStrokeWidth(8);
        float cenX=Xwidth/2f;
        float cenY=Xheight/2f;

        canvas.drawCircle(cenX-50,cenY,0.3f*Xheight,paint);
        canvas.drawCircle(cenX+50,cenY,0.3f*Xheight,paint);
    }


    @Override
    public ArrayList<Point> getPortPoint() {


        Point point = new Point(getRecXY());
        if(oriental%4==0)
        {
            point.x+=widthNum;
            point.y+=heightNum/2;
        }
        else if(oriental%4==1)
        {
            point.x+=heightNum/2;
            point.y+=widthNum;
        }
        else if(oriental%4==2)
        {
            point.y+=heightNum/2;
        }
        else if(oriental%4==3)
        {
            point.x+=widthNum/2;
        }

        Point point2 = new Point(getRecXY());
        if(oriental%4==0)
        {
            point2.x+=0;
            point2.y+=heightNum/2;
        }
        else if(oriental%4==1)
        {
            point2.x+=heightNum/2;
            point2.y+=0;
        }
        else if(oriental%4==2)
        {
            point2.y+=heightNum/2;
            point2.x+=widthNum;
        }
        else if(oriental%4==3)
        {
            point2.x+=widthNum/2;
            point2.y+=heightNum+1;
        }

        ArrayList<Point> arrayList=new ArrayList();

        arrayList.add(point2);
        arrayList.add(point);
        return arrayList;
    }
}
