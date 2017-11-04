package com.sebduczmal.goshopping.current;

import com.sebduczmal.goshopping.BasePresenter;


public class CurrentListPresenter extends BasePresenter<CurrentListView> {
    @Override
    protected Class viewClass() {
        return CurrentListView.class;
    }
}
