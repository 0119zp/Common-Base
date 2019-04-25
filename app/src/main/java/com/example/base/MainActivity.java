package com.example.base;

import com.example.base.base.BaseActivity;

/**
 * @author Zpan
 */
public class MainActivity extends BaseActivity {

    @Override
    public int exTitleBarLabel() {
        return R.string.app_name;
    }

    @Override
    public int exInitLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void exInitView() {

    }
}
