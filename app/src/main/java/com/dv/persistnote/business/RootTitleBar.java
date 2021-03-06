package com.dv.persistnote.business;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dv.persistnote.R;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.ui.UICallBacks;
import com.dv.persistnote.framework.ui.common.GlideCircleTransform;

/**
 * Created by QinZheng on 2016/6/3.
 */
public class RootTitleBar extends RelativeLayout implements View.OnClickListener {

    private ImageView mAvatarButton;
    private TextView mTitleText;
    private GlideCircleTransform mGlideCircleTransform;

    private UICallBacks mCallback;

    public RootTitleBar(Context context, UICallBacks callback) {
        super(context);
        mCallback = callback;

        mGlideCircleTransform = new GlideCircleTransform(getContext());
        mAvatarButton = new ImageView(getContext());
        mAvatarButton.setOnClickListener(this);
        Glide.with(getContext()).load(R.drawable.fake_card_image)
                .transform(mGlideCircleTransform)
                .into(mAvatarButton);

        LayoutParams lp = new LayoutParams((int)ResTools.dpToPx(30), (int)ResTools.dpToPx(30));
        lp.addRule(ALIGN_PARENT_LEFT);
        lp.addRule(CENTER_VERTICAL);
        addView(mAvatarButton, lp);

        mTitleText = new TextView(getContext());
        mTitleText.setTextColor(ResTools.getColor(R.color.c2));
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResTools.getDimenInt(R.dimen.title_bar_text));
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_IN_PARENT);
        addView(mTitleText, lp);

        setBackgroundColor(ResTools.getColor(R.color.c4));
        int padding = ResTools.getDimenInt(R.dimen.common_margin_16);
        setPadding(padding, 0, padding, 0);
    }

    public void setTitle(String title) {
        mTitleText.setText(title);
    }

    @Override
    public void onClick(View view) {
        if (view == mAvatarButton) {
            mCallback.handleAction(ActionId.OnRootShowPhotoPicker, null, null);
        }
    }

    public void setAvatar(String path) {
        Glide.with(getContext()).load(path)
                .placeholder(R.drawable.main_logo).crossFade()
                .transform(mGlideCircleTransform)
                .into(mAvatarButton);
    }
}
