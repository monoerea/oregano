package com.mono.oregano.domain.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import java.util.ArrayList;

public class uiUtil {
    public static void navigate(View view, Button button, Integer id){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(id);
            }
        });
    }
    public static void assignTextContent(String text, TextView view){
        view.setText(text);
    }

    public static ArrayList<TextView> toList(ViewGroup view){
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < view.getChildCount(); i++) {
            arrayList.add(view.getChildAt(i));
        } ;
        return arrayList;
    }
}
