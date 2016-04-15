package com.dv.persistnote.business.habit;

import android.os.Message;
import android.view.KeyEvent;

import com.dv.persistnote.framework.core.AbstractController;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.MsgDef;
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
            mDetailScreen.setHabitId();
            mWindowMgr.pushScreen(mDetailScreen, true);
        }
    }

    @Override
    public void onWindowStateChange(AbstractScreen target, byte stateFlag) {

    }

    @Override
    public boolean onWindowKeyEvent(AbstractScreen target, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        return false;
    }
}