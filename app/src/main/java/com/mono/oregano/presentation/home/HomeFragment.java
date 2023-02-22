package com.mono.oregano.presentation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.mono.oregano.data.repository.user.UserRepository;
import com.mono.oregano.databinding.FragmentHomeBinding;
import com.mono.oregano.presentation.BaseFragment;

public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository> {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    protected Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    protected UserRepository getFragmentRepository() {
        return UserRepository.getInstance();
    }

    @Override
    protected FragmentHomeBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}