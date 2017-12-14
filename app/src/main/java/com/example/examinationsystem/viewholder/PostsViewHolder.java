package com.example.examinationsystem.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examinationsystem.R;
import com.example.examinationsystem.interfaces.ItemClickListener;

/**
 * Created by Shailesh on 10/1/2017.
 */

public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView, postUserIcon;
    public TextView textDesc, txtUserName,txtTime;

    private ItemClickListener itemClickListener;

    public PostsViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.layout_posts_image);
        postUserIcon = (ImageView) itemView.findViewById(R.id.layout_posts_user_icon);
        textDesc = (TextView) itemView.findViewById(R.id.layout_posts_desc);
        txtUserName = (TextView) itemView.findViewById(R.id.layout_posts_user_name);
        txtTime = (TextView) itemView.findViewById(R.id.postTime);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
