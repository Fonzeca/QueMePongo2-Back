package com.s21.quemepongo2front.ui.Preferencias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PreferenciasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PreferenciasViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}