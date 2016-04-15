package com.dv.persistnote.business.habit;

import com.dv.persistnote.base.network.bean.ZHOther;
import com.dv.persistnote.base.network.bean.ZHResult;

import java.util.ArrayList;
import java.util.List;

import habit.dao.CommunityRecord;

/**
 * Created by Hang on 2016/4/14.
 */
public class CommunityDataProcesser {

    public static List<CommunityRecord> convertNetDataToBean(ZHResult zhResult) {
        List<CommunityRecord> list = new ArrayList<>();
        try {
            List<ZHOther> others = zhResult.getOthers();
            for(ZHOther other : others) {
                CommunityRecord record = new CommunityRecord();
                record.setUserName(other.getName());
                record.setContent(other.getDescription());
                record.setAvatarUrl(other.getThumbnail());
                record.setTimestamp(String.valueOf(other.getColor()));
                record.setPersistCount(other.getId());
                list.add(record);
            }
        } catch (Exception e) {

        }
        return list;
    }
}
