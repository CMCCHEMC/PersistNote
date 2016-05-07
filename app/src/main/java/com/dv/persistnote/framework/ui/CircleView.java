package com.dv.persistnote.framework.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by QinZheng on 2016/4/1.
 */
public class CircleView extends View {
    private float mCx;
    private float mCy;
    private float mRadius;
    private Paint mPaint;

    public CircleView(Context context, float cx, float cy, float radius) {
        super(context);
        mCx = cx;
        mCy = cy;
        mRadius = radius;

        // 首先定义一个paint
        mPaint = new Paint();

        // 绘制矩形区域-实心矩形
        // 设置颜色
        mPaint.setColor(Color.BLUE);
        // 设置样式-填充
        mPaint.setStyle(Paint.Style.FILL);
        // 设置抗锯齿
        mPaint.setAntiAlias(true);
    }

    public void setColor(int color) {
        // 设置颜色
        mPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw circle
        canvas.drawCircle(mCx, mCy, mRadius, mPaint);
    }


}
