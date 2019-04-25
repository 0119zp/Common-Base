package com.example.base.base.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.example.base.R;

/**
 * @author zpan
 * @date 2019/3/29 17:58
 *
 * description: 公用 titlebar 容器
 */
public class TitleBarWrapper extends FrameLayout {

    public TitleBarWrapper(Context context) {
        super(context);
        initTitleBarView(context);
    }

    public TitleBarWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitleBarView(context);
    }

    public TitleBarWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTitleBarView(context);
    }

    private void initTitleBarView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.common_title_bar_container, this, true);
    }
}
