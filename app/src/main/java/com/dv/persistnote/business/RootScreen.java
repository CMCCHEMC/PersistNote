package com.dv.persistnote.business;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.business.habit.HabitModel;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.DefaultScreen;
import com.dv.persistnote.framework.ui.AbstractScreen;
import com.dv.persistnote.framework.ui.UICallBacks;

import java.util.List;

import habit.dao.HabitRecord;

/**
 * Created by QinZheng on 2016/6/3.
*/
public class RootScreen extends AbstractScreen {

    protected RootTitleBar mTitleBar;
    private LinearLayout mContainer;

    public RootScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        initTitle();
        init();
        setBackgroundColor(ResTools.getColor(R.color.default_grey));
    }

    private void initTitle() {
        mTitleBar = new RootTitleBar(getContext(), mCallBacks);
        mTitleBar.setId(R.id.id_title_bar);
        LayoutParams titleLp = new LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.title_bar_height));
        addView(mTitleBar, titleLp);
        setTitle(ResTools.getString(R.string.app_name));
    }

    public void setTitle(String title) {
        mTitleBar.setTitle(title);
    }

    public void setAvatar(String path) {
        mTitleBar.setAvatar(path);
    }

    protected void init() {
        mContainer = new LinearLayout(getContext());
        mContainer.setOrientation(LinearLayout.VERTICAL);
        LayoutParams contentLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        contentLp.addRule(BELOW, R.id.id_title_bar);
        addView(mContainer, contentLp);
    }

    public void updateData() {
        mContainer.removeAllViews();

        List<HabitRecord> habitInfos = HabitModel.getInstance().getHabitList();

        if (habitInfos == null) {
            return;
        }

        for (final HabitRecord info : habitInfos) {
            final HabitItemView itemView = new HabitItemView(getContext());
            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ResTools.getDimenInt(R.dimen.habit_item_height));
            lp.topMargin = ResTools.getDimenInt(R.dimen.habit_item_margin_top);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallBacks.handleAction(ActionId.OnHabitItemClick, info.getHabitId() , null);
                }
            });

            itemView.bindData(info);
            mContainer.addView(itemView, lp);
        }

    }

    public void setCheckInText(String checkInText) {
        //网络接口的测试返回结果
        setTitle(checkInText);
    }


    public void notifyDataChange() {
        updateData();
    }
}
