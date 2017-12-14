package com.example.examinationsystem.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.examinationsystem.R;
import com.example.examinationsystem.constants.Common;
import com.example.examinationsystem.constants.User;
import com.example.examinationsystem.model.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import info.hoang8f.widget.FButton;

public class StartTest extends AppCompatActivity {
    TextView txtSubject, txtLevel, txtDesc, txtDuration, lblHeading, txtStatus;
    FButton btnStart, btnCancle;
    FirebaseDatabase database;
    Query query;
    String level, desc, duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        btnStart = (FButton) findViewById(R.id.startTest);
        btnStart.setEnabled(false);
        btnCancle = (FButton) findViewById(R.id.cancleTest);
        txtSubject = (TextView) findViewById(R.id.subjectName);
        txtLevel = (TextView) findViewById(R.id.levelName);
        txtDesc = (TextView) findViewById(R.id.desc);
        txtDuration = (TextView) findViewById(R.id.duration);
        lblHeading = (TextView) findViewById(R.id.lblHeading);
        txtStatus = (TextView) findViewById(R.id.txt_start_test_status);
        Typeface pacifico = Typeface.createFromAsset(getAssets(),"fonts/Pacifico-Regular.ttf");
        lblHeading.setTypeface(pacifico);

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
        finish();
    }

    private void loadDetails() {
        level =  getIntent().getStringExtra("level");
        desc =  getIntent().getStringExtra("desc");
        duration =  getIntent().getStringExtra("duration");

        txtSubject.setText("Subject = "+ Common.subjName);
        txtLevel.setText("Level = "+ level);
        txtDesc.setText(desc);
        txtDuration.setText("Duration = "+ duration+" min");

        database = FirebaseDatabase.getInstance();
        query = database.getReference("Result");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(User.userId).child(Common.subjId).child(Common.levelId).exists()) {
                    btnStart.setEnabled(false);
                    Result result = dataSnapshot.child(User.userId).child(Common.subjId).child(Common.levelId).getValue(Result.class);
                    txtStatus.setText("You already take the exam."+"\n"+
                    "You schored : "+result.getScore()+" at \n" + result.getTimestamp());
                } else {
                    btnStart.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
