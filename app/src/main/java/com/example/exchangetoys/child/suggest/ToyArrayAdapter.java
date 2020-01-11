package com.example.exchangetoys.child.suggest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.ToyService;
import com.example.exchangetoys.Tools.DownloadImageTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToyArrayAdapter extends RecyclerView.Adapter<ToyArrayAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Toy> itemList;

    // Constructor of the class
    public ToyArrayAdapter(int layoutId, ArrayList<Toy> itemList) {
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
        toyName.setText(itemList.get(listPosition).getToy_name());
        TextView toyInfo = holder.toyInfo;
        toyInfo.setText(itemList.get(listPosition).getToy_description());
        ImageView imageView = holder.toyImage;
        holder.toy=itemList.get(listPosition);
        if (itemList.get(listPosition).getToy_photos() != null && itemList.get(listPosition).getToy_photos() != "")
            new DownloadImageTask(imageView)
                    .execute(itemList.get(listPosition).getToy_photos().split(";")[0]);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView toyName, toyInfo;
        public ImageView toyImage;
        public Toy toy;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            toyName = itemView.findViewById(R.id.toyName);
            toyInfo = itemView.findViewById(R.id.toySomeInfo);
            toyImage = itemView.findViewById(R.id.imageViewToy);
            delete = itemView.findViewById(R.id.delete_suggestion);
            delete.setOnClickListener(v -> deleteRequest());

        }

        private void deleteRequest() {
            ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
            Call<Long> call = toyService.deleteSuggestion(toy.getToy_id());
            call.enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    if (response.isSuccessful()){

                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {

                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}