package com.example.base.base.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.base.R;

/**
 * @author zpan
 * @date 2019/3/29 17:58
 *
 * description: 公用 titlebar 容器
 */
public class TitleBarWrapper extends RelativeLayout {

    private LinearLayout mTitleBarRoot;
    private Context mContext;

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
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.common_title_bar_container, this, true);
        mTitleBarRoot = view.findViewById(R.id.ll_common_title_bar_root);
    }

    /**
     * 获得 titleBar 工厂对象
     */
    public TitleBarFactory getTitleBarFactory() {
        return new TitleBarFactory(mContext, mTitleBarRoot);
    }

    /**
     * 获得 titlebar 的布局容器
     * 注：需要自定义 titlebar 布局的时候调用该方法获得titlebar的布局容器，将自定义布局addview进去
     */
    public LinearLayout getTitleBarRootView() {
        return mTitleBarRoot;
    }
}
