package com.example.base.base.loading;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;

/**
 * @author Panpan Zhang
 * @date 2019/4/9 16:37
 *
 * description: 公用loading
 */
public class CommonLoading extends LinearLayout {

    public CommonLoading(Context context) {
        super(context);
        initLoading(context);
    }

    public CommonLoading(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLoading(context);
    }

    public CommonLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLoading(context);
    }

    private void initLoading(Context context) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.TRANSPARENT);

        ProgressBar progressBar = new ProgressBar(context);
        addView(progressBar);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
