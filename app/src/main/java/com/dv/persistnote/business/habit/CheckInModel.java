package com.dv.persistnote.business.habit;

import android.util.Log;

import com.dv.persistnote.base.util.ThreadManager;
import com.dv.persistnote.framework.model.GreenDaoManager;
import com.dv.persistnote.framework.model.IModelObserver;
import com.dv.persistnote.framework.model.ModelId;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import habit.dao.CheckInRecord;
import habit.dao.CheckInRecordDao;


/**
 * Created by Hang on 2016/5/5.
 */
public class CheckInModel {
    private static CheckInModel mInstance;

    private CheckInRecordDao mCheckInDao;

    private List<CheckInRecord> mCheckInList;
    private List<CheckInRecord> mTempList;

    public static CheckInModel getInstance() {
        if (mInstance == null) {
            mInstance = new CheckInModel();
        }
        return mInstance;
    }

    CheckInModel() {
        mCheckInDao = GreenDaoManager.getInstance().getSession().getCheckInRecordDao();
        long start = System.currentTimeMillis();
        mCheckInList = mCheckInDao.loadAll();
        Log.d("xyao","load checkIn dao cost "+(System.currentTimeMillis() - start));
    }

    public boolean checkInRightNow(final long habitId, final IModelObserver observer) {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                CheckInRecord record = new CheckInRecord();
                record.setHabitId(habitId);
                record.setCheckInTimestamp(System.currentTimeMillis());
                record.setCheckInDate(Calendar.getInstance().getTime());
                mCheckInDao.insert(record);
                mTempList = mCheckInDao.loadAll();
            }
        }, new Runnable() {
            @Override
            public void run() {
                mCheckInList = mTempList;
                observer.handleData(ModelId.OnCheckInLoaded, null, null);
            }
        });
        return true;
    }

    public boolean isDayCheckedIn(long habitId, Date date) {
        if(mCheckInList != null) {
            for(CheckInRecord item : mCheckInList) {
//                Log.d("xyao","compareHabit "+item.getHabitId()+" "+habitId);
                if(item.getHabitId() == habitId &&
                        isSameDay(item.getCheckInDate(), date)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isSameDay(Date date, Date sameDate) {
        if (null == date || null == sameDate) {
            return false;
        }
//        Log.d("xyao","compareTime "+date+" "+sameDate);
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(sameDate);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)
                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)
                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {
            return true;
        }

        return false;

    }
}
