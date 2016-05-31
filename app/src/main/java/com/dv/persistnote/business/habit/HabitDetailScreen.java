package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
public class HabitDetailScreen extends DefaultScreen implements IUIObserver{

    private CheckInCalendar mCalendar;
    private TextView mPersistDuration;
    private TextView mNoteButton;
    private CheckInWidget mCheckInWidget;
    private TextView mFooter;
    private long mHabitId;

    private ListView mDetailListView;
    private SwipeRefreshLayout mRefreshLayout;

    private CommunityDetailAdapter mAdapter;

    public HabitDetailScreen(Context context, UICallBacks callBacks, long habitId) {
        super(context, callBacks);
        mHabitId = habitId;
        init();
        setBackgroundColor(ResTools.getColor(R.color.default_grey));
    }

    protected void init() {
        mDetailListView = new ListView(getContext());
        mDetailListView.setDivider(null);
        mDetailListView.setCacheColorHint(Color.TRANSPARENT);
        mDetailListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mDetailListView.setOnScrollListener(createScrollListener());
        mAdapter = new CommunityDetailAdapter(this);

        mRefreshLayout = new SwipeRefreshLayout(getContext());
        mRefreshLayout.addView(mDetailListView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mRefreshLayout.setColorSchemeColors(ResTools.getColor(R.color.c1));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCallBacks.handleAction(ActionId.OnCommunityRefresh, null, null);
            }
        });

        configHeader();
        configAutoLoadMore();
        mDetailListView.setAdapter(mAdapter);
        setContent(mRefreshLayout);
    }

    private void configHeader() {
        mCalendar = new CheckInCalendar(getContext(), this);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        mCalendar.setLayoutParams(lp);
        mCalendar.setBackgroundColor(ResTools.getColor(R.color.c4));

        mCheckInWidget = new CheckInWidget(getContext());
        lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        mCheckInWidget.setLayoutParams(lp);
        mCheckInWidget.setOnUIObserver(this);

        View banner = createBannerContainer();

        TextView communityTitle = new TextView(getContext());
        communityTitle.setText(ResTools.getString(R.string.community_title));
        communityTitle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        int margin = ResTools.getDimenInt(R.dimen.common_margin_16);
        communityTitle.setPadding(ResTools.getDimenInt(R.dimen.common_margin_16), margin, 0, margin);
        communityTitle.setTextColor(ResTools.getColor(R.color.c2));

        mDetailListView.addHeaderView(mCalendar);
        mDetailListView.addHeaderView(mCheckInWidget);
        mDetailListView.addHeaderView(banner);
        mDetailListView.addHeaderView(communityTitle);
    }

    /**
     * 包括天数和点击记录的按钮
     * @return
     */
    private View createBannerContainer() {
        LinearLayout bannerContainer = new LinearLayout(getContext());
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.calendar_height) * 2);
        bannerContainer.setLayoutParams(lp);
        bannerContainer.setBackgroundColor(ResTools.getColor(R.color.c4));
        bannerContainer.setGravity(Gravity.CENTER);
        bannerContainer.setPadding((int) ResTools.dpToPx(70), 0, (int) ResTools.dpToPx(70), 0);

        View divider = new View(getContext());
        divider.setBackgroundColor(ResTools.getColor(R.color.c3));

        LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        childLp.weight = 1;
        int expandPadding = (int) ResTools.dpToPx(10);
        mPersistDuration = new TextView(getContext());
        mPersistDuration.setGravity(Gravity.CENTER);
        mPersistDuration.setPadding(0, expandPadding, 0, expandPadding);
        mPersistDuration.setTextColor(ResTools.getColor(R.color.c6));

        mNoteButton = new TextView(getContext());
        mNoteButton.setText("记录一下");
        mNoteButton.setPadding(0, expandPadding, 0, expandPadding);
        mNoteButton.setGravity(Gravity.CENTER);
        mNoteButton.setTextColor(ResTools.getColor(R.color.c6));
        mNoteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBacks.handleAction(ActionId.OnNoteButtonClick, null, null);
            }
        });

        bannerContainer.addView(mPersistDuration, childLp);
        bannerContainer.addView(divider, 1, (int) ResTools.dpToPx(20));
        bannerContainer.addView(mNoteButton, childLp);
        return bannerContainer;
    }

    private void configAutoLoadMore() {

        mFooter = new TextView(getContext());
        mFooter.setText("正在加载..");
        mFooter.setGravity(Gravity.CENTER);
        mFooter.setTextColor(ResTools.getColor(R.color.c3));
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                ResTools.getDimenInt(R.dimen.calendar_height) * 2);
        mFooter.setLayoutParams(lp);
        mDetailListView.addFooterView(mFooter);

    }

    public void setHabitDataById(long habitId) {
        HabitRecord habit = HabitModel.getInstance().getHabitById(habitId);
        if(habit != null) {
            setTitle(habit.getHabitName());
            setPersistCounts(habit.getPersistCount());
        }
        mHabitId = habitId;
        updateCheckInWidget();
    }

    private void updateCheckInWidget() {
        boolean checkedToday = CheckInModel.getInstance().isDayCheckedIn(mHabitId, CalendarDay.today().getDate());
        mCheckInWidget.setChecked(checkedToday ,false);
    }

    private void setPersistCounts(int counts) {
        mPersistDuration.setText("第" + counts + "天");
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

    public void notifyCommunityDataChange(int type) {
        mAdapter.notifyDataSetChanged();
        if(type == CommunityModel.TYPE_NEW) {
            mRefreshLayout.setRefreshing(false);
        } else if (type == CommunityModel.TYPE_ERROR) {
            mRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(),"网络不给力", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNoHistoryData() {
        mFooter.setText("sb 别拉了");
    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        boolean handle = true;
        switch (actionId) {
            case ActionId.OnCalendarTouchState:
                int state = (Integer)arg;
                boolean enable = state == MotionEvent.ACTION_UP || state == MotionEvent.ACTION_CANCEL;
                mRefreshLayout.setEnabled(enable);
                mDetailListView.setEnabled(enable);
                break;
            case ActionId.OnCheckIn:
                mCallBacks.handleAction(actionId, mHabitId,result);
                break;
            case ActionId.GetHabitId:
                ((Message)result).obj = mHabitId;
                break;
            case ActionId.OnPagerClick:
                mCalendar.toggleState();
                break;
            default:
                handle = false;
        }
        if(!handle) {
            mCallBacks.handleAction(actionId, arg, result);
        }
        return handle;
    }

    public void notifyCheckInDataChange() {
        mCalendar.invalidateDecorators();
    }

    public AbsListView.OnScrollListener createScrollListener() {
        AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                //向后加载更多
                int lastVisibleItem = mDetailListView.getLastVisiblePosition();
                int totalItemCount = mAdapter.getCount();
                boolean lastItemVisible = totalItemCount > 0 && (lastVisibleItem >= totalItemCount - 1);
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisible) {
                    mCallBacks.handleAction(ActionId.OnCommunityLoadMore, null, null);
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //向下收起月面板
                if (mDetailListView.getFirstVisiblePosition() == 0
                        && mDetailListView.getChildCount() > 0
                        && mDetailListView.getChildAt(0).getTop() < -50) {
                    mCalendar.ensureCollapse();
                }

            }
        };
        return scrollListener;
    }

    public long getHabitId() {
        return mHabitId;
    }
}
