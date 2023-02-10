package com.mono.oregano.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mono.oregano.R;
import com.mono.oregano.databinding.FragmentStartUpScreenBinding;
import com.mono.oregano.ui.auth.AuthActivity;

public class StartUpScreenActivity extends Fragment {
    private FragmentStartUpScreenBinding binding;
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentStartUpScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button registerBtn = binding.registerBtn.getRoot();
        final Button signInBtn = binding.signInBtn.getRoot();

        assignTextContent("Sign up", registerBtn);
        assignTextContent("Sign in", signInBtn);

        navigate(registerBtn, R.id.action_startFragment_to_registerFragment);
        navigate(signInBtn, R.id.action_startFragment_to_loginFragment);
    }
    public void navigate(Button button, Integer id){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(binding.getRoot()).navigate(id);
            }
        });
    }
    public void assignTextContent(String text, @NonNull TextView view){
        view.setText(text);
    }
}