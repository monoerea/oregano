package com.mono.oregano.domain.useCase;

import com.mono.oregano.data.model.Model;

import java.util.ArrayList;

public class FilterByAlphabetUseCase<T> {
    private ArrayList<Model> currentList;

    public FilterByAlphabetUseCase(ArrayList<Model> entity){
        this.currentList = entity;
    }

    public IntroSort filter(){
        if (this.currentList==null){
            return null;
        }
        IntroSort introSort = new IntroSort(this.currentList);
        return introSort;
    }
}

