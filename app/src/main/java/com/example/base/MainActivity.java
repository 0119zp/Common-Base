package com.example.base;

import android.view.View;
import com.example.base.base.CommonTitleBar;
import com.example.base.base.ZpBaseActivity;
import com.example.base.utils.CommonToast;

public class MainActivity extends ZpBaseActivity {

    @Override
    public int exInitLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void exInitTitleBar(CommonTitleBar titleBar) {
        super.exInitTitleBar(titleBar);
        titleBar.setTitleLabel("汉薇商城").setTitleLeft("返回").setTitleRight("分享").setTitleLeftClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonToast(MainActivity.this).setText("返回").show();
            }
        }).setTitleRightClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonToast(MainActivity.this).setText("分享").show();
            }
        }).setTitleRightVisibility(View.GONE);
    }
}
