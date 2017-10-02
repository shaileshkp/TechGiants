package com.example.examinationsystem.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.examinationsystem.R;
import com.example.examinationsystem.constants.Subject;
import com.example.examinationsystem.interfaces.ItemClickListener;
import com.example.examinationsystem.model.Posts;
import com.example.examinationsystem.model.Subjects;
import com.example.examinationsystem.viewholder.PostsViewHolder;
import com.example.examinationsystem.viewholder.SubjectViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SubjectsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference subjects;

    FirebaseRecyclerAdapter<Subjects,SubjectViewHolder> adapter;

    RecyclerView recycler_subjects;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        recycler_subjects = (RecyclerView) findViewById(R.id.subjects_list_subject);
        recycler_subjects.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(SubjectsActivity.this);
        recycler_subjects.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        subjects = database.getReference("Exams");

        loadSubjects();

    }

    private void loadSubjects() {

        adapter = new FirebaseRecyclerAdapter<Subjects, SubjectViewHolder>(
                Subjects.class,
                R.layout.layout_subjects,
                SubjectViewHolder.class,
                subjects
        ) {
            @Override
            protected void populateViewHolder(SubjectViewHolder viewHolder, final Subjects model, int position) {
                viewHolder.txtSubjectName.setText(model.getSubjectName());
                viewHolder.txtDesc.setText(model.getDesc());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(SubjectsActivity.this, ""+model.getSubjectName(), Toast.LENGTH_SHORT).show();
                        Subject.subjectName = model.getSubjectName();
                        Intent intent = new Intent(SubjectsActivity.this, LevelsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recycler_subjects.setAdapter(adapter);
    }
}
