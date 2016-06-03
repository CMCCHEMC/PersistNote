package com.dv.persistnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.business.PhotoPickerScreen;
import com.dv.persistnote.framework.core.ControllerFactory;
import com.dv.persistnote.framework.core.ControllerRegister;
import com.dv.persistnote.framework.core.MsgDef;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.ControllerCenter;


public class PersistNoteActivity extends Activity {

    private BaseEnv mEnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBaseEnv();
        initController();

    }

    /*
    初始化基础框架，包括消息通讯，UI基础框架
     */
    private void initBaseEnv() {
        mEnv = new BaseEnv(this);

        ContextManager.initialize(this);
        ContextManager.setApplicationContext(this.getApplicationContext());
    }

    /*
    初始化Controller框架，包括工厂和消息注册
     */
    private void initController() {
        ControllerCenter center = new ControllerCenter();
        ControllerRegister register = new ControllerRegister(center);
        mEnv.getMsgDispatcher().setControllerCenter(center);
        center.setControllerFactory(new ControllerFactory());
        center.setEnvironment(mEnv);
        register.registerControllers();

        mEnv.getMsgDispatcher().sendMessage(MsgDef.MSG_INIT_ROOTSCREEN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 相机拍照完成后，返回图片路径
        switch (requestCode) {
            case PhotoPickerScreen.ACCOUNT_CONTROLLER:
                mEnv.getMsgDispatcher().sendMessage(MsgDef.MSG_ACCOUNT_CAMERA_RETURN);
                break;
            case PhotoPickerScreen.ROOT_CONTROLLER:
                mEnv.getMsgDispatcher().sendMessage(MsgDef.MSG_ROOT_CAMERA_RETURN);
                break;
            default:
                break;
        }
    }
}
