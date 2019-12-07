package com.example.exchangetoys.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.R;
import com.example.exchangetoys.ToyActivity;

import java.util.ArrayList;

public class ToyArrayAdapter extends RecyclerView.Adapter<ToyArrayAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<ToyModelToRecycle> itemList;

    // Constructor of the class
    public ToyArrayAdapter(int layoutId, ArrayList<ToyModelToRecycle> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ToyArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ToyArrayAdapter.ViewHolder myViewHolder = new ToyArrayAdapter.ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ToyArrayAdapter.ViewHolder holder, final int listPosition) {
        TextView toyName = holder.toyName;
        toyName.setText(itemList.get(listPosition).getName());
        TextView toyInfo = holder.toyInfo;
        toyInfo.setText(itemList.get(listPosition).getInfo());
        ImageView imageView = holder.toyImage;
        imageView.setImageResource(itemList.get(listPosition).getImage());
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView toyName, toyInfo;
        public ImageView toyImage;
        public Button toyButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            toyName = itemView.findViewById(R.id.toyName);
            toyInfo = itemView.findViewById(R.id.toySomeInfo);
            toyImage = itemView.findViewById(R.id.imageViewToy);
            toyButton = itemView.findViewById(R.id.buttonToyAction);
        }

        @Override
        public void onClick(View view) {
          //  Toast.makeText(view.getContext(), "onClick " + getLayoutPosition() + " " + toyName.getText(), Toast.LENGTH_SHORT).show();
            //Log.d("onclick", "onClick " + getLayoutPosition() + " " + toyName.getText());
            Intent intent = new Intent(view.getContext(), ToyActivity.class);
            intent.putExtra("toy",new ToyModelToRecycle(toyName.getText().toString(),toyInfo.getText().toString(),toyImage.getImageAlpha()));
            view.getContext().startActivity(intent);
        }
    }
}