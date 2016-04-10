package com.dv.persistnote.business;

import android.app.Activity;
import android.os.Message;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.widget.Toast;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.network.TestServiceInterface;
import com.dv.persistnote.base.network.bean.Result;
import com.dv.persistnote.base.network.bean.TransResult;
import com.dv.persistnote.base.network.bean.ZHOther;
import com.dv.persistnote.base.network.bean.ZHResult;
import com.dv.persistnote.business.account.AccountModel;
import com.dv.persistnote.business.habit.HabitModel;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.core.AbstractController;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.MsgDef;
import com.dv.persistnote.framework.ui.AbstractScreen;
import com.dv.persistnote.framework.ui.AbstractTabContentView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Hang on 2016/3/13.
 * 最底部的窗口，用于承载各个Tab
 */
public class RootController extends AbstractController{

    private RootScreen mRootScreen;
    private SparseArray<AbstractTabContentView> mTabViews = new SparseArray<AbstractTabContentView>();

    private long mClickBackFirstTime = 0;

    public RootController(BaseEnv baseEnv) {
        super(baseEnv);
    }
    @Override
    public void handleMessage(Message msg) {
        if(msg.what == MsgDef.MSG_INIT_ROOTSCREEN) {
            mRootScreen = new RootScreen(mContext, this);
            mWindowMgr.createWindowStack(mRootScreen);

            checkLoginState();
        }
    }

    private void checkLoginState() {
        mDispatcher.sendMessage(MsgDef.MSG_SHOW_WELCOME_SCREEN);
    }

    @Override
    public void onWindowStateChange(AbstractScreen target, byte stateFlag) {

    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        switch (actionId) {
            case ActionId.OnHabitItemClick:
//                handleHabitClick();
                mDispatcher.sendMessage(MsgDef.MSG_OPEN_HABIT_DETAIL);
                break;
        }
        return false;
    }

    private void handleHabitClick() {
        AccountModel.getInstance().testJHRequest("NetworkTest",
                new Callback<ZHResult>() {
                    @Override
                    public void success(ZHResult result, Response response) {
                        try {
                            //网络接口测试代码
                            int count = result.getOthers().size();
                            int index = (int) (System.currentTimeMillis() % count);
                            mRootScreen.setCheckInText(result.getOthers().get(index).getName());
                        }catch (Exception e) {
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    @Override
    public boolean onWindowKeyEvent(AbstractScreen target, int keyCode, KeyEvent event) {
        boolean retVal = false;
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (!onWindowBackKeyEvent()) {
                if (ContextManager.getContext() instanceof Activity) {
                    Activity activity = (Activity) ContextManager.getContext();
                    long secondTime = System.currentTimeMillis();
                    if (secondTime - mClickBackFirstTime > 2000) {
                        Toast.makeText(activity, ResTools.getString(R.string.more_click_back),Toast.LENGTH_SHORT).show();
                        mClickBackFirstTime = secondTime;
                        return true;
                    } else {
                        activity.finish();
                    }
                }
            }
            retVal = true;
        }
        return retVal;
    }

}
