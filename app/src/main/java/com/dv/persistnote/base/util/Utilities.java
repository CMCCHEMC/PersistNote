package com.dv.persistnote.base.util;

import android.content.Context;

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

}
