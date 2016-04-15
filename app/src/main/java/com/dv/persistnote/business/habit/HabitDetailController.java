package com.dv.persistnote.business.habit;

import android.os.Message;

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
            if(mDetailScreen == null) {
                mDetailScreen = new HabitDetailScreen(mContext, this);
            }
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
        if(actionId == ActionId.OnCommunityLoadMore) {
            CommunityModel.getInstance().startLoadData(this, false, 5);
        }
        return false;
    }

    @Override
    public boolean handleData(int dataId, Object arg, Object result) {
        if(dataId == ModelId.OnCommunityLoaded) {
            mDetailScreen.notifyCommunityDataChange();
        } else if (dataId == ModelId.OnCommunityNoMore) {
            mDetailScreen.onNoHistoryData();
        }
        return super.handleData(dataId, arg, result);
    }
}
