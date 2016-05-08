package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.ui.common.materialcalendarview.CalendarDay;
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
    public CheckInCalendar(Context context) {
        super(context);

        setOnDateChangedListener(this);
        setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);

        Calendar calendar = Calendar.getInstance();
        setSelectedDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        setMinimumDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        setMaximumDate(calendar.getTime());

        addDecorators(
                new CheckInDecorator(),
//                new HighlightWeekendsDecorator(),
                new TodayDecorator()
        );
        setTileSizeDp(35);
        String[] weeks = new String[] {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        setWeekDayFormatter(new ArrayWeekDayFormatter(weeks));

        String[] months = new String[] {"01","02","03","04","05","06","07","08","09","10","11","12"};
        setTitleFormatter(new MonthArrayTitleFormatter(months));

        setDirectionEnable(false);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

    }


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


    public class CheckInDecorator implements DayViewDecorator {

        private CalendarDay date;

        public CheckInDecorator() {
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
}
