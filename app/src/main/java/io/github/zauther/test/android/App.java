package io.github.zauther.test.android;

import android.app.Application;

import io.github.zauther.test.web.ZWeb;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZWeb.init(this);
    }
}
