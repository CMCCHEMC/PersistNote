package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.business.habit.calendar.CheckInCalendar;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.IUIObserver;
import com.dv.persistnote.framework.ui.UICallBacks;
import com.dv.persistnote.framework.ui.common.materialcalendarview.CalendarDay;

import habit.dao.HabitRecord;

/**
 * Created by Hang on 2016/4/3.
 */
public class NoteScreen extends DefaultScreen implements IUIObserver, View.OnClickListener{

    private LinearLayout mContainer;

    private EditText mEditText;

    private ImageView mAddIcon;

    public NoteScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
        setBackgroundColor(ResTools.getColor(R.color.default_grey));
    }

    protected void init() {
        setTitle("记录一下");
        mTitleBar.setActionText("完成");

        mContainer = new LinearLayout(getContext());
        mContainer.setOrientation(LinearLayout.VERTICAL);
        setContent(mContainer);

        mEditText = new EditText(getContext());
        mEditText.setHint("记录一下吧~");
        mEditText.setMaxLines(5);
        mEditText.setGravity(Gravity.TOP);
        mEditText.setBackgroundColor(ResTools.getColor(R.color.c4));
        int padding = ResTools.getDimenInt(R.dimen.common_margin_16);
        mEditText.setPadding(padding,padding,padding,padding);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) ResTools.dpToPx(160));
        lp.topMargin = ResTools.getDimenInt(R.dimen.common_margin_16);
        mContainer.addView(mEditText, lp);

        FrameLayout photoContainer = new FrameLayout(getContext());
        photoContainer.setBackgroundColor(ResTools.getColor(R.color.c4));
        photoContainer.setPadding(padding,padding,padding,padding);
        mAddIcon = new ImageView(getContext());
        mAddIcon.setImageDrawable(ResTools.getDrawable(R.drawable.icon_plus));
        mAddIcon.setOnClickListener(this);
        int iconWidth = (int) ResTools.dpToPx(60);
        photoContainer.addView(mAddIcon, iconWidth, iconWidth);
        lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) ResTools.dpToPx(90));
        lp.topMargin = ResTools.getDimenInt(R.dimen.common_margin_8);
        mContainer.addView(photoContainer, lp);
    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        boolean handle = true;
        switch (actionId) {
            case ActionId.OnCalendarTouchState:
                break;
            default:
                handle = false;
        }
        if(!handle) {
            mCallBacks.handleAction(actionId, arg, result);
        }
        return handle;
    }

    @Override
    public void onClick(View v) {
        if(v == mAddIcon) {
            mCallBacks.handleAction(ActionId.OnNotePicSelectClick, null, null);
        }
    }
}
