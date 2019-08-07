package com.eecso.powersystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.ArrayList;

public class EEGener extends EEView {

    public EEGener(Context context) {
        super(context);
    }
    public EEGener(Context context,CirCuit cirCuit) {
        super(context,cirCuit);
    }
    public EEGener(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EEGener(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EEGener(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void initConfig() {
        portnum=1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //之前画的就不怕旋转
        drawText("G",0.5f*getWidth(),canvas);
        super.onDraw(canvas);
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
        ArrayList<Point> arrayList=new ArrayList();
        arrayList.add(point);
        return arrayList;
    }
}
