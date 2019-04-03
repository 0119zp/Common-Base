package com.example.base.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.example.base.R;

/**
 * description: Activity基类-公用title
 */
public class ZpBaseActivity extends BaseActivity {

    @Override
    public void buildInitView() {
        initRootView();
        super.buildInitView();
    }

    private void initRootView() {
        setContentView(R.layout.common_activity_base_view);
        CommonTitleBar commonTitleBar = findViewById(R.id.ctb_common_title_bar);
        exInitTitleBar(commonTitleBar);
        FrameLayout commonContent = findViewById(R.id.fl_common_content);
        View contentView = LayoutInflater.from(this).inflate(exInitLayout(), null);
        commonContent.addView(contentView);
    }

    @Override
    public int exInitLayout() {
        return 0;
    }

    @Override
    public void exInitTitleBar(CommonTitleBar titleBar) {
        if (titleBar.getTitleLeftClickListener() == null) {
            titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    public void exInitBundle() {

    }

    @Override
    public void exInitView() {

    }

    @Override
    public void exInitAfter() {

    }
}
