package com.dv.persistnote.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

/**
 * Created by Hang on 2016/3/21.
 */
public class ResTools {

    public static Drawable getDrawable(int drawableId) {
        return ContextManager.getResources().getDrawable(drawableId);
    }

    public static Bitmap getBitmap(int drawableId) {
        return BitmapFactory.decodeResource(ContextManager.getResources(), drawableId);
    }

    public static int getDimenInt(int id) {
        return (int) ContextManager.getResources().getDimension(id);
    }

    public static int getColor(int id) {
        return ContextManager.getResources().getColor(id);
    }

    public static String getString(int id) {
        return ContextManager.getResources().getString(id);
    }

    /**
     * 只用于一些一次性的不规则边距，而基本控件，标注间距等不应该通过这个方法去拿, 切勿滥用
     * @param dips
     * @return
     */
    public static float dpToPx(float dips) {
        Context context = ContextManager.getContext();
        return dips * context.getResources().getDisplayMetrics().density + 0.5f;
    }
}
