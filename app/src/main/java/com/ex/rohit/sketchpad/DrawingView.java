package com.ex.rohit.sketchpad;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.util.TypedValue;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.ex.rohit.sketchpad.http.HttpCallback;
import com.ex.rohit.sketchpad.http.HttpClient;

import com.ex.rohit.sketchpad.http.ParcelableUtil;
import com.ex.rohit.sketchpad.model.CustomPath;
import com.ex.rohit.sketchpad.model.PathGroup;
import com.ex.rohit.sketchpad.model.PathJson;
import com.google.gson.Gson;

public class DrawingView extends View {

    public String color;

    private CustomPath drawPath;
    private Paint drawPaint,canvasPaint;
    private int paintColor=0xFF6600;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float brushSize,lastBrushSize;
    private boolean erase = false;
    private SharedPreferences sp;
    @SuppressLint("WrongConstant")
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
        sp = context.getSharedPreferences("userinfo",Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
        CreateHandler();
    }

    private void setupDrawing() {

        //get drawing area for interaction

        brushSize= 10;
        lastBrushSize=brushSize;

        drawPath= new CustomPath();
        drawPaint=new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint=new Paint(Paint.DITHER_FLAG);
        color = "#000000";

    }

    private  void CreateHandler(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                final String lastid =  String.valueOf(sp.getInt("last_id",0));
                Log.d("lastid", lastid);
                    HttpClient.getPath(lastid, new HttpCallback<PathGroup>() {
                        @Override
                        public void onSuccess(PathGroup pathGroup) {
                            if (pathGroup != null ) {
                                for (PathJson path : pathGroup.getPathGroup()) {
                                    CustomPath customPath = path.getCustompath();
                                    Paint paint = new Paint();
                                    String remotecolor = path.getColor();
                                    int newcolor = Color.parseColor(remotecolor);
                                    float newSize = path.getBrushSize();
                                    drawPaint.setColor(newcolor);
                                    drawPaint.setStrokeWidth(newSize);
                                    drawCanvas.drawPath(customPath, drawPaint);
                                    invalidate();
                                    drawPaint.setColor(Color.parseColor(color));
                                    drawPaint.setStrokeWidth(brushSize);
//                                    drawPaint.setColor(Color.parseColor(color));
//                                    canvasPaint.setColor(Color.parseColor(color));
                                    sp.edit().putInt("last_id", Integer.parseInt(path.getId())).commit();
                                }

                                Log.d("getpath", "success");
                            }
                        }

                        @Override
                        public void onFail(Exception e) {
                            Log.d("getpath", e.toString());
                        }
                    });
                }
        };
        timer.schedule(timerTask,1000,300);
    }


    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh)
    {
        super.onSizeChanged( w,h,oldw,oldh);
        canvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawCanvas= new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(canvasBitmap,0,0, canvasPaint);
        canvas.drawPath(drawPath,drawPaint);

        //Adding  Border to the custom view
//        Paint paint= new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5.0f);
        //canvas.drawRect(0,0,getWidth(),1225,paint);
        Log.d("draw",drawPaint.toString());

    }

    @Override
    public boolean onTouchEvent(MotionEvent  event)
    {
        //detect user touch
        float touchX=event.getX();
        float touchY=event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX,touchY);
                Log.d("touch",String.valueOf(touchX)+", "+String.valueOf(touchY));
                break;
            case MotionEvent.ACTION_UP:
//                    Gson gson = new Gson();
//                    String tmp = gson.toJson(drawPaint);
//                    Paint customPath = gson.fromJson(tmp,Paint.class);
//                    drawCanvas.drawPath(drawPath,customPath);
                drawCanvas.drawPath(drawPath,drawPaint);
                int last = sp.getInt("last_id",0);
                sp.edit().putInt("last_id", last+1).commit();
                try {
                    HttpClient.sendPath("1",drawPath,this.color,this.brushSize, new HttpCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Log.d("sendSuccess",s);
                        }

                        @Override
                        public void onFail(Exception e) {
                            Log.d("sendFail",e.toString());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    //设置颜色
    /*
    String newColor 颜色名字
     */
    public void setColor(String newColor)
    {
        invalidate();
        this.color = newColor;
        paintColor= Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }


    public void startNew() {
        drawCanvas.drawColor(0,PorterDuff.Mode.CLEAR);
        invalidate();
    }


    public void setBrushSize(float newSize)
    {
        float pixelSize= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,newSize,getResources().getDisplayMetrics());
        brushSize=pixelSize;
        drawPaint.setStrokeWidth(brushSize);

    }

    //设置最后笔刷大小和得到笔刷大小
    public void setLastBrushSize(float lastSize)
    {
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize()
    {
        return lastBrushSize;
    }

    //橡皮擦
    public void setErase(boolean isErase)
    {
        //set erase true or false
        erase=isErase;
        if(erase)
        {
            drawPaint.setColor(Color.WHITE);
            this.color = "#ffffff";
        }
        else drawPaint.setXfermode(null);
    }

}