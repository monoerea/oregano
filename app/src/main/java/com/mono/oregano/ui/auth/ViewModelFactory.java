package com.mono.oregano.ui.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mono.oregano.data.network.FirebaseAuthInstance;
import com.mono.oregano.data.repository.baseRepository;
import com.mono.oregano.data.repository.user.AuthRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @SuppressWarnings("unchecked")

    private baseRepository repository;



    public ViewModelFactory() {
    }

    public ViewModelFactory(baseRepository repository) {
        this.repository = repository;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel((AuthRepository) baseRepository.getInstance(new FirebaseAuthInstance()));
        }
        else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
