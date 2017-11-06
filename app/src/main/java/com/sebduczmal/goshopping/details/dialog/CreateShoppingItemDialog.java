package com.sebduczmal.goshopping.details.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.databinding.DialogCreateShoppingItemBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


public class CreateShoppingItemDialog extends DialogFragment {

    private CompositeDisposable viewsDisposables = new CompositeDisposable();
    private OnShoppingItemCreateListener onShoppingItemCreateListener;
    private DialogCreateShoppingItemBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onShoppingItemCreateListener = (OnShoppingItemCreateListener) getActivity();
        } catch (ClassCastException e) {
            Timber.e(e, "Calling activity must implement OnShoppingItemCreateListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_create_shopping_item,
                container, false);
        setupViews();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        viewsDisposables.dispose();
        super.onStop();
    }

    @Override
    public void onDetach() {
        onShoppingItemCreateListener = null;
        super.onDetach();
    }

    /**
     * Example of using Rx for views
     */
    private void setupViews() {
        viewsDisposables.add(RxView.clicks(binding.buttonOk)
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    final String itemName = binding.inputItemName.getText().toString();
                    final String quantity = binding.inputQuantity.getText().toString();
                    final String unit = binding.inputUnit.getText().toString();
                    if (!(TextUtils.isEmpty(itemName) || TextUtils.isEmpty(quantity) || TextUtils
                            .isEmpty(unit))) {
                        onShoppingItemCreateListener.onShoppingItemCreated(itemName, Long.valueOf
                                (quantity), unit);
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), R.string.empty_item_details_warning, Toast
                                .LENGTH_SHORT).show();
                    }
                }));

        viewsDisposables.add(RxView.clicks(binding.buttonCancel)
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> dismiss()));
    }
}
