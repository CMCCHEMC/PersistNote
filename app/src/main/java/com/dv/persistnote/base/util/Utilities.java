package com.dv.persistnote.base.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.Date;

public final class Utilities {
	private static int id_base = 0x60000000;
	
	public static int densityDpi;

	public static boolean isHaveKeyDownEvent = false;

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 获取拍照相片存储文件
	 * @param context
	 * @return
	 */
	public static File createFile(Context context){
		File file;
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			String timeStamp = String.valueOf(new Date().getTime());
			file = new File(Environment.getExternalStorageDirectory() +
					File.separator + timeStamp+".jpg");
		}else{
			File cacheDir = context.getCacheDir();
			String timeStamp = String.valueOf(new Date().getTime());
			file = new File(cacheDir, timeStamp+".jpg");
		}
		return file;
	}

}
