package com.example.examinationsystem.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.examinationsystem.R;
import com.example.examinationsystem.constants.Common;
import com.example.examinationsystem.interfaces.ItemClickListener;
import com.example.examinationsystem.model.Levels;
import com.example.examinationsystem.viewholder.LevelsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LevelsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    Query levels;

    FirebaseRecyclerAdapter<Levels,LevelsViewHolder> adapter;

    RecyclerView recycler_levels;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        setTitle(Common.subjName);

        recycler_levels = (RecyclerView) findViewById(R.id.list_levels);
        recycler_levels.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(LevelsActivity.this);
        recycler_levels.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        levels = database.getReference("Levels").orderByChild("subjId").equalTo(Common.subjId);

        loadLevels();
    }

    private void loadLevels() {
        adapter = new FirebaseRecyclerAdapter<Levels, LevelsViewHolder>(
                Levels.class,
                R.layout.layout_levels,
                LevelsViewHolder.class,
                levels
        ) {
            @Override
            protected void populateViewHolder(final LevelsViewHolder viewHolder, final Levels model, int position) {
                viewHolder.txtLevel.setText(model.getLevel());
                viewHolder.txtDesc.setText(model.getDescription());
                viewHolder.txtDuration.setText(model.getDuration()+" min");
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Common.levelId = adapter.getRef(position).getKey();
                        Intent intent = new Intent(LevelsActivity.this, StartTest.class);
                        intent.putExtra("level",model.getLevel());
                        intent.putExtra("desc",model.getDescription());
                        intent.putExtra("duration",model.getDuration());
                        startActivity(intent);
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recycler_levels.setAdapter(adapter);
    }
}
