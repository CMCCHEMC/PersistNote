package com.dv.persistnote.framework;

import android.content.Context;
import android.view.View;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.ui.AbstractScreen;
import com.dv.persistnote.framework.ui.UICallBacks;

/**
 * Created by Hang on 2016/3/21.
 */
public class DefaultScreen extends AbstractScreen {

    protected DefaultTitleBar mTitleBar;

    public DefaultScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        initTitle();
        setBackgroundColor(ResTools.getColor(R.color.c4));
    }

    private void initTitle() {
        mTitleBar = new DefaultTitleBar(getContext(), mCallBacks);
        mTitleBar.setId(R.id.id_title_bar);
        LayoutParams titleLp = new LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.title_bar_height));
        addView(mTitleBar, titleLp);
    }

    public void setTitle(String title) {
        mTitleBar.setTitle(title);
    }

    public void setContent(View v) {
        LayoutParams contentLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        contentLp.addRule(BELOW, R.id.id_title_bar);
        addView(v, contentLp);
    }

    public void enableTitleBack(boolean enable) {
        if(mTitleBar != null) {
            mTitleBar.enableTitleBack(enable);
        }
    }

}
