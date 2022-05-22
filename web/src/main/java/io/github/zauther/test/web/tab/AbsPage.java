package io.github.zauther.test.web.tab;

import android.view.View;

public abstract class AbsPage implements IPage{

    @Override
    public View getView() {
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

    @Override
    public ITab[] getChildren() {
        return new ITab[0];
    }
}
