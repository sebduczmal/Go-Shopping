package com.sebduczmal.goshopping;

import android.app.ProgressDialog;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.sebduczmal.goshopping.common.di.ApplicationComponent;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    protected void showProgressDialog(@StringRes int stringResId) {
        showProgressDialog(getString(stringResId));
    }

    protected void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected ApplicationComponent getApplicationComponent() {
        final GoShoppingApplication application = (GoShoppingApplication) getApplication();
        return application.getApplicationComponent();
    }
}
