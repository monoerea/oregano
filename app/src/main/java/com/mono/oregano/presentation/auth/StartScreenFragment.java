package com.mono.oregano.presentation.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.mono.oregano.R;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.databinding.FragmentStartScreenBinding;
import com.mono.oregano.domain.util.uiUtil;
import com.mono.oregano.presentation.BaseFragment;

public class StartScreenFragment extends BaseFragment<AuthViewModel, FragmentStartScreenBinding, AuthRepository> {
    private FragmentStartScreenBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    protected AuthRepository getFragmentRepository() {
        return AuthRepository.getInstance();
    }

    @Override
    protected FragmentStartScreenBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentStartScreenBinding.inflate(inflater, container, false);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        ViewModelFactory factory = new ViewModelFactory(getFragmentRepository());
        vModel = new ViewModelProvider(this, factory).get(getViewModel());
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button registerBtn = binding.registerBtn.getRoot();
        final Button signInBtn = binding.signInBtn.getRoot();
;
        uiUtil.assignTextContent("Sign up", registerBtn);

        uiUtil.navigate(binding.getRoot(),registerBtn, R.id.action_startFragment_to_registerFragment);
        uiUtil.navigate(binding.getRoot(),signInBtn, R.id.action_startFragment_to_loginFragment);
    }



}