package com.mono.oregano.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mono.oregano.data.network.DataSources;
import com.mono.oregano.data.repository.baseRepository;
import com.mono.oregano.ui.auth.ViewModelFactory;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VM extends  ViewModel,VB extends ViewBinding, BR extends baseRepository> extends Fragment {
    protected ViewBinding binding;

    protected ViewModel vModel;
    protected DataSources dataSources;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = (ViewBinding) getFragmentBinding(inflater, container);
        ViewModelFactory factory = new ViewModelFactory(getFragmentRepository());
        vModel = new ViewModelProvider(this, factory).get(getViewModel());
        return binding.getRoot();
    }
    protected abstract Class<VM> getViewModel();
    protected abstract BR getFragmentRepository();
    protected abstract VB getFragmentBinding(LayoutInflater inflater, ViewGroup container);

}
