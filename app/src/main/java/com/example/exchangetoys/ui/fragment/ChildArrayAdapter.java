package com.example.exchangetoys.ui.fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.R;

import java.util.ArrayList;


public class ChildArrayAdapter extends RecyclerView.Adapter<ChildArrayAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<ChildModelToRecycle> itemList;

    // Constructor of the class
    public ChildArrayAdapter(int layoutId, ArrayList<ChildModelToRecycle> itemList) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        return new ViewHolder(view);
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView item = holder.item;
        item.setText(itemList.get(listPosition).getName());
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            item = itemView.findViewById(R.id.row_item);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + item.getText());
        }
    }
}


//olx takie kategorie
//konfiguracja obaszaru wyszukiwanie
//wypożyczanie za opłatą, można
//opłata i kaucja
////pole na co mozna się wymienić
//kto chce na co się wymienić
//filtrowanie po stronie serwera
//hosting plików,
//