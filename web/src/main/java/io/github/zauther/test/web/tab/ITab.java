package io.github.zauther.test.web.tab;

import android.view.View;

public interface ITab {

    String name();

    void onCreate();

    void onAppear();

    void onDisappear();

    void onDestroy();
}
