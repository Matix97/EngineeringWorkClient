package com.example.exchangetoys.recycleAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.R;

import java.util.ArrayList;

public class ImageArrayAdapter extends RecyclerView.Adapter<ImageArrayAdapter.ViewHolder> {

    private ArrayList<ImageAdapter> itemList;
    private int layout_id;

    public ImageArrayAdapter(ArrayList<ImageAdapter> itemList, int layout_id) {
        this.itemList = itemList;
        this.layout_id = layout_id;
    }

    @NonNull
    @Override
    public ImageArrayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        ImageArrayAdapter.ViewHolder myViewHolder = new ImageArrayAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageArrayAdapter.ViewHolder holder, int position) {
        ImageView imageView = holder.toyImage;
        imageView.setImageBitmap(itemList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView toyImage;

        public ViewHolder(View itemView) {
            super(itemView);
            //  itemView.setOnClickListener(this);   //todo unccometn if You want add some function on clisk item
            toyImage = itemView.findViewById(R.id.imageView);

        }

        @Override
        public void onClick(View view) {

        }
    }

}
