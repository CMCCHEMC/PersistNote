package com.dv.persistnote.business;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dv.persistnote.FakeDataHelper;
import com.dv.persistnote.HabitIconHelper;
import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.network.bean.ZHOther;
import com.dv.persistnote.base.network.bean.ZHResult;
import com.dv.persistnote.business.account.AccountModel;

import java.util.List;

import habit.dao.HabitRecord;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Hang on 2016/3/23.
 */
public class HabitItemView extends RelativeLayout {

    private ImageView mIcon;
    private TextView mTitle;
    private TextView mSubTitle;
    private ImageView mCheckIcon;

    public HabitItemView(Context context) {
        super(context);

        LayoutParams lp = new LayoutParams(ResTools.getDimenInt(R.dimen.habit_icon_width), ResTools.getDimenInt(R.dimen.habit_icon_width));
        mIcon = new ImageView(getContext());
        mIcon.setId(R.id.id_icon);
        lp.addRule(ALIGN_PARENT_LEFT);
        lp.addRule(CENTER_VERTICAL);
        lp.rightMargin = 10;
        addView(mIcon, lp);

        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mTitle = new TextView(getContext());
        mTitle.setId(R.id.id_title);
        mTitle.setTextColor(ResTools.getColor(R.color.c2));
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResTools.getDimenInt(R.dimen.h1));
        lp.addRule(RIGHT_OF, R.id.id_icon);
        lp.topMargin = ResTools.getDimenInt(R.dimen.common_margin_top_22);
        addView(mTitle, lp);

        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mSubTitle = new TextView(getContext());
        mSubTitle.setTextColor(ResTools.getColor(R.color.c3));
        lp.addRule(RIGHT_OF, R.id.id_icon);
        lp.addRule(BELOW, R.id.id_title);
        addView(mSubTitle, lp);

        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mCheckIcon = new ImageView(getContext());
        lp.addRule(ALIGN_PARENT_RIGHT);
        lp.addRule(CENTER_VERTICAL);
        addView(mCheckIcon, lp);

        setBackgroundColor(ResTools.getColor(R.color.c4));
        int padding = ResTools.getDimenInt(R.dimen.common_margin_16);
        setPadding(padding, 0, padding, 0);

    }

    public void setChecked(boolean checked) {

    }

    public void setData(Drawable drawable, String title, String subTitle) {
        mIcon.setImageDrawable(drawable);
        mTitle.setText(title);
        mSubTitle.setText(subTitle);
    }

    public void bindData(HabitRecord info) {
        mIcon.setImageDrawable(HabitIconHelper.getHabitIcon((int)info.getHabitId()));
        mTitle.setText(info.getHabitName());
        mSubTitle.setText("已经坚持"+info.getPersistCount()+"天");
    }
}
