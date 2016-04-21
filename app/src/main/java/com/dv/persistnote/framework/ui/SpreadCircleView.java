package com.dv.persistnote.framework.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.WindowManager;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.util.ThreadManager;
import com.dv.persistnote.framework.ActionId;

/**
 * Created by QinZheng on 2016/4/21.
 */
public class SpreadCircleView extends View implements Runnable{
    private float mCx;
    private float mCy;
    private float mRadius;
    private float mAcc;
    private Paint mPaint;
    private UICallBacks mCallBacks;

    public SpreadCircleView(Context context, float cx, float cy, UICallBacks mC) {
        super(context);
        mCx = cx;
        mCy = cy;
        mRadius = 1.0f;
        mAcc = 0.2f;
        // 首先定义一个paint
        mPaint = new Paint();
        mCallBacks = mC;

        // 绘制矩形区域-实心矩形
        // 设置颜色
        mPaint.setColor(ResTools.getColor(R.color.c1));
        // 设置样式-填充
        mPaint.setStyle(Paint.Style.FILL);
        // 设置抗锯齿
        mPaint.setAntiAlias(true);

        ThreadManager.execute(this, new Runnable() {
            @Override
            public void run() {
                mCallBacks.handleAction(ActionId.CommitRegisterUserInfoClick, null, null);
            }
        });

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

    @Override
    public void run() {
        while(true) {
            mRadius = mAcc + mRadius;

            mAcc = mAcc + 0.35f;

            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

            float height = wm.getDefaultDisplay().getHeight()/2 + 300f;

            if (mRadius > height) {
                break;
            }

            postInvalidate();

            try {
                Thread.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
