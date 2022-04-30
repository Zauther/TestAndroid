package io.github.zauther.test.android.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.zauther.test.android.R;

public class FuncListAdapter extends RecyclerView.Adapter<FuncListViewHolder> {

    Context context;
    List<FuncListItem> data;

    public FuncListAdapter(Context context, List<FuncListItem> data) {
        this.context = context;
        this.data = data;
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public FuncListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.func_list_item, parent, false);
        return new FuncListViewHolder(root);
    }

    //    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater.from()
//        return new FuncListViewHolder(parent);
//    }
//
    @Override
    public void onBindViewHolder(@NonNull FuncListViewHolder holder, int position) {
        holder.setValue(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        holder.itemView.findViewById()
//    }
}
