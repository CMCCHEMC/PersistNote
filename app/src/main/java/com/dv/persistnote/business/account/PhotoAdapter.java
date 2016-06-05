package com.dv.persistnote.business.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dv.persistnote.R;
import com.dv.persistnote.base.ContextManager;
import com.dv.persistnote.base.ResTools;
import com.dv.persistnote.base.util.HardwareUtil;
import com.dv.persistnote.base.util.Utilities;

import java.util.List;

public class PhotoAdapter extends BaseAdapter {

    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_PHOTO = 1;

    private List<Photo> mDatas;
    private Context mContext;
    private int mWidth;
    //是否显示相机，默认显示
    private boolean mIsShowCamera = true;

    public PhotoAdapter(Context context, List<Photo> mDatas) {
        this.mDatas = mDatas;
        this.mContext = context;
        int screenWidth = HardwareUtil.getDeviceWidth();
        mWidth = (screenWidth - Utilities.dip2px(ContextManager.getContext(),4))/3;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 && mIsShowCamera) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PHOTO;
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Photo getItem(int position) {
        if(mIsShowCamera) {
            if(position == 0){
                return null;
            }
            return mDatas.get(position-1);
        }else{
            return mDatas.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return mDatas.get(position).getId();
    }

    public void setDatas(List<Photo> mDatas) {
        this.mDatas = mDatas;
    }

    public void setIsShowCamera(boolean isShowCamera) {
        this.mIsShowCamera = isShowCamera;
    }

    public boolean isShowCamera() {
        return mIsShowCamera;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == TYPE_CAMERA) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_camera_layout, null);
            convertView.setTag(null);
            //设置高度等于宽度
            GridView.LayoutParams lp = new GridView.LayoutParams(mWidth, mWidth);
            convertView.setLayoutParams(lp);
        } else {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.item_photo_layout, null);
                holder.photoImageView = (ImageView) convertView.findViewById(R.id.imageview_photo);
                holder.maskView = convertView.findViewById(R.id.checkmark);
                holder.wrapLayout = (FrameLayout) convertView.findViewById(R.id.wrap_layout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Photo photo = getItem(position);

            Glide.with(ContextManager.getContext()).load(photo.getPath())
                    .placeholder(ResTools.getColor(R.color.c4)).crossFade()
                    .into(holder.photoImageView);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView photoImageView;
        private View maskView;
        private FrameLayout wrapLayout;
    }
}
