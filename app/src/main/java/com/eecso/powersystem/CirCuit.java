package com.eecso.powersystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eecso.powersystem.EEView;

import java.util.ArrayList;

public class CirCuit extends RelativeLayout {
    public static final int TYPE_GENER = 562;
    public static final int TYPE_TRANS = 811;
    public static final int TYPE_LOAD = 824;
    public ArrayList<EEView> compoList = new ArrayList();
    Context context;
    int RecWidthNum;
    int RecHeightNum;
    public CirCuit(Context context) {
        super(context);
        this.context=context;
        setWillNotDraw(false);
    }

    public CirCuit(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setWillNotDraw(false);
    }

    public CirCuit(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        setWillNotDraw(false);
    }

    public CirCuit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        setWillNotDraw(false);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v("测试","layout");
        super.onLayout(changed, l, t, r, b);
        postInvalidate();


        final View activityRootView = this;
        activityRootView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                postInvalidate();
            }
        });
    }


    public void removeComponentByView(EEView eeView)
    {
        if(eeView!=null)
        {
                compoList.remove(eeView);
                removeView(eeView);
        }
    }
    public void removeAllComponent()
    {
        for(int i=0;i<compoList.size();i++)
        {
            removeView((View)compoList.get(i));

        }
        compoList.clear();
    }

    public int addComponent(int itemType)
    {
        RecWidthNum=getWidth()/50;
                RecHeightNum=getHeight()/50;
        EEView eeView=new EEView(context,this);
        switch (itemType)
        {
            case TYPE_GENER:
               eeView=new EEGener(context,this);
                break;
            case TYPE_TRANS:
               eeView=new EETrans(context,this);
                break;
            case TYPE_LOAD:
                eeView=new EELoad(context,this);
                break;

        }


        for(int k=0;k<RecHeightNum-eeView.heightNum;k++) {
            for (int j = 0; j < RecWidthNum-eeView.heightNum; j++) {

                boolean flag=true;
                for (int i = 0; i < compoList.size(); i++) {
                    EEView.CellPosition cellPosition = ((EEView) compoList.get(i)).getRecPosition();
                    if (cellPosition.IsOverLapped(j, k, j + eeView.widthNum, k + eeView.heightNum)) {

                        flag=false;
                        break;
                    }
                    else {
                        continue;
                    }

                }
                if(flag)
                {
                    eeView.setRecXY(j, k);
                    compoList.add(eeView);
                    this.addView(eeView);
                    j=RecHeightNum-eeView.heightNum;k=RecHeightNum-eeView.heightNum;

                    return 0;
                }


            }
        }
        Toast.makeText(context,"当前电路图已经没有空间放置该元件了！",Toast.LENGTH_SHORT).show();

        return 0;
    }



    @Override
    protected void onDraw(Canvas canvas) {

    super.onDraw(canvas);


        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        for(float i=0;i<getWidth();i+=50)
        {
            canvas.drawLine(i,0,i,getHeight(),paint);
        }
        for(float i=0;i<getHeight();i+=50)
        {
            canvas.drawLine(0,i,getWidth(),i,paint);
        }

        connectPorts(canvas);

    }

    private void connectPorts(Canvas canvas)
    {
        int compoNum=compoList.size();


        for(int i=0;i<compoNum-1;i++)
        {
            EEView eeView=(EEView)compoList.get(i);
            EEView eeView2=(EEView)compoList.get(i+1);
            if(compoList.get(i).portnum==2)
            {
                connectDots(eeView.getPortPoint().get(1),eeView2.getPortPoint().get(0),canvas);
            }
            else
            {
                connectDots(eeView.getPortPoint().get(0),eeView2.getPortPoint().get(0),canvas);
            }
        }
    }
    private void connectDots(Point point1,Point point2,Canvas canvas)
    {
        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK);
        paint2.setStrokeWidth(8);
        paint2.setAntiAlias(true);


            canvas.drawLine(point1.x*50,point1.y*50,point2.x*50,point2.y*50,paint2);
    }


    class Connection
    {
        int View0=0;
        int View1=0;
        int Port0=0;
        int Port1=0;
        Connection(int view0,int port0,int view1,int port1)
        {
            View0=view0;
            View1=view1;
            Port0=port0;
            Port1=port1;
        }
    }
}
