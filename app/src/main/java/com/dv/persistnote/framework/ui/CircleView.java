package com.dv.persistnote.framework.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Admin2 on 2016/4/1.
 */
public class CircleView extends View {
    private float m_cx;
    private float m_cy;
    private float m_radius;
    private Paint paint;

    public CircleView(Context context, float cx, float cy, float radius) {
        super(context);
        m_cx = cx;
        m_cy = cy;
        m_radius = radius;

        // 首先定义一个paint
        paint = new Paint();

        // 绘制矩形区域-实心矩形
        // 设置颜色
        paint.setColor(Color.BLUE);
        // 设置样式-填充
        paint.setStyle(Paint.Style.FILL);
    }

    public void setColor(int color) {
        // 设置颜色
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw circle
        canvas.drawCircle(m_cx, m_cy, m_radius, paint);
    }


}
