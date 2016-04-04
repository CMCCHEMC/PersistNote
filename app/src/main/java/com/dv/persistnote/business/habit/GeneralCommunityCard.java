package com.dv.persistnote.business.habit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.dv.persistnote.R;

/**
 * Created by Hang on 2016/4/3.
 */
public class GeneralCommunityCard extends LinearLayout {


    public GeneralCommunityCard(Context context) {
        super(context);

    }

    public static View build(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.community_card, null);
        return v;
    }
}