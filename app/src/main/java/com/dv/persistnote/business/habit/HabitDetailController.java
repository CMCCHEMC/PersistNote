package com.dv.persistnote.business.habit;

import android.os.Message;
import android.widget.Toast;

import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.core.AbstractController;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.MsgDef;
import com.dv.persistnote.framework.model.ModelId;
import com.dv.persistnote.framework.ui.AbstractScreen;

/**
 * Created by Hang on 2016/4/4.
 */
public class HabitDetailController extends AbstractController{

    HabitDetailScreen mDetailScreen;

    public HabitDetailController(BaseEnv baseEnv) {
        super(baseEnv);
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == MsgDef.MSG_OPEN_HABIT_DETAIL) {
            mDetailScreen = new HabitDetailScreen(mContext, this, (long) msg.obj);
            if(msg.obj instanceof Long) {
                mDetailScreen.setHabitDataById((long) msg.obj);
            }
            mWindowMgr.pushScreen(mDetailScreen, true);
            CommunityModel.getInstance().startLoadData(this, true);
        }
    }

    @Override
    public void onWindowStateChange(AbstractScreen target, byte stateFlag) {

    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        switch (actionId) {
            case ActionId.OnCommunityLoadMore:
                CommunityModel.getInstance().startLoadData(this, false, 5);
                break;
            case ActionId.OnCommunityRefresh:
                CommunityModel.getInstance().startRefresh(this);
                break;
            case ActionId.OnCheckIn:
                if(arg instanceof Long) {
                    CheckInModel.getInstance().checkInRightNow((Long)arg, this);
                }
                break;
            case ActionId.OnCommunityCardShare:
                Toast.makeText(mContext, "菊花分享接这里", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public boolean handleData(int dataId, Object arg, Object result) {
        if(dataId == ModelId.OnCommunityLoaded) {
            mDetailScreen.notifyCommunityDataChange((int)arg);
        } else if (dataId == ModelId.OnCommunityNoMore) {
            mDetailScreen.onNoHistoryData();
        } else if (dataId == ModelId.OnCheckInLoaded) {
            mDetailScreen.notifyCheckInDataChange();
        }
        return super.handleData(dataId, arg, result);
    }
}
