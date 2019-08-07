package com.eecso.powersystem;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class EEView extends View {
    boolean focused=false;
    int oriental=0;
    int portnum=1;
    int blockLenth=50;
    boolean fanzhuan=false;

    float para_SN=30f;
    float para_UN=10.5f;

    AlertDialog dialog;
    Context mcontext;

    int widthNum=4;
    int heightNum=4;

    public void initConfig()//提供接口
    {
        //提供继承代码

    }
    public CellPosition getRecPosition()
    {
        return new CellPosition((int)(getX()/blockLenth),(int)(getY()/blockLenth),(int)((getX()+getWidth())/blockLenth),(int)((getY()+getHeight())/blockLenth));
    }
    public void setRecPosition(CellPosition cellPosition)
    {
        int x1=cellPosition.X1;
        int y1=cellPosition.Y1;
        int x2=cellPosition.X2;
        int y2=cellPosition.Y2;
        int orien=cellPosition.derection;
        setX(x1*blockLenth);
        setY(y1*blockLenth);
        oriental=orien;

    }
    public void setRecXY(int x,int y)
    {
        setX(x*blockLenth);
        setY(y*blockLenth);

    }
    public Point getRecXY()
    {
        Point point=new Point();
        point.set((int)getX()/blockLenth,(int)getY()/blockLenth);
        return point;

    }
    CirCuit mcircuit;
    public EEView(Context context) {
        super(context);
        mcontext=context;
        init();
        initConfig();
    }
    public EEView(Context context,CirCuit cirCuit) {
        super(context);
        mcontext=context;
        mcircuit=cirCuit;
        init();
        initConfig();
    }

    public EEView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mcontext=context;
        init();
        initConfig();
    }

    public EEView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext=context;
        init();
        initConfig();
    }

    public EEView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mcontext=context;
        init();
        initConfig();
    }

    private void init() {
    }

    int viewWidth=0;
    int viewHeight=0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(fanzhuan)
        { setMeasuredDimension(heightNum*blockLenth,widthNum*blockLenth);
        }
        else
        {
            setMeasuredDimension(widthNum*blockLenth, heightNum*blockLenth);

        }
     }

    float dX=0,dY=0;
    int lastAction;

    private float QuZheng(float input)
    {

        return QuZheng(input,50);
    }
    private float QuZheng(float input,float range)
    {
        float returnvalue=input+(input%range>(range/2)?(range-input%range):(-input%range));
        Log.v("返回值1",String.valueOf(input));
        Log.v("返回值2",String.valueOf(returnvalue));
        if(returnvalue<=0)
            returnvalue=0;
        return returnvalue;
    }

    void rotate()
    {


        oriental=oriental+1;
        fanzhuan=!fanzhuan;
        setLayoutParams(getLayoutParams());


        postInvalidate();
        postCircuitUpdate();

    }
    int lastState=0;//0是点，1是拖
    float lastX=0;
    float lastY=0;
    long DownTime=0;
    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {


            final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            String[]  items;
            items =new String[]{""};

            if(EEView.this instanceof EEGener)
            {
               items =new String[]{"配置参数","旋转90度", "删除原件","额定电压：10.5kV","额定容量：30MV·A","标幺电抗：0.15"};
            }
            else if(EEView.this instanceof EETrans)
            {
                items =new String[]{"配置参数","旋转90度", "删除原件","额定电压：10kV/110kV","额定容量：50MV·A","标幺电抗:0.3"};
            }
            else if(EEView.this instanceof EELoad)
            {
                items =new String[]{"配置参数","旋转90度", "删除原件","额定电压：115kV","额定容量：30MV·A","标幺电抗：0.8"};
            }


            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case 0:

                            dialog.dismiss();
                            break;
                            case 1: rotate();
                            dialog.dismiss();
                            break;
                            case 2: mcircuit.removeComponentByView(EEView.this);

                                dialog.dismiss();
                            break;

                    }
                }
            });
            dialog=builder.create();
            dialog.show();



            Vibrator vibrator = (Vibrator)getContext().getSystemService(getContext().VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {



        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            lastX=getX();
            lastY=getY();
            handler.postDelayed(mLongPressed,500);
        }
        if((event.getAction() == MotionEvent.ACTION_UP)||(getX()!=lastX||getY()!=lastY))
            handler.removeCallbacks(mLongPressed);


        long DetTime;
        switch (event.getAction())
        {


            case MotionEvent.ACTION_MOVE:
                //dXdY是手指相对于view的偏移
                lastState=1;
                float realX=event.getRawX() + dX;
                float realY=event.getRawY()+dY;
                setY(QuZheng(realY));
                setX(QuZheng(realX));


                return true;//只要我处理了，返回ture才会有move

            case MotionEvent.ACTION_DOWN:
                DownTime=System.currentTimeMillis();
                focused=true;
                dX = getX() - event.getRawX();
                dY = getY() - event.getRawY();
                lastX=getX();
                lastY=getY();
                lastState=0;
                postInvalidate();

                return true;
            case MotionEvent.ACTION_UP:


                /**
                DetTime=System.currentTimeMillis()-DownTime;
                focused=false;
                //(getX()==lastX&&getY()==lastY)则没有变
                if((getX()==lastX&&getY()==lastY)&&DetTime<500)
                {

                    oriental=oriental+1;
                    fanzhuan=!fanzhuan;
                    setLayoutParams(getLayoutParams());

                }

                postInvalidate();
                postCircuitUpdate();*/
                focused=false;
                postInvalidate();
                postCircuitUpdate();
                
                return true;
        }


        return false;
    }

    public void drawOutCircle(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.CENTER);

        paint.setStrokeWidth(8);
        float cenX=getWidth()/2f;
        float cenY=getHeight()/2f;


        canvas.drawCircle(cenX,cenY,0.4f*getWidth(),paint);
    }
    public void drawText(String text,float fontSize,Canvas canvas,boolean isNeedCircle)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        float textSize=fontSize;
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        float cenX=getWidth()/2f;
        float cenY=getHeight()/2f;


        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (cenY- top/2 - bottom/2);//基线中间点的y轴计算公式




        paint.setStrokeWidth(10);
        if(isNeedCircle)
            drawOutCircle(canvas);

        canvas.drawText(text,cenX,baseLineY,paint);
    }

    public void drawText(String text,float fontSize,Canvas canvas)
    {
        drawText(text,fontSize,canvas,true);
    }
    int Xwidth=0;
    int Xheight=0;
    @Override
    protected void onDraw(Canvas canvas) {

         Xwidth=widthNum*blockLenth;
         Xheight=heightNum*blockLenth;
         if(oriental%2==1)
         {

             canvas.translate((Xheight-Xwidth)/2f,(Xwidth-Xheight)/2f);
         }

        canvas.rotate(90*oriental,Xwidth/2,Xheight/2);


        Paint paint=new Paint();
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);

        Log.v("测试:宽度",String.valueOf(getWidth()));
        Log.v("测试:高度",String.valueOf(getHeight()));

        Log.v("测试:宽度X",String.valueOf(Xwidth));
        Log.v("测试:高度X",String.valueOf(Xheight));

        int width=Xwidth-1;
        int height=Xheight-1;
        //canvas.drawLine(0,0,width,height,paint);
        //canvas.drawLine(0,height,width,0,paint);

        if(focused)
        {
            paint.setColor(0xFF999999);
            canvas.drawLine(0,0,width,0,paint);
            canvas.drawLine(0,0,0,height,paint);
            canvas.drawLine(0,height,width,height,paint);
            canvas.drawLine(width,0,width,height,paint);
        }

        Paint paint2=new Paint();
        paint2.setStrokeWidth(30);
        paint2.setColor(Color.BLACK);

        switch (portnum)
        {

            case 1:
                canvas.drawCircle(width-5,(height+1) /2,15,paint2);
                break;
            case 2:
                canvas.drawCircle(width-5,(height+1) /2,15,paint2);
                canvas.drawCircle(5,(height+1) /2,15,paint2);
                break;
        }



        //super.onDraw(canvas);
    }
    void postCircuitUpdate()
    {
        CirCuit cirCuit=(CirCuit)findViewById(R.id.CircuitBoard);
        if (cirCuit!=null)
            cirCuit.postInvalidate();
    }
    public class CellPosition
    {

        int X1=0;
        int Y1=0;
        int X2=0;
        int Y2=0;
        int derection=0;
        CellPosition()
        {

        }
        CellPosition(int x1,int y1,int x2,int y2)
        {
            X1=x1;
            X2=x2;
            Y1=y1;
            Y2=y2;
        }
        public boolean IsOverLapped(int x1,int y1,int x2,int y2)
        {
            return x1 < X2 && x2 > X1&& y1 < Y2 && y2 > Y1;

        }
        public boolean IsOverLapped(CellPosition cellPosition)
        {
            return IsOverLapped(cellPosition.X1,cellPosition.Y1,cellPosition.X2,cellPosition.Y2);
        }


    }

    //提供重写，默认是右中端口，一个
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
