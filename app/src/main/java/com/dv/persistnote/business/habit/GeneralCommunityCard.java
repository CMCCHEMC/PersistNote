package com.dv.persistnote.business.habit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dv.persistnote.R;
import com.dv.persistnote.base.ContextManager;

/**
 * Created by Hang on 2016/4/3.
 */
public class GeneralCommunityCard extends FrameLayout {

    private ImageView mUserAvatar;
    private TextView mUserName;
    private TextView mUpdateTime;
    private TextView mPersistDuration;


    public GeneralCommunityCard(Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(ContextManager.getContext());
        inflater.inflate(R.layout.community_card, this, true);
        mUserAvatar = (ImageView) findViewById(R.id.user_avatar);
        mUserName = (TextView) findViewById(R.id.user_name);
        mUpdateTime = (TextView) findViewById(R.id.update_time);
        mPersistDuration = (TextView) findViewById(R.id.persist_duration);
    }


    public void bindData(CommunityModel.CommunityData communityData) {
    }
}