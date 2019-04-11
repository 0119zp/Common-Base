package com.example.base.base.nonetwork;

import android.view.View;
import android.widget.TextView;
import com.example.base.R;
import com.example.base.base.BaseFragment;

/**
 * @author zpan
 * @date 2019/4/9 10:11
 *
 * description: 无网络 Fragment
 */
public class CommonNoNetWorkFragment extends BaseFragment {

    private OnNoNetWorkCallBack mCallback;

    @Override
    public int exInitFragmentLayout() {
        return R.layout.common_fragment_no_network;
    }

    @Override
    protected OnNoNetWorkCallBack getNoNetWorkCallback() {
        return null;
    }

    @Override
    public void exInitFragmentView(View contentView) {
        TextView mTvRefresh = contentView.findViewById(R.id.tv_not_work_refresh);

        mTvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.clickRefresh();
                }
            }
        });
    }

    public void setNoNetWorkCallBack(OnNoNetWorkCallBack callback) {
        this.mCallback = callback;
    }

    public interface OnNoNetWorkCallBack {
        /**
         * 点击刷新
         */
        void clickRefresh();
    }
}
