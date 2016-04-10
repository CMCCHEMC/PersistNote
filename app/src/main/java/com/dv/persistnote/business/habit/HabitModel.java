package com.dv.persistnote.business.habit;

import com.dv.persistnote.framework.model.GreenDaoManager;

import habit.dao.HabitRecord;
import habit.dao.HabitRecordDao;

/**
 * Created by Hang on 2016/4/11.
 */
public class HabitModel {

    private static HabitModel mInstance;

    private HabitRecordDao mHabitDao;

    public static HabitModel getInstance() {
        if (mInstance == null) {
            mInstance = new HabitModel();
        }
        return mInstance;
    }

    HabitModel() {
        mHabitDao = GreenDaoManager.getInstance().getSession().getHabitRecordDao();
    }


    public void addHabit(String habitId, String habitName, String iconUrl) {
        HabitRecord record = new HabitRecord();
        record.setHabitId(habitId);
        record.setHabitName(habitName);
        record.setIconUrl(iconUrl);
        mHabitDao.insert(record);
    }
}
