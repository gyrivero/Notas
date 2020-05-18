package com.example.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.myapplication.ui.MainActivity;

public class NotaRepositoryProvider {
    private static NotaRepository instance;

    public static NotaRepository getRepository(final Application application) {
        if (instance == null) {
            instance = new NotaRepository(application);
        }
        return instance;
    }
}
