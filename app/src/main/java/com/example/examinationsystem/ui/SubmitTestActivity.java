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

import info.hoang8f.widget.FButton;

public class SubmitTestActivity extends AppCompatActivity {
    TextView txtMarks, lblHeading;
    FButton btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_test);
        txtMarks = (TextView) findViewById(R.id.txtMarks);
        btnDone = (FButton) findViewById(R.id.btnDone);
        lblHeading = (TextView) findViewById(R.id.lblHeading);
        Typeface pacifico = Typeface.createFromAsset(getAssets(),"fonts/Pacifico-Regular.ttf");
        lblHeading.setTypeface(pacifico);

        Intent intent = getIntent();
        txtMarks.setText("You obtained "+ Common.obt +" out of "+ Common.tot+".");
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
