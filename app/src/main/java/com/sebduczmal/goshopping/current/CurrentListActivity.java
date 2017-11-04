package com.sebduczmal.goshopping.current;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sebduczmal.goshopping.BaseActivity;
import com.sebduczmal.goshopping.R;


public class CurrentListActivity extends BaseActivity implements CurrentListView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_list);
    }
}
