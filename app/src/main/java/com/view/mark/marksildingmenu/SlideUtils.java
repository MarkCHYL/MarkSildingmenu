package com.view.mark.marksildingmenu;

import android.content.Context;

/**
 * 项目名称：MarkSildingmenu
 * 类描述：工具类
 * Created by mark on 2018/5/27 18:18
 * 修改人：mark
 * 修改时间：2018/5/27 18:18
 * 修改备注：
 */
public class SlideUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
