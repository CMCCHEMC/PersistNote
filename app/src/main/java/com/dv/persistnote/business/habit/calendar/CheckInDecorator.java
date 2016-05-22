package com.dv.persistnote.business.habit.calendar;

import android.os.Message;
import android.text.style.ForegroundColorSpan;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.business.habit.CheckInModel;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.ui.IUIObserver;
import com.dv.persistnote.framework.ui.common.materialcalendarview.CalendarDay;
import com.dv.persistnote.framework.ui.common.materialcalendarview.DayView;
import com.dv.persistnote.framework.ui.common.materialcalendarview.DayViewDecorator;
import com.dv.persistnote.framework.ui.common.materialcalendarview.DayViewFacade;

import java.util.Date;

/**
 * Created by Hang on 2016/5/15.
 */
public class CheckInDecorator implements DayViewDecorator {

    private CalendarDay date;

    IUIObserver mObserver;

    public CheckInDecorator(IUIObserver observer) {
        mObserver = observer;
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Message msg = Message.obtain();
        mObserver.handleAction(ActionId.GetHabitId, null, msg);
        long habitId = (long) msg.obj;
        boolean result =  day != null && CheckInModel.getInstance().isDayCheckedIn(habitId, day.getDate());
        return result;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(ResTools.getColor(R.color.c4)));
        view.setBackgroundDrawable(DayView.generateCircleDrawable(ResTools.getColor(R.color.c1)));
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}