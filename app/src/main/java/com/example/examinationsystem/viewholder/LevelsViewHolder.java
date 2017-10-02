package com.example.examinationsystem.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examinationsystem.R;
import com.example.examinationsystem.interfaces.ItemClickListener;

/**
 * Created by Shailesh on 10/2/2017.
 */

public class LevelsViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtLevel, txtDuration, txtMarks, txtDesc;
    public Button btnStatus;

    private ItemClickListener itemClickListener;

    public LevelsViewHolder(View itemView) {
        super(itemView);
        txtLevel = (TextView) itemView.findViewById(R.id.layout_level_name);
        txtDuration = (TextView) itemView.findViewById(R.id.layout_level_duration);
        txtMarks = (TextView) itemView.findViewById(R.id.layout_level_marks);
        txtDesc = (TextView) itemView.findViewById(R.id.layout_level_desc);
        btnStatus = (Button) itemView.findViewById(R.id.layout_levels_status);

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
