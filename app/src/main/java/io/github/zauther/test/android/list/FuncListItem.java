package io.github.zauther.test.android.list;

import android.view.View;

public class FuncListItem {
    String name;
    View.OnClickListener clickListener;

    public FuncListItem(String name, View.OnClickListener clickListener) {
        this.name = name;
        this.clickListener = clickListener;
    }
}
