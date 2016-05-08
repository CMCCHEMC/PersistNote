package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.ui.IUIObserver;
import com.dv.persistnote.framework.ui.UICallBacks;

/**
 * Created by Hang on 2016/4/5.
 */
public class CheckInWidget extends FrameLayout implements View.OnClickListener {

    public static final int DURATION = 150;
    private IUIObserver mObserver;
    private ImageView mCheckInView;
    private StateListDrawable mCheckInDrawable;

    private boolean mChecked;

    public CheckInWidget(Context context) {
        super(context);
        mCheckInView = new ImageView(getContext());
        mCheckInView.setScaleType(ImageView.ScaleType.FIT_XY);
        LayoutParams lp = new LayoutParams(ResTools.getDimenInt(R.dimen.check_in_widget_width), ResTools.getDimenInt(R.dimen.check_in_widget_width));
        lp.gravity = Gravity.CENTER;
        addView(mCheckInView, lp);

        mCheckInDrawable = new StateListDrawable();
        mCheckInDrawable.addState(new int[] { android.R.attr.state_selected }, ResTools.getDrawable(R.drawable.fake_checkin));
        mCheckInDrawable.addState(new int[] {},  ResTools.getDrawable(R.drawable.fake_uncheckin));
        mCheckInView.setImageDrawable(mCheckInDrawable);
        mCheckInView.setOnClickListener(this);
        setChecked(false, true);
        setBackgroundColor(ResTools.getColor(R.color.c4));
    }

    public void setChecked(boolean checked, boolean anim) {
        mChecked = checked;

        if (mCheckInDrawable != null) {
            mCheckInView.setSelected(checked);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mCheckInView) {
            if(mChecked) {
                Toast.makeText(getContext(), "今天已经打过卡了", Toast.LENGTH_SHORT).show();
            } else {
                setChecked(!mChecked, true);
                mObserver.handleAction(ActionId.OnCheckIn, null, null);
            }
        }
    }

    public void setOnUIObserver(IUIObserver observer) {
        mObserver = observer;
    }
}
