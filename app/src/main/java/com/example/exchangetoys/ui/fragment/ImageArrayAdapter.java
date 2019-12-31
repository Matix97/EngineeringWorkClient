package com.example.exchangetoys.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.R;
import com.example.exchangetoys.Tools.DownloadImageTask;

import java.util.ArrayList;

public class ImageArrayAdapter extends RecyclerView.Adapter<ImageArrayAdapter.ViewHolder> {

    private ArrayList<String> itemList;

    public ImageArrayAdapter(ArrayList<String> photosURL){
        itemList=photosURL;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        ImageArrayAdapter.ViewHolder myViewHolder = new ImageArrayAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView imageView = holder.toyImage;
            new DownloadImageTask(imageView)
                    .execute(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView toyImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            toyImage =itemView.findViewById(R.id.imageView2);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
