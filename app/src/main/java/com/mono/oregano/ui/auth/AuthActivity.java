package com.mono.oregano.ui.auth;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mono.oregano.databinding.ActivityAuthBinding;
public class AuthActivity extends AppCompatActivity {
    private ActivityAuthBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
