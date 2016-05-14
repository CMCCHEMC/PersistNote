package com.dv.persistnote.business.habit.calendar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.util.L;
import com.dv.persistnote.business.habit.CheckInModel;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.ui.IUIObserver;
import com.dv.persistnote.framework.ui.common.materialcalendarview.CalendarDay;
import com.dv.persistnote.framework.ui.common.materialcalendarview.CalendarMode;
import com.dv.persistnote.framework.ui.common.materialcalendarview.DayView;
import com.dv.persistnote.framework.ui.common.materialcalendarview.DayViewDecorator;
import com.dv.persistnote.framework.ui.common.materialcalendarview.DayViewFacade;
import com.dv.persistnote.framework.ui.common.materialcalendarview.MaterialCalendarView;
import com.dv.persistnote.framework.ui.common.materialcalendarview.OnDateSelectedListener;
import com.dv.persistnote.framework.ui.common.materialcalendarview.format.ArrayWeekDayFormatter;
import com.dv.persistnote.framework.ui.common.materialcalendarview.format.MonthArrayTitleFormatter;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hang on 2016/4/23.
 */
public class CheckInCalendar extends MaterialCalendarView implements OnDateSelectedListener {

    private String[] weeks = new String[] {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    private String[] months = new String[] {"01","02","03","04","05","06","07","08","09","10","11","12"};

    private int mCurrentHeight = ResTools.getDimenInt(R.dimen.calendar_week_height);
    private ValueAnimator mAnimator;

    private enum State { MONTH, WEEK , ANIMATING}
    private State mCurrentState = State.WEEK;

    public CheckInCalendar(Context context, IUIObserver observer) {
        super(context, observer);

        setOnDateChangedListener(this);
        setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);

        Calendar calendar = Calendar.getInstance();
        setSelectedDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        setMinimumDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JULY, 31);
        setMaximumDate(calendar.getTime());

        addDecorators( new CheckInDecorator(observer), new TodayDecorator() );
        setTileSizeDp(35);
        setCalendarDisplayMode(CalendarMode.WEEKS);
        setWeekDayFormatter(new ArrayWeekDayFormatter(weeks));
        setTitleFormatter(new MonthArrayTitleFormatter(months));

        setDirectionEnable(false);
        getAdapter().setSelectionEnabled(false);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long start = System.currentTimeMillis();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mCurrentHeight);
        L.d(" measure cost "+(System.currentTimeMillis()- start));

        L.d(" measure heigh "+getMeasuredHeight());
    }

    public void toggleState() {
        if(mCurrentState == State.ANIMATING) {
            return;
        }
        final boolean expand = mCurrentState == State.WEEK;
        toggle(expand, new Runnable() {
            @Override
            public void run() {
                if(expand) {
                    mCurrentState = State.MONTH;
                    setCalendarDisplayMode(CalendarMode.MONTHS);
                } else {
                    mCurrentState = State.WEEK;
                    setCalendarDisplayMode(CalendarMode.WEEKS);
                }
                setWeekDayFormatter(new ArrayWeekDayFormatter(weeks));
            }
        });
    }

    private void toggle(boolean expand, final Runnable postTask) {
        mAnimator = ValueAnimator.ofInt(mCurrentHeight, expand ? ResTools.getDimenInt(R.dimen.calendar_month_height) : ResTools.getDimenInt(R.dimen.calendar_week_height));
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentHeight = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                postTask.run();
            }
        });
        mCurrentState = State.ANIMATING;
        mAnimator.start();
    }

    public void ensureCollapse() {
        if(mCurrentState == State.MONTH) {
            toggleState();
        }
    }
}
