package com.dv.persistnote.business.habit;

import android.content.Context;

import com.dv.persistnote.FakeDataHelper;

/**
 * Created by Hang on 2016/4/3.
 */
public class CommunityModel {

    private static CommunityModel mInstance;

    public static CommunityModel getInstance() {
        if (mInstance == null) {
            mInstance = new CommunityModel();
        }
        return mInstance;
    }


    public int getCommunityCount() {
        return FakeDataHelper.COMMUTIY_COUNT;
    }
}
