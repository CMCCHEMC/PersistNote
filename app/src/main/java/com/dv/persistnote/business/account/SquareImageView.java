package com.dv.persistnote.business.account;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.util.HardwareUtil;
import com.dv.persistnote.base.util.Utilities;

public class SquareImageView extends ImageView {

    Context mContext;
    int mWidth;
    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        int screenWidth = HardwareUtil.getDeviceWidth();
        mWidth = (screenWidth - Utilities.dip2px(ContextManager.getContext(),4))/3;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mWidth);
    }

}
