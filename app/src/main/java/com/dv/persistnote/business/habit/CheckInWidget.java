package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;

/**
 * Created by Hang on 2016/4/5.
 */
public class CheckInWidget extends FrameLayout implements View.OnClickListener {

    public static final int DURATION = 150;

    private ImageView mCheckInView;
    private TransitionDrawable mCheckInDrawable;

    private boolean mChecked;

    public CheckInWidget(Context context) {
        super(context);

        mCheckInView = new ImageView(getContext());
        mCheckInView.setScaleType(ImageView.ScaleType.FIT_XY);
        LayoutParams lp = new LayoutParams(ResTools.getDimenInt(R.dimen.check_in_widget_width), ResTools.getDimenInt(R.dimen.check_in_widget_width));
        lp.gravity = Gravity.CENTER;
        addView(mCheckInView, lp);

        mCheckInDrawable = new TransitionDrawable(
                new Drawable[]{
                        ResTools.getDrawable(R.drawable.fake_checkin),
                        ResTools.getDrawable(R.drawable.fake_uncheckin)
                });
        mCheckInView.setImageDrawable(mCheckInDrawable);
        mCheckInView.setOnClickListener(this);

        setBackgroundColor(ResTools.getColor(R.color.default_white));
    }

    public void setChecked(boolean checked, boolean anim) {
        mChecked = checked;

        if (anim && mCheckInDrawable != null) {
            if (checked) {
                mCheckInDrawable.startTransition(DURATION);
            } else {
                mCheckInDrawable.reverseTransition(DURATION);
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == mCheckInView) {
            setChecked(!mChecked, true);
        }
    }
}
