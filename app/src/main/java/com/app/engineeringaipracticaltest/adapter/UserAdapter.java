package com.app.engineeringaipracticaltest.adapter;

import com.app.engineeringaipracticaltest.R;
import com.app.engineeringaipracticaltest.model.User;
import com.app.engineeringaipracticaltest.utility.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> userArrayList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        bindData(holder, user);
    }

    private void bindData(ViewHolder holder, final User user) {

        /*load user image*/
        Glide.with(holder.ivUser.getContext())
            .load(user.getImage())
            .apply(new RequestOptions().circleCrop())
            .placeholder(R.drawable.ic_user_place_holder_24dp)
            .into(holder.ivUser);

        /*set user name*/
        holder.tvUserName.setText(user.getName());

        /*set image list of user*/
        final ImageAdapter imageAdapter = new ImageAdapter((ArrayList<String>) user.getItems());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(holder.rvImage.getContext(), 2);

        gridLayoutManager.setSpanSizeLookup(
            new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    /*check for odd even logic*/
                    if (user.getItems().size() % 2 == 0) {
                        return 1;
                    } else if (position == 0) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
            });

        holder.rvImage.setLayoutManager(gridLayoutManager);

        holder.rvImage.setAdapter(imageAdapter);
    }

    public void setItems(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Utility.isEmpty(userArrayList) ? 0 : userArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivUser;

        private TextView tvUserName;

        private RecyclerView rvImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.ivUser);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            rvImage = itemView.findViewById(R.id.rvImage);
        }
    }
}
