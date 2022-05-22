package io.github.zauther.test.web;

import android.view.View;

import io.github.zauther.test.web.tab.IPage;
import io.github.zauther.test.web.tab.ITab;

public class WebTab implements IPage {
    @Override
    public View getView() {
        return null;
    }

    @Override
    public ITab[] getChildren() {
        return new ITab[0];
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onAppear() {

    }

    @Override
    public void onDisappear() {

    }

    @Override
    public void onDestroy() {

    }
}
