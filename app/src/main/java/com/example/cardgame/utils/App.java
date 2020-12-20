package com.example.cardgame.utils;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySPV.init(this);
    }
}
