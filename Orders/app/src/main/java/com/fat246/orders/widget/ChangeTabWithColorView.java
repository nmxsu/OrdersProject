package com.fat246.orders.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.fat246.orders.R;

/**
 * Created by Administrator on 2016/2/4.
 */
public class ChangeTabWithColorView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;

    //图标
    private Bitmap mIconBitmap;

    //个性化的颜色
    private int mColor=0xFF45C01A;

    //文字的颜色
    private int mTitleColor=0xFF333333;

    //图标下方显示的文字
    private String mTitle="单单";

    //显示文字的大小
    private int mTitleSize=(int ) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,10,
            getResources().getDisplayMetrics());

    //图标Icon的绘制绘制范围
    private Rect mIconRect;

    //用于绘制文字
    private Paint mTitlePaint;
    private Rect mTitleBound=new Rect();

    //透明度 0-1.0f
    private float mAlpha=0f;

    public ChangeTabWithColorView(Context context) {
        super(context);

        init(context, null);
    }

    public ChangeTabWithColorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context,attrs);
    }

    public ChangeTabWithColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    //初始化
    private void init(Context context,AttributeSet attrs){

        //首先获得自定义属性
        handleAttrs(context,attrs);

        mTitlePaint=new Paint();
        mTitlePaint.setTextSize(mTitleSize);
        mTitlePaint.setColor(0xff555555);

        //得到绘制文字的范围
        mTitlePaint.getTextBounds(mTitle, 0, mTitle.length(), mTitleBound);
    }

    //处理自定义属性
    private void handleAttrs(Context context,AttributeSet attrs){

        //获得TypedArray
        TypedArray ta=context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChangeTabWithColorView,0,0);

        //由于TypedArray 是共享资源  所以得及时释放
        try {

            //获得图标
            mIconBitmap=BitmapFactory.decodeResource(getResources(),
                    ta.getResourceId(R.styleable.ChangeTabWithColorView_ct_icon, R.drawable.action_all_orders));



            //获得颜色定制
            mColor=ta.getColor(R.styleable.ChangeTabWithColorView_ct_color, 0xFF45C01A);

            //获得显示的文字
            mTitle=ta.getString(R.styleable.ChangeTabWithColorView_ct_text);


            //获得显示文字的大小
            mTitleSize=(int )ta.getDimension(R.styleable.ChangeTabWithColorView_ct_text_size,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,10,
                            getResources().getDisplayMetrics()));

            //获得文字的颜色
            mTitleColor=ta.getColor(R.styleable.ChangeTabWithColorView_ct_text_color,0xFF333333);


        } catch (Exception e){
            e.printStackTrace();
        } finally{
            ta.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获得Icon的绘制范围
        int bitmapWidth=Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),
                getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-mTitleBound.height());

        int left=getMeasuredWidth()/2-bitmapWidth/2;
        int top=getMeasuredHeight()/2-bitmapWidth/2;

        //做一个向上的调整
        top-=bitmapWidth/6;

        //设置Icon的绘制范围
        mIconRect=new Rect(left,top,left+bitmapWidth,top+bitmapWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int alpha=(int )Math.ceil(255*mAlpha);
        canvas.drawBitmap(mIconBitmap,null,mIconRect,null);

        setupTargetBitmap(alpha);

        drawSourceTitle(canvas,alpha);
        drawTargetTitle(canvas,alpha);

        canvas.drawBitmap(mBitmap,0,0,null);
    }

    private void setupTargetBitmap(int alpha){

        //绘制 src dst
        mBitmap=Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(mBitmap);
        mPaint=new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    private void drawSourceTitle(Canvas canvas,int alpha){

        //没有变化前的Title
        mTitlePaint.setTextSize(mTitleSize);
        mTitlePaint.setColor(mTitleColor);
        mTitlePaint.setAlpha(255 - alpha);
        canvas.drawText(mTitle, mIconRect.left + mIconRect.width() / 2 - mTitleBound.width() / 2,
                mIconRect.bottom + mTitleBound.height(), mTitlePaint);
    }

    private void drawTargetTitle(Canvas canvas,int alpha){

        //在渐变的Title
        mTitlePaint.setColor(mColor);
        mTitlePaint.setAlpha(alpha);
        canvas.drawText(mTitle, mIconRect.left + mIconRect.width() / 2 - mTitleBound.width() / 2,
                mIconRect.bottom + mTitleBound.height(), mTitlePaint);
    }

    //公布设置透明的方法
    public void setIconAlpha(float alpha){
        this.mAlpha=alpha;

        //重绘
        invalidateView();
    }

    private void invalidateView(){

        if (Looper.getMainLooper()==Looper.myLooper()){

            invalidate();
        }else {

            postInvalidate();
        }
    }
}
