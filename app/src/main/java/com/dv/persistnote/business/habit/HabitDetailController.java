package com.dv.persistnote.business.habit;

import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dv.persistnote.base.util.L;
import com.dv.persistnote.base.util.ThreadManager;
import com.dv.persistnote.business.share.ShareData;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.core.AbstractController;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.MsgDef;
import com.dv.persistnote.framework.model.ModelId;
import com.dv.persistnote.framework.ui.AbstractScreen;

import java.util.concurrent.ExecutionException;

import habit.dao.CommunityRecord;
import habit.dao.HabitRecord;

/**
 * Created by Hang on 2016/4/4.
 * 处理所有分享业务的入口控制器
 */
public class HabitDetailController extends AbstractController{

    private HabitDetailScreen mDetailScreen;

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
                if(arg instanceof CommunityRecord) {
                    handleShareClick((CommunityRecord)arg);
                }
                break;
            case ActionId.OnNoteButtonClick:
                Message msg = Message.obtain();
                msg.what = MsgDef.MSG_OPEN_NOTE_SCREEN;
                msg.obj = mDetailScreen.getHabitId();
                mDispatcher.sendMessage(msg);
                break;
        }
        return false;
    }

    private void handleShareClick(final CommunityRecord record) {
        final ShareData shareData = new ShareData();
        HabitRecord habit = HabitModel.getInstance().getHabitById(mDetailScreen.getHabitId());
        shareData.mTitle = "今天是坚持<"+habit.getHabitName()+">的第"+habit.getPersistCount()+"天 ";
        shareData.mContent = record.getContent();
        shareData.mImageUrl = record.getAvatarUrl();
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap myBitmap = Glide.with(mContext)
                            .load(record.getAvatarUrl())
                            .asBitmap() //必须
                            .centerCrop()
                            .into(400, 300)
                            .get();
                    shareData.mBitmap = myBitmap;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                sendMessage(MsgDef.MSG_OPEN_SHARE_PLATFORM, 0,0, shareData);
            }
        });
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
