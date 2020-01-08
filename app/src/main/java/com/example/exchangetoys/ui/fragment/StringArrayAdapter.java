package com.example.exchangetoys.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.R;

import java.util.ArrayList;

public class StringArrayAdapter extends RecyclerView.Adapter<StringArrayAdapter.ViewHolder> {
    private ArrayList<String> itemList;
    public StringArrayAdapter(ArrayList<String> features){
        itemList=features;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_item, parent, false);
        StringArrayAdapter.ViewHolder myViewHolder = new StringArrayAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name=itemList.get(position);
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public String name;
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox =itemView.findViewById(R.id.checkBox);
            checkBox.setText(name);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
