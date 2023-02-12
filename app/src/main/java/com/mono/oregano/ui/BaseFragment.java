package com.mono.oregano.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mono.oregano.R;
import com.mono.oregano.data.network.DataSources;
import com.mono.oregano.data.repository.baseRepository;
import com.mono.oregano.databinding.BottomNavBinding;
import com.mono.oregano.ui.auth.ViewModelFactory;

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

    public static class BottomNavActivity extends AppCompatActivity {

        private BottomNavBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = BottomNavBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_nav);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }

    }
}
