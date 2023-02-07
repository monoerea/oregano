package com.mono.oregano.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mono.oregano.R;
import com.mono.oregano.databinding.ActivityStartUpScreenBinding;

public class StartUpScreenActivity extends AppCompatActivity {
    private ActivityStartUpScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up_screen);

        binding = ActivityStartUpScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button registerBtn = binding.registerBtn.getRoot();
        final Button signInBtn = binding.signInBtn.getRoot();

        assignTextContent("Sign up", registerBtn);
        assignTextContent("Sign in", signInBtn);

        ScrollView startUp = findViewById(R.id.start_screen);

    }

    public void assignTextContent(String text, @NonNull TextView view){
        view.setText(text);
    }
}