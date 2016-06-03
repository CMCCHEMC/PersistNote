package com.dv.persistnote.business.account;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.dv.persistnote.base.ContextManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoUtils {


    public static Map<String, PhotoFolder> getPhotos() {
        Map<String, PhotoFolder> folderMap = new HashMap<String, PhotoFolder>();

        String allPhotosKey = "所有图片";
        PhotoFolder allFolder = new PhotoFolder();
        allFolder.setName(allPhotosKey);
        allFolder.setDirPath(allPhotosKey);
        allFolder.setPhotoList(new ArrayList<Photo>());
        folderMap.put(allPhotosKey, allFolder);

        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = ContextManager.getContext().getContentResolver();

        // 只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(imageUri, null,
                MediaStore.Images.Media.MIME_TYPE + " in(?, ?)",
                new String[] { "image/jpeg", "image/png" },
                MediaStore.Images.Media.DATE_MODIFIED + " desc");

        int pathIndex = mCursor
                .getColumnIndex(MediaStore.Images.Media.DATA);

        mCursor.moveToFirst();
        while (mCursor.moveToNext()) {
            // 获取图片的路径
            String path = mCursor.getString(pathIndex);

            // 获取该图片的父路径名
            File parentFile = new File(path).getParentFile();
            if (parentFile == null) {
                continue;
            }
            String dirPath = parentFile.getAbsolutePath();

            if (folderMap.containsKey(dirPath)) {
                Photo photo = new Photo(path);
                PhotoFolder photoFolder = folderMap.get(dirPath);
                photoFolder.getPhotoList().add(photo);
                folderMap.get(allPhotosKey).getPhotoList().add(photo);
                continue;
            } else {
                // 初始化imageFolder
                PhotoFolder photoFolder = new PhotoFolder();
                List<Photo> photoList = new ArrayList<Photo>();
                Photo photo = new Photo(path);
                photoList.add(photo);
                photoFolder.setPhotoList(photoList);
                photoFolder.setDirPath(dirPath);
                photoFolder.setName(dirPath.substring(dirPath.lastIndexOf(File.separator) + 1, dirPath.length()));
                folderMap.put(dirPath, photoFolder);
                folderMap.get(allPhotosKey).getPhotoList().add(photo);
            }
        }
        mCursor.close();
        return folderMap;
    }

}
