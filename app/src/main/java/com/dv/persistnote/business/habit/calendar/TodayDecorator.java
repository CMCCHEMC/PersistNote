package com.dv.persistnote.business.habit.calendar;

import android.graphics.Typeface;
import android.text.style.StyleSpan;

import com.dv.persistnote.framework.ui.common.materialcalendarview.CalendarDay;
import com.dv.persistnote.framework.ui.common.materialcalendarview.DayViewDecorator;
import com.dv.persistnote.framework.ui.common.materialcalendarview.DayViewFacade;
import com.dv.persistnote.framework.ui.common.materialcalendarview.MaterialCalendarView;

import java.util.Date;

/**
 * Created by Hang on 2016/5/15.
 */
public class TodayDecorator implements DayViewDecorator {

    private CalendarDay date;

    public TodayDecorator() {
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.setTextString("今");
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}