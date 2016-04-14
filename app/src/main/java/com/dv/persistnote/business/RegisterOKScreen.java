package com.dv.persistnote.business;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.ui.AbstractScreen;
import com.dv.persistnote.framework.ui.UICallBacks;


/**
 * Created by QinZheng on 2016/4/06.
 */
public class RegisterOKScreen extends AbstractScreen{

    private RelativeLayout mContainer;

    private ImageView mIvRegisterOK;

    private TextView mTvRegisterOK;

    public RegisterOKScreen(Context context, UICallBacks callBacks) {
        super(context, callBacks);
        init();
    }

    private void init() {
        mContainer = new RelativeLayout(getContext());
        mContainer.setBackgroundColor(ResTools.getColor(R.color.c1));
        LayoutParams contentLp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        addView(mContainer, contentLp);

        mIvRegisterOK = new ImageView(getContext());
        mIvRegisterOK.setId(R.id.register_ok_iv);
        mIvRegisterOK.setImageDrawable(ResTools.getDrawable(R.drawable.rignt));

        LayoutParams lp1 = new LayoutParams(ResTools.getDimenInt(R.dimen.register_ok_iv_width),
                ResTools.getDimenInt(R.dimen.register_ok_iv_height));
        lp1.addRule(CENTER_HORIZONTAL);
        lp1.topMargin = ResTools.getDimenInt(R.dimen.register_ok_iv_margin_top);

        mContainer.addView(mIvRegisterOK, lp1);

        mTvRegisterOK = new TextView(getContext());
        mTvRegisterOK.setId(R.id.register_ok_tv);
        mTvRegisterOK.setTextColor(ResTools.getColor(R.color.c4));
        mTvRegisterOK.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.register_ok_tv_text_size));
        mTvRegisterOK.setText(ResTools.getString(R.string.register_ok_tv));

        LayoutParams lp2 = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp2.addRule(CENTER_HORIZONTAL);
        lp2.addRule(RelativeLayout.BELOW, R.id.register_ok_iv);
        lp2.topMargin = ResTools.getDimenInt(R.dimen.register_ok_tv_margin_top);

        mContainer.addView(mTvRegisterOK, lp2);

    }

}
