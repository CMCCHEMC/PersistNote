package com.dv.persistnote.business.account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;

import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.base.util.Utilities;
import com.dv.persistnote.business.LoginScreen;
import com.dv.persistnote.business.PhotoPickerScreen;
import com.dv.persistnote.business.RegisterHomeScreen;
import com.dv.persistnote.business.RegisterOKScreen;
import com.dv.persistnote.business.RegisterPasswordScreen;
import com.dv.persistnote.business.RegisterUserInfoScreen;
import com.dv.persistnote.business.ResetPasswordHomeScreen;
import com.dv.persistnote.business.ResetPasswordPasswordScreen;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.business.WelcomeScreen;
import com.dv.persistnote.framework.ui.AbstractScreen;
import com.dv.persistnote.framework.core.AbstractController;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.MsgDef;

import java.io.File;

/**
 * Created by Hang on 2016/3/14.
 */
public class AccountController extends AbstractController{

    private WelcomeScreen mWelcomeScreen;

    private LoginScreen mLoginScreen;

    private RegisterHomeScreen mRegisterHomeScreen;

    private RegisterPasswordScreen mRegisterPasswordScreen;

    private RegisterUserInfoScreen mRegisterUserInfoScreen;

    private RegisterOKScreen mRegisterOKScreen;

    private ResetPasswordHomeScreen mResetPasswordHomeScreen;

    private ResetPasswordPasswordScreen mResetPasswordPasswordScreen;

    private PhotoPickerScreen mPhotoPickerScreen;

    private File mTmpFile;

    public AccountController(BaseEnv baseEnv) {
        super(baseEnv);
    }

    @Override
    public void handleMessage(Message msg) {

        if(msg.what == MsgDef.MSG_SHOW_WELCOME_SCREEN){
            //未登录时显示登陆窗口
            mWelcomeScreen = new WelcomeScreen(mContext,this);
            mWindowMgr.pushScreen(mWelcomeScreen, false);
        }
        if(msg.what == MsgDef.MSG_ACCOUNT_CAMERA_RETURN){
            if(mTmpFile != null) {
                // TODO:进入剪裁页面
            }
        }
    }



    @Override
    public void onWindowStateChange(AbstractScreen target, byte stateFlag) {

    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        switch (actionId) {
            case ActionId.OnLoginClick:
                mLoginScreen = new LoginScreen(mContext, this);
                mWindowMgr.pushScreen(mLoginScreen, true);
                break;
            case ActionId.OnRegisterClick:
                //mPhotoPickerScreen = new PhotoPickerScreen(mContext, this, PhotoPickerScreen.ACCOUNT_CONTROLLER);
                //mWindowMgr.pushScreen(mPhotoPickerScreen, true);
                mRegisterHomeScreen = new RegisterHomeScreen(mContext, this);
                mWindowMgr.pushScreen(mRegisterHomeScreen, true);
                break;
            case ActionId.OnDirectEntryClick:
                mWindowMgr.popScreen(true);
                break;
            case ActionId.CommitLoginClick:
                // TODO: Login function.
                break;
            case ActionId.CommitRegisterHomeClick:
                mRegisterPasswordScreen = new RegisterPasswordScreen(mContext, this);
                mWindowMgr.pushScreen(mRegisterPasswordScreen, true);
                break;
            case ActionId.CommitRegisterPasswordClick:
                mRegisterUserInfoScreen = new RegisterUserInfoScreen(mContext, this);
                mWindowMgr.pushScreen(mRegisterUserInfoScreen, true);
                break;
            case ActionId.CommitRegisterUserInfoClick:
                mRegisterOKScreen = new RegisterOKScreen(mContext, this);
                mWindowMgr.pushScreen(mRegisterOKScreen, true);
                break;
            case ActionId.CommitResetPasswordHomeClick:
                mResetPasswordPasswordScreen = new ResetPasswordPasswordScreen(mContext, this);
                mWindowMgr.pushScreen(mResetPasswordPasswordScreen, true);
                break;
            case ActionId.CommitResetPasswordPasswordClick:
                // TODO: Reset Password Function.
                break;
            case ActionId.OnForgetPasswordClick:
                mResetPasswordHomeScreen = new ResetPasswordHomeScreen(mContext, this);
                mWindowMgr.pushScreen(mResetPasswordHomeScreen, true);
                break;
            case ActionId.OnACCOUNTShowCamera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(ContextManager.getPackageManager()) != null){
                    // 设置系统相机拍照后的输出路径
                    // 创建临时文件
                    mTmpFile = Utilities.createFile(ContextManager.getContext());
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                    ((Activity) ContextManager.getContext()).startActivityForResult(cameraIntent, PhotoPickerScreen.ACCOUNT_CONTROLLER);
                }else{
                    // no camera
                }
                break;
        }
        return false;
    }

}
