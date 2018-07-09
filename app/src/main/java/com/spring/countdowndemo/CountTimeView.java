package com.spring.countdowndemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/6/11.
 * 倒计时控件
 */

public class CountTimeView extends View {

    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画圆环的画笔背景色
    private Paint mRingPaintBg;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 圆环背景颜色
    private int mRingBgColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    //进度圆环半径
    private float mProgressRadius;
    // 圆环宽度
    private float mBordWidth;
    //进度圆环宽度
    private float mProgressWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    //文字颜色
    private int mTextColor;
    //文字大小
    private float mTextSize;
    // 总进度
    private long mTotalProgress = 100;
    // 当前进度
    private long mProgress = 100;

    public CountTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    //属性
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CountTimeView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.CountTimeView_radius, 80);
        mBordWidth = typeArray.getDimension(R.styleable.CountTimeView_bordWidth, 10);
        mProgressWidth = typeArray.getDimension(R.styleable.CountTimeView_progressWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.CountTimeView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.CountTimeView_ringColor, 0xFFFFFFFF);
        mRingBgColor = typeArray.getColor(R.styleable.CountTimeView_ringBgColor, 0xFFFFFFFF);
        mTextColor = typeArray.getColor(R.styleable.CountTimeView_text_color, 0xFFFFFFFF);
        mTextSize = typeArray.getDimension(R.styleable.CountTimeView_text_size, 12);
        mTotalProgress = typeArray.getInteger(R.styleable.CountTimeView_total_progress, 10);
        mProgress = typeArray.getInteger(R.styleable.CountTimeView_progress, 10);

        mRingRadius = mRadius + mBordWidth / 2;
        mProgressRadius = mRadius + mProgressWidth / 2;
    }

    //初始化画笔
    private void initVariable() {
        //内圆
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        //外圆弧背景
        mRingPaintBg = new Paint();
        mRingPaintBg.setAntiAlias(true);
        mRingPaintBg.setColor(mRingBgColor);
        mRingPaintBg.setStyle(Paint.Style.STROKE);
        mRingPaintBg.setStrokeWidth(mBordWidth);


        //外圆弧
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mProgressWidth);
        mRingPaint.setStrokeCap(Paint.Cap.SQUARE);//设置线冒样式，有圆 有方

        //中间字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
    }

    //画图
    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        //内圆
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

        //外圆弧背景
        RectF oval1 = new RectF();
        oval1.left = (mXCenter - mRingRadius);
        oval1.top = (mYCenter - mRingRadius);
        oval1.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval1.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
        canvas.drawArc(oval1, 0, 360, false, mRingPaintBg); //圆弧所在的椭圆对象、圆弧的起始角度、圆弧的角度、是否显示半径连线

        //外圆弧
        if (mProgress >= 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mProgressRadius - (mBordWidth - mProgressWidth) / 2);
            oval.top = (mYCenter - mProgressRadius - (mBordWidth - mProgressWidth) / 2);
            oval.right = mProgressRadius * 2 + (mXCenter - mProgressRadius) + (mBordWidth - mProgressWidth) / 2;
            oval.bottom = mProgressRadius * 2 + (mYCenter - mProgressRadius) + (mBordWidth - mProgressWidth) / 2;
            canvas.drawArc(oval, -90, ((float) (mTotalProgress - mProgress) / mTotalProgress) * 360, false, mRingPaint); //

            //字体
            long count = mProgress;
            String txt = String.valueOf(count);
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
        }
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(long totalProgress, long progress) {
        mTotalProgress = totalProgress;
        mProgress = progress;
        postInvalidate();//重绘
    }
}
