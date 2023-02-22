package com.mono.oregano.presentation.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mono.oregano.data.repository.baseRepository;
import com.mono.oregano.data.repository.user.AuthRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @SuppressWarnings("unchecked")

    private baseRepository repository;

    public <BR extends baseRepository> ViewModelFactory(BR fragmentRepository) {
        this.repository = fragmentRepository;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel((AuthRepository) repository);
        }
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
