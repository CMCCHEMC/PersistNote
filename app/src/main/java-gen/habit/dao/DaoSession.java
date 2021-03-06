package habit.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import habit.dao.HabitRecord;
import habit.dao.CommunityRecord;
import habit.dao.CheckInRecord;

import habit.dao.HabitRecordDao;
import habit.dao.CommunityRecordDao;
import habit.dao.CheckInRecordDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig habitRecordDaoConfig;
    private final DaoConfig communityRecordDaoConfig;
    private final DaoConfig checkInRecordDaoConfig;

    private final HabitRecordDao habitRecordDao;
    private final CommunityRecordDao communityRecordDao;
    private final CheckInRecordDao checkInRecordDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        habitRecordDaoConfig = daoConfigMap.get(HabitRecordDao.class).clone();
        habitRecordDaoConfig.initIdentityScope(type);

        communityRecordDaoConfig = daoConfigMap.get(CommunityRecordDao.class).clone();
        communityRecordDaoConfig.initIdentityScope(type);

        checkInRecordDaoConfig = daoConfigMap.get(CheckInRecordDao.class).clone();
        checkInRecordDaoConfig.initIdentityScope(type);

        habitRecordDao = new HabitRecordDao(habitRecordDaoConfig, this);
        communityRecordDao = new CommunityRecordDao(communityRecordDaoConfig, this);
        checkInRecordDao = new CheckInRecordDao(checkInRecordDaoConfig, this);

        registerDao(HabitRecord.class, habitRecordDao);
        registerDao(CommunityRecord.class, communityRecordDao);
        registerDao(CheckInRecord.class, checkInRecordDao);
    }
    
    public void clear() {
        habitRecordDaoConfig.getIdentityScope().clear();
        communityRecordDaoConfig.getIdentityScope().clear();
        checkInRecordDaoConfig.getIdentityScope().clear();
    }

    public HabitRecordDao getHabitRecordDao() {
        return habitRecordDao;
    }

    public CommunityRecordDao getCommunityRecordDao() {
        return communityRecordDao;
    }

    public CheckInRecordDao getCheckInRecordDao() {
        return checkInRecordDao;
    }

}
