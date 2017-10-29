package com.example.examinationsystem.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.examinationsystem.R;
import com.example.examinationsystem.constants.Common;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class StartTest extends AppCompatActivity {
    TextView txtSubject, txtLevel, txtDesc, txtDuration;
    Button btnStart, btnCancle;
    FirebaseDatabase database;
    Query query;
    String level, desc, duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        btnStart = (Button) findViewById(R.id.startTest);
        btnCancle = (Button) findViewById(R.id.cancleTest);
        txtSubject = (TextView) findViewById(R.id.subjectName);
        txtLevel = (TextView) findViewById(R.id.levelName);
        txtDesc = (TextView) findViewById(R.id.desc);
        txtDuration = (TextView) findViewById(R.id.duration);
        loadDetails();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExam();
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startExam() {
        Intent intent = new Intent(StartTest.this, TestActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("levelId", Common.levelId);
        intent.putExtra("duration", duration);
        intent.putExtra("subjName", Common.subjName);
        startActivity(intent);
    }

    private void loadDetails() {
        level =  getIntent().getStringExtra("level");
        desc =  getIntent().getStringExtra("desc");
        duration =  getIntent().getStringExtra("duration");

        txtSubject.setText("Subject = "+ Common.subjName);
        txtLevel.setText("Level = "+ level);
        txtDesc.setText(desc);
        txtDuration.setText("Duration = "+ duration+" min");
    }
}
