package com.example.exchangetoys.ui.home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exchangetoys.DTOs.ToyServiceData.Toy;
import com.example.exchangetoys.R;
import com.example.exchangetoys.Services.ServiceGenerator;
import com.example.exchangetoys.Services.ToyService;
import com.example.exchangetoys.Tools.DownloadImageTask;
import com.example.exchangetoys.child.ToyActivityChild;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TorDeleteAdvert extends RecyclerView.Adapter<TorDeleteAdvert.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    public ArrayList<Toy> itemList;
    public Context context;
    public View view;

    // Constructor of the class
    public TorDeleteAdvert(int layoutId, ArrayList<Toy> itemList, Context context,View view) {
        listItemLayout = layoutId;
        this.itemList = itemList;
        this.context=context;
        this.view=view;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public TorDeleteAdvert.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
       TorDeleteAdvert.ViewHolder myViewHolder = new TorDeleteAdvert.ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final TorDeleteAdvert.ViewHolder holder, final int listPosition) {
        TextView toyName = holder.toyName;
        toyName.setText(itemList.get(listPosition).getToy_name());
        //   TextView toyInfo = holder.toyInfo;
        // toyInfo.setText(itemList.get(listPosition).getToy_description());
        ImageView imageView = holder.toyImage;
        holder.toy=itemList.get(listPosition);
        if (itemList.get(listPosition).getToy_photos() != null && itemList.get(listPosition).getToy_photos() != "")
            new DownloadImageTask(imageView)
                    .execute(itemList.get(listPosition).getToy_photos().split(";")[0]);
        holder.delete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm")
                    .setMessage("Are You sure to retrun advert? ")
                    .setNegativeButton(android.R.string.no, (dialog, which) -> {

                        Snackbar.make(view, "You cancel deleting", Snackbar.LENGTH_LONG).show();
                    })
                    .setPositiveButton(android.R.string.yes, ((dialog, which) -> {
                        deleteRequest(itemList.get(listPosition));
                        removeItem(itemList.get(listPosition));
                    }))
                    .show();

        });

    }
    public void removeItem(@NonNull Object object) {
        itemList.remove(object);
        notifyDataSetChanged();
    }
    private void deleteRequest(Toy toy) {
        ToyService toyService = ServiceGenerator.createAuthorizedService(ToyService.class);
       Call<Void> call =toyService.deleteToy(toy.getToy_id());
       call.enqueue(new Callback<Void>() {
           @Override
           public void onResponse(Call<Void> call, Response<Void> response) {
               Snackbar.make(view, "Deleting complete", Snackbar.LENGTH_LONG).show();
           }

           @Override
           public void onFailure(Call<Void> call, Throwable t) {

           }
       });

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
            //  toyInfo = itemView.findViewById(R.id.toySomeInfo);
            toyImage = itemView.findViewById(R.id.imageViewToy);
            delete = itemView.findViewById(R.id.delete_suggestion);


        }



        @Override
        public void onClick(View view) {
            if(ServiceGenerator.role.equals("child")){
                Intent intent = new Intent(view.getContext(), ToyActivityChild.class);
                intent.putExtra("toy", toy);
                view.getContext().startActivity(intent);
            }

        }
    }
}