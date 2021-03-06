package com.dv.persistnote.business.habit;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dv.persistnote.R;
import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.framework.ActionId;
import com.dv.persistnote.framework.ui.IUIObserver;
import com.dv.persistnote.framework.ui.common.GlideCircleTransform;

import habit.dao.CommunityRecord;

/**
 * Created by Hang on 2016/4/3.
 */
public class GeneralCommunityCard extends FrameLayout implements View.OnClickListener{

    private ImageView mUserAvatar;
    private TextView mUserName;
    private TextView mUpdateTime;
    private TextView mPersistDuration;
    private TextView mContent;
    private ImageView mContentImage;

    private ImageView mShareButton;

    private GlideCircleTransform mCircleTransform;

    private IUIObserver mObserver;

    private CommunityRecord mData;

    public GeneralCommunityCard(Context context, IUIObserver observer) {
        super(context);
        mObserver = observer;
        LayoutInflater inflater = LayoutInflater.from(ContextManager.getContext());
        inflater.inflate(R.layout.community_card, this, true);
        mUserAvatar = (ImageView) findViewById(R.id.user_avatar);
        mUserName = (TextView) findViewById(R.id.user_name);
        mUpdateTime = (TextView) findViewById(R.id.update_time);
        mPersistDuration = (TextView) findViewById(R.id.persist_duration);
        mContent = (TextView)findViewById(R.id.card_content);
        mContentImage = (ImageView)findViewById(R.id.card_image);
        mCircleTransform = new GlideCircleTransform(getContext());

        mShareButton = (ImageView)findViewById(R.id.card_share);
        mShareButton.setOnClickListener(this);
    }

    public void bindData(CommunityRecord communityData) {
        mData = communityData;
//        mUserAvatar.seti
        Glide.with(getContext()).load(communityData.getAvatarUrl())
                .placeholder(R.drawable.main_logo).crossFade()
                .transform(mCircleTransform)
                .into(mUserAvatar);
        Glide.with(getContext()).load(communityData.getAvatarUrl())
                .placeholder(new ColorDrawable(ResTools.getColor(R.color.c3)))
                .into(mContentImage);

        mUserName.setText(communityData.getUserName());
        mUpdateTime.setText(communityData.getTimestamp());
        mPersistDuration.setText(String.valueOf(communityData.getPersistCount()));
        mContent.setText(communityData.getContent());
        mPersistDuration.setText(communityData.getPersistCount() +" 天");
    }

    public void enableSpacing(boolean b) {
        int spacing = b ? ResTools.getDimenInt(R.dimen.common_margin_8) : 0;
        setPadding(0, spacing, 0, 0);
    }

    @Override
    public void onClick(View v) {
        if(v == mShareButton) {
            mObserver.handleAction(ActionId.OnCommunityCardShare, mData, null);
        }
    }
}