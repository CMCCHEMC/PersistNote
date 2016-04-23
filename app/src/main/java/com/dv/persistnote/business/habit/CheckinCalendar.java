package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.dv.persistnote.R;
import com.dv.persistnote.framework.ui.common.materialcalendarview.CalendarDay;
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
public class CheckinCalendar extends MaterialCalendarView implements OnDateSelectedListener {
    public CheckinCalendar(Context context) {
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
//                new MySelectorDecorator(this),
//                new HighlightWeekendsDecorator(),
                new OneDayDecorator()
        );
        setTileSize(100);
        String[] weeks = new String[] {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        setWeekDayFormatter(new ArrayWeekDayFormatter(weeks));

        String[] months = new String[] {"01","02","03","04","05","06","07","08","09","10","11","12"};
        setTitleFormatter(new MonthArrayTitleFormatter(months));

        setLeftArrowMask( getResources().getDrawable(R.drawable.mcv_action_previous));
        setRightArrowMask( getResources().getDrawable(R.drawable.mcv_action_next));

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

    }


    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }
}
