package com.dv.persistnote.business.habit;

import android.os.Message;
import android.widget.Toast;

import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.business.share.ShareData;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.core.AbstractController;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.MsgDef;
import com.dv.persistnote.framework.model.ModelId;
import com.dv.persistnote.framework.ui.AbstractScreen;

import habit.dao.CommunityRecord;
import habit.dao.HabitRecord;

public class NoteController extends AbstractController{

    private NoteScreen mNoteScreen;

    public NoteController(BaseEnv baseEnv) {
        super(baseEnv);
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == MsgDef.MSG_OPEN_NOTE_SCREEN) {
            pushNoteWindow((Long)msg.obj);
        }
    }

    @Override
    public void onWindowStateChange(AbstractScreen target, byte stateFlag) {

    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        switch (actionId) {
            case ActionId.OnNoteActionButtonClick:
//                mWindowMgr.popScreen(true);
                sendMessage(MsgDef.MSG_SHARE_TO_WX_TIMELINE, 0,0, mNoteScreen.getShareData());
                break;
            case ActionId.OnNotePicSelectClick:
                Toast.makeText(ContextManager.getContext(), "打开图片选择", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    private void pushNoteWindow(long habitId) {
        mNoteScreen = new NoteScreen(mContext, this);
        mNoteScreen.setHabitInfo(HabitModel.getInstance().getHabitById(habitId));
        mWindowMgr.pushScreen(mNoteScreen, true);
    }

    @Override
    public boolean handleData(int dataId, Object arg, Object result) {
        return super.handleData(dataId, arg, result);
    }
}
