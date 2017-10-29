package com.example.examinationsystem.ui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examinationsystem.R;
import com.example.examinationsystem.model.Questions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class TestActivity extends AppCompatActivity {
    FirebaseDatabase database;
    Query questions;
    String subject, level, levelId, duration;
    TextView txtSubject, txtLevel, txtDuration, txtQuesNo, txtQuestion;
    FButton btnSubmit, btnClear, btnNext, btnPrevious;
    RadioButton opt1, opt2, opt3, opt4;
    RadioGroup optGroup;
    List<Questions> questionsList;

    int totalQuestions=0;
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        txtSubject = (TextView) findViewById(R.id.txtSubjName);
        txtLevel = (TextView) findViewById(R.id.txtLevel);
        txtDuration = (TextView) findViewById(R.id.txtTime);
        txtQuesNo = (TextView) findViewById(R.id.txtQuestionNo);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);

        btnSubmit = (FButton) findViewById(R.id.btnSubmit);
        btnClear = (FButton) findViewById(R.id.btnClear);
        btnNext = (FButton) findViewById(R.id.btnNext);
        btnPrevious = (FButton) findViewById(R.id.btnPrevious);

        optGroup = (RadioGroup) findViewById(R.id.radioGroup);

        opt1 = (RadioButton) findViewById(R.id.opt1);
        opt2 = (RadioButton) findViewById(R.id.opt2);
        opt3 = (RadioButton) findViewById(R.id.opt3);
        opt4 = (RadioButton) findViewById(R.id.opt4);

        questionsList = new ArrayList<>();

        getRecievedData();
        loadBasicFormData();
        getQuestionsData();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitClick();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClearClick();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNextClick();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPreviousClick();
            }
        });

    }

    private void btnNextClick() {
        if (position < totalQuestions - 1) {
            position++;
            getQuestion(position);
        } else {
            showAlert("No more question found!");
        }
    }

    private void btnPreviousClick() {
        if (position > 0) {
            position--;
            getQuestion(position);
        }
    }

    private void btnSubmitClick() {

        Intent intent = new Intent(TestActivity.this, SubmitTestActivity.class);
        startActivity(intent);
        finish();
    }

    private void btnClearClick() {
        opt1.setChecked(false);
        opt2.setChecked(false);
        opt3.setChecked(false);
        opt4.setChecked(false);
    }

    private void loadBasicFormData() {
        txtSubject.setText(subject);
        txtLevel.setText(level);
        txtDuration.setText(duration);
        txtQuesNo.setText("");
        txtQuestion.setText("");
        opt1.setText("");
        opt2.setText("");
        opt3.setText("");
        opt4.setText("");
    }

    private void getRecievedData() {
        level =  getIntent().getStringExtra("level");
        levelId =  getIntent().getStringExtra("levelId");
        duration =  getIntent().getStringExtra("duration");
        subject =  getIntent().getStringExtra("subjName");
    }


    private void getQuestionsData() {
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions").orderByChild("levelId").equalTo(levelId);

        questions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Questions questions = snapshot.getValue(Questions.class);
                    questionsList.add(questions);
                    totalQuestions++;
                    Toast.makeText(TestActivity.this, ""+totalQuestions, Toast.LENGTH_SHORT).show();
                }
                startTest();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void startTest() {
        if(totalQuestions > 0) {

            txtQuesNo.setText("1 / "+totalQuestions);
            getQuestion(position);
        }
        else {
            displayToast("Questions are not uploaded.");
            finish();
        }
    }

    private void getQuestion(int position) {
        Questions questions = questionsList.get(position);
        txtQuesNo.setText(position+1+" / "+totalQuestions);
        txtQuestion.setText(questions.getQuestion());
        opt1.setText(questions.getAnswer1());
        opt2.setText(questions.getAnswer2());
        opt3.setText(questions.getAnswer3());
        opt4.setText(questions.getAnswer4());
    }



    private void showAlert(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setMessage(s);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void displayToast(String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }

}
