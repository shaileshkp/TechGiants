package com.example.examinationsystem.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.examinationsystem.R;
import com.example.examinationsystem.constants.Common;
import com.example.examinationsystem.constants.User;

public class ProfileActivity extends AppCompatActivity {
    TextView txtStudName, txtPhNo, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(User.userName);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtStudName = (TextView) findViewById(R.id.txtsStudentName);
        txtPhNo = (TextView) findViewById(R.id.txtPhNo);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        txtStudName.setText(User.userName);
        txtEmail.setText(User.userEmail);
        txtPhNo.setText(User.userId);
    }
}
