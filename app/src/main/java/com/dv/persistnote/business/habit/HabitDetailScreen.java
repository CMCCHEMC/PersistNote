package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.UICallBacks;

import habit.dao.HabitRecord;

/**
 * Created by Hang on 2016/4/3.
 */
public class HabitDetailScreen extends DefaultScreen{

    private TextView mFakeCalendar;
    private TextView mPersistDuration;
    private CheckInWidget mCheckInWidget;

    private ListView mDetailListView;

    private CommunityDetailAdapter mAdapter;

    public HabitDetailScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
    }

    @Override
    protected void init() {
        super.init();
        mDetailListView = new ListView(getContext());
        mDetailListView.setDivider(null);
        mDetailListView.setCacheColorHint(Color.TRANSPARENT);
        mDetailListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new CommunityDetailAdapter();

        mFakeCalendar = new TextView(getContext());
        mFakeCalendar.setText("假装是日历");
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_text_size_30) *2);
        mFakeCalendar.setLayoutParams(lp);
        mFakeCalendar.setGravity(Gravity.CENTER);
        mFakeCalendar.setBackgroundColor(ResTools.getColor(R.color.default_white));

        mCheckInWidget = new CheckInWidget(getContext());
        lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        mCheckInWidget.setLayoutParams(lp);

        mPersistDuration = new TextView(getContext());
        mPersistDuration.setText("第28天");
        lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.common_text_size_30) * 2);
        mPersistDuration.setLayoutParams(lp);
        mPersistDuration.setGravity(Gravity.CENTER);
        mPersistDuration.setBackgroundColor(ResTools.getColor(R.color.default_white));

        mDetailListView.addHeaderView(mFakeCalendar);
        mDetailListView.addHeaderView(mCheckInWidget);
        mDetailListView.addHeaderView(mPersistDuration);

        mDetailListView.setAdapter(mAdapter);
        setContent(mDetailListView);
    }

    public void setHabitDataById(long habitId) {
        HabitRecord habit = HabitModel.getInstance().getHabitById(habitId);
        if(habit != null) {
            setTitle(habit.getHabitName());
            setPersistCounts(habit.getPersistCount());
        }
    }

    private void setPersistCounts(int counts) {
        mPersistDuration.setText("第"+counts+"天");
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        switch (screenState) {
            case STATE_ON_ATTACH:
                mDetailListView.setSelection(0);
                break;
        }
    }
}
