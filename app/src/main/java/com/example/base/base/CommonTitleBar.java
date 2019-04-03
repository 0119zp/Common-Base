package com.example.base.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.base.R;
import com.example.base.icon.IconFontTextView;

/**
 * description: 公用title
 */
public class CommonTitleBar extends RelativeLayout {

    public static final int COMMON_TITLE_TYPE_NORMAL = 0x201;

    private int mTitleType = COMMON_TITLE_TYPE_NORMAL;
    private OnClickListener mTitleLeftClickLitener;

    private IconFontTextView mTitleLeft;
    private IconFontTextView mTitleRight;
    private TextView mTitleLabel;

    public CommonTitleBar(Context context) {
        super(context);
        initTitleBarView(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitleBarView(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTitleBarView(context);
    }

    private void initTitleBarView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View titleBarView = inflater.inflate(getTitleBarView(), this, true);

        mTitleLeft = titleBarView.findViewById(R.id.common_title_bar_left_icon);
        mTitleLabel = titleBarView.findViewById(R.id.common_title_bar_label);
        mTitleRight = titleBarView.findViewById(R.id.common_title_bar_right_icon);
    }

    private int getTitleBarView() {
        switch (getTitleType()) {
            case COMMON_TITLE_TYPE_NORMAL:
                return R.layout.common_title_bar_normal;
            default:
                return R.layout.common_title_bar_normal;
        }
    }

    private int getTitleType() {
        return mTitleType;
    }

    /**
     * 设置title bar 布局类型
     */
    public CommonTitleBar setTitleType(int titleType) {
        if (titleType == 0) {
            this.mTitleType = COMMON_TITLE_TYPE_NORMAL;
            return this;
        }
        this.mTitleType = titleType;
        return this;
    }

    /**
     * 返回titleLabel控件
     */
    public TextView getTitleLabel() {
        return mTitleLabel;
    }

    /**
     * 设置title 标题
     */
    public CommonTitleBar setTitleLabel(String title) {
        mTitleLabel.setText(title);
        return this;
    }

    /**
     * 返回 TitleLeft 控件
     */
    public IconFontTextView getTitleLeft() {
        return mTitleLeft;
    }

    /**
     * 设置左侧文字/图标
     */
    public CommonTitleBar setTitleLeft(String iconFont) {
        mTitleLeft.setText(iconFont);
        return this;
    }

    /**
     * 返回左侧点击监听
     */
    public OnClickListener getTitleLeftClickListener() {
        return mTitleLeftClickLitener;
    }

    /**
     * 设置左侧点击事件
     */
    public CommonTitleBar setTitleLeftClicklistener(OnClickListener clicklistener) {
        this.mTitleLeftClickLitener = clicklistener;
        if (null != clicklistener) {
            mTitleLeft.setOnClickListener(clicklistener);
        }
        return this;
    }

    /**
     * 返回 TitleRight 控件
     */
    public IconFontTextView getTitleRight() {
        return mTitleRight;
    }

    /**
     * 设置右侧文字/图标
     */
    public CommonTitleBar setTitleRight(String iconFont) {
        mTitleRight.setText(iconFont);
        return this;
    }

    /**
     * 设置右侧点击事件
     */
    public CommonTitleBar setTitleRightClicklistener(OnClickListener clicklistener) {
        if (null != clicklistener) {
            mTitleRight.setOnClickListener(clicklistener);
        }
        return this;
    }

    /**
     * 设置标题是否隐藏
     */
    public CommonTitleBar setTitleLabelVisibility(int visibility) {
        mTitleLabel.setVisibility(visibility);
        return this;
    }

    /**
     * 设置左侧是否隐藏
     */
    public CommonTitleBar setTitleLeftVisibility(int visibility) {
        mTitleLeft.setVisibility(visibility);
        return this;
    }

    /**
     * 设置右侧是否隐藏
     */
    public CommonTitleBar setTitleRightVisibility(int visibility) {
        mTitleRight.setVisibility(visibility);
        return this;
    }
}
