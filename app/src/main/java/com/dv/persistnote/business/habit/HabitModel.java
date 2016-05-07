package com.dv.persistnote.business.habit;

import com.dv.persistnote.FakeDataHelper;
import com.dv.persistnote.base.util.SharedPreferencesUtil;
import com.dv.persistnote.base.util.ThreadManager;
import com.dv.persistnote.business.HabitConstDef;
import com.dv.persistnote.framework.model.GreenDaoManager;
import com.dv.persistnote.framework.model.IModelObserver;
import com.dv.persistnote.framework.model.ModelId;
import com.dv.persistnote.framework.model.SettingFlags;

import java.util.List;

import habit.dao.HabitRecord;
import habit.dao.HabitRecordDao;

/**
 * Created by Hang on 2016/4/11.
 */
public class HabitModel {

    private static HabitModel mInstance;

    private HabitRecordDao mHabitDao;

    private List<HabitRecord> mHabitList;

    public static HabitModel getInstance() {
        if (mInstance == null) {
            mInstance = new HabitModel();
        }
        return mInstance;
    }

    HabitModel() {
        mHabitDao = GreenDaoManager.getInstance().getSession().getHabitRecordDao();

//测试数据写入数据库
        if(!SharedPreferencesUtil.getBooleanValue(SettingFlags.FLAG_HAS_INIT_TEST_DB)) {
            SharedPreferencesUtil.saveData(SettingFlags.FLAG_HAS_INIT_TEST_DB, true);
            addHabit(HabitConstDef.ID_BREAKFAST, "吃早餐", 23, "");
            addHabit(HabitConstDef.ID_RUNNING, "跑步", 16, "");
            addHabit(HabitConstDef.ID_PAINTING, "画画", 12, "");
            addHabit(HabitConstDef.ID_SUNDOG, "日狗", 49, "");
        }
    }


    public void addHabit(long habitId, String habitName,int count, String iconUrl) {
        HabitRecord record = new HabitRecord();
        record.setHabitId(habitId);
        record.setHabitName(habitName);
        record.setPersistCount(count);
        record.setIconUrl(iconUrl);
        mHabitDao.insert(record);
    }

    public void requestHabitList(final IModelObserver observer) {
        ThreadManager.post(ThreadManager.THREAD_WORK, new Runnable() {
            @Override
            public void run() {
                mHabitList = mHabitDao.loadAll();
            }
        }, new Runnable() {
            @Override
            public void run() {
                observer.handleData(ModelId.OnHabitListLoaded, null, null);
            }
        });
    }

    public List<HabitRecord> getHabitList() {
        return mHabitList;
    }

    public HabitRecord getHabitById(long habitId) {
        if (mHabitList == null) {
            return null;
        }

        for (HabitRecord habit : mHabitList) {
            if (habit.getHabitId() == habitId) {
                return habit;
            }
        }

        return null;
    }
}
