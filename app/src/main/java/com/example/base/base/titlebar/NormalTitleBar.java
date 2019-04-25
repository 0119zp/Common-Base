package com.example.base.base.titlebar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.base.R;
import com.example.base.icon.IconFontTextView;

/**
 * @author zpan
 * @date 2019/4/3 19:59
 *
 * description: 包括：左侧、中部、右侧
 */
public class NormalTitleBar extends BaseTitleBar {

    private IconFontTextView mTitleLeft;
    private IconFontTextView mTitleRight;
    private TextView mTitleLabel;

    private Activity mActivity;
    private View normalTitleBarView;
    private View.OnClickListener mTitleLeftClickLitener;

    public NormalTitleBar(Activity activity) {
        this.mActivity = activity;
        normalTitleBarView = LayoutInflater.from(activity).inflate(R.layout.common_title_bar_normal, null);

        mTitleLeft = normalTitleBarView.findViewById(R.id.common_title_bar_left_icon);
        mTitleLabel = normalTitleBarView.findViewById(R.id.common_title_bar_label);
        mTitleRight = normalTitleBarView.findViewById(R.id.common_title_bar_right_icon);
        // 右侧默认隐藏
        mTitleRight.setVisibility(View.GONE);
        // 左侧默认返回
        if (getTitleLeftClickListener() == null) {
            mTitleLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.finish();
                }
            });
        }
    }

    @Override
    public View getContentView() {
        return normalTitleBarView;
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
    public NormalTitleBar setTitleLabel(String title) {
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
    public NormalTitleBar setTitleLeft(String iconFont) {
        mTitleLeft.setText(iconFont);
        return this;
    }

    /**
     * 返回左侧点击监听
     */
    public View.OnClickListener getTitleLeftClickListener() {
        return mTitleLeftClickLitener;
    }

    /**
     * 设置左侧点击事件
     */
    public NormalTitleBar setTitleLeftClicklistener(View.OnClickListener clicklistener) {
        this.mTitleLeftClickLitener = clicklistener;
        if (null != getTitleLeftClickListener()) {
            mTitleLeft.setOnClickListener(clicklistener);
            return this;
        }
        mTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
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
    public NormalTitleBar setTitleRight(String iconFont) {
        mTitleRight.setText(iconFont);
        return this;
    }

    /**
     * 设置右侧点击事件
     */
    public NormalTitleBar setTitleRightClicklistener(View.OnClickListener clicklistener) {
        if (null != clicklistener) {
            mTitleRight.setOnClickListener(clicklistener);
        }
        return this;
    }

    /**
     * 设置标题是否隐藏
     */
    public NormalTitleBar setTitleLabelVisibility(int visibility) {
        mTitleLabel.setVisibility(visibility);
        return this;
    }

    /**
     * 设置左侧是否隐藏
     */
    public NormalTitleBar setTitleLeftVisibility(int visibility) {
        mTitleLeft.setVisibility(visibility);
        return this;
    }

    /**
     * 设置右侧是否隐藏
     */
    public NormalTitleBar setTitleRightVisibility(int visibility) {
        mTitleRight.setVisibility(visibility);
        return this;
    }
}
