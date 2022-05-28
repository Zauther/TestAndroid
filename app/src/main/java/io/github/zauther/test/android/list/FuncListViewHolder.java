package io.github.zauther.test.android.list;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.github.zauther.test.android.R;

public class FuncListViewHolder extends RecyclerView.ViewHolder {

    TextView nameTv;


    public FuncListViewHolder(@NonNull View root) {
        super(root);
        nameTv = root.findViewById(R.id.funcItemName);
    }

    public void setValue(FuncListItem item) {
        nameTv.setText(item.name);
        nameTv.setOnClickListener(item.clickListener);
    }

}
