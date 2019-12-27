package com.app.engineeringaipracticaltest.adapter;

import com.app.engineeringaipracticaltest.R;
import com.app.engineeringaipracticaltest.utility.Utility;
import com.bumptech.glide.Glide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<String> imagesArrayList;

    ImageAdapter(ArrayList<String> imagesArrayList) {
        this.imagesArrayList = imagesArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_image_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String imageUrl = imagesArrayList.get(position);

        /*load image to imageview*/
        Glide.with(holder.ivImage.getContext())
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_image_place_holder_24dp)
            .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return Utility.isEmpty(imagesArrayList) ? 0 : imagesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
