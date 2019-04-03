package com.example.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
import com.example.base.R;

/**
 * description: 公用的Toast
 */
public class CommonToast extends Toast {

    private TextView mToastText;
    private Context mContext;

    public CommonToast(Context context) {
        super(context);
        this.mContext = context;
        initToast(context);
    }

    private void initToast(Context context) {
        if (mToastText == null) {
            mToastText = new TextView(context);
            mToastText.setTextSize(16);
            mToastText.setTextColor(Color.parseColor("#FFFFFF"));
            int padding = dp2px(context, 15.0f);
            mToastText.setPadding(padding, padding, padding, padding);
            mToastText.setMaxWidth(dp2px(context, 180));
            mToastText.setBackgroundResource(R.drawable.common_shape_toast_background);
        }
        setGravity(Gravity.CENTER, 0, 0);
        setDuration(Toast.LENGTH_SHORT);
        setView(mToastText);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置提示文案
     *
     * @param str 字符串
     */
    public CommonToast setText(String str) {
        mToastText.setText(str);
        return this;
    }

    /**
     * 设置提示文案
     *
     * @param id 字符串资源 id
     */
    public CommonToast setTextId(int id) {
        String resources = mContext.getResources().getString(id);
        mToastText.setText(resources);
        return this;
    }

    /**
     * 设置显示时长
     *
     * @param duration 时长
     */
    public CommonToast setduration(int duration) {
        setDuration(duration);
        return this;
    }
}
