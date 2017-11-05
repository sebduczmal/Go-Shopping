package com.sebduczmal.goshopping.current.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.sebduczmal.goshopping.R;
import com.sebduczmal.goshopping.databinding.DialogCreateShoppingListBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class CreateShoppingListDialog extends DialogFragment {

    private CompositeDisposable viewsDisposables = new CompositeDisposable();
    private OnShoppingListCreateListener onShoppingListCreateListener;
    private DialogCreateShoppingListBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onShoppingListCreateListener = (OnShoppingListCreateListener) getActivity();
        } catch (ClassCastException e) {
            Timber.e(e, "Calling activity must implement OnShoppingListCreateListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_create_shopping_list,
                container, false);
        setupViews();
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        viewsDisposables.dispose();
        super.onStop();
    }

    @Override
    public void onDetach() {
        onShoppingListCreateListener = null;
        super.onDetach();
    }

    /**
     * Example of using Rx for views
     */
    private void setupViews() {
        viewsDisposables.add(RxView.clicks(binding.buttonOk)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    final String shoppingListName = binding.etShoppingListName.getText().toString();
                    if (!TextUtils.isEmpty(shoppingListName)) {
                        onShoppingListCreateListener.onShoppingListCreated(shoppingListName);
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), R.string.empty_list_name_warning, Toast
                                .LENGTH_SHORT).show();
                    }
                }));

        viewsDisposables.add(RxView.clicks(binding.buttonCancel)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> dismiss()));
    }
}
