package com.dv.persistnote.business.share;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;


import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.framework.core.AbstractController;
import com.dv.persistnote.framework.core.BaseEnv;
import com.dv.persistnote.framework.core.MsgDef;
import com.dv.persistnote.framework.ui.AbstractScreen;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
/**
 * Created by Hang on 2016/5/20.
 */
public class ShareController  extends AbstractController {
    public static String WEB_SITE = "http://yinfan.org";
    public ShareController(BaseEnv baseEnv) {
        super(baseEnv);
    }
    private ShareData mShareData;

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == MsgDef.MSG_OPEN_SHARE_PLATFORM) {
            mShareData = (ShareData) msg.obj;
            showShare(this.mContext, null, true);
        } else if (msg.what == MsgDef.MSG_SHARE_TO_WX_TIMELINE) {
            Toast.makeText(ContextManager.getContext(), "发布到社区", Toast.LENGTH_SHORT).show();
            mShareData = (ShareData) msg.obj;
            showShare(this.mContext, null, true);
        }
    }

    public void showShare(Context context, String platformToShare, boolean showContentEdit) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        if(mShareData.mBitmap != null) {
            oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                @Override
                public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                    if (true || "Wechat".equals(platform.getName()) || "WechatMoments".equals(platform.getName())) {
                        paramsToShare.setShareType(Platform.SHARE_IMAGE);
                        paramsToShare.setImageData(mShareData.mBitmap);
                        // paramsToShare.setImagePath(_picPath);
                    }
                }
            });
        } else {
            oks.setTitle(mShareData.mTitle);
            oks.setTitleUrl(mShareData.mImageUrl);
            oks.setText(mShareData.mContent);
        }
        //oks.setImageUrl(mShareData.mImageUrl); //微信不绕过审核分享链接
        oks.show(context);
    }

    @Override
    public void onWindowStateChange(AbstractScreen target, byte stateFlag) {

    }

    @Override
    public boolean handleAction(int actionId, Object arg, Object result) {
        return false;
    }
}
