package com.senior.project.genealogy.app;

import android.app.Application;

public class GenealogyApplication extends Application {

    private static GenealogyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static GenealogyApplication getInstance() {
        if (sInstance == null) {
            sInstance = new GenealogyApplication();
        }
        return sInstance;
    }
}
