package com.dv.persistnote.business.share;

import android.os.Message;
import android.widget.Toast;

import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.framework.core.AbstractController;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.MsgDef;
import com.dv.persistnote.framework.ui.AbstractScreen;

/**
 * Created by Hang on 2016/5/20.
 */
public class ShareController  extends AbstractController {

    public ShareController(BaseEnv baseEnv) {
        super(baseEnv);
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == MsgDef.MSG_OPEN_SHARE_PLATFORM) {
            ShareData shareData = (ShareData)msg.obj;
            Toast.makeText(mContext, "菊花分享接这里\n"+shareData.mTitle, Toast.LENGTH_SHORT).show();
        } else if (msg.what == MsgDef.MSG_SHARE_TO_WX_TIMELINE) {
            Toast.makeText(ContextManager.getContext(), "发布到社区", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWindowStateChange(AbstractScreen target, byte stateFlag) {

    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        return false;
    }
}
