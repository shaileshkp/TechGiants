package com.example.examinationsystem.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examinationsystem.R;
import com.example.examinationsystem.constants.Common;
import com.example.examinationsystem.constants.User;
import com.example.examinationsystem.model.Questions;
import com.example.examinationsystem.model.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hoang8f.widget.FButton;

public class TestActivity extends AppCompatActivity {
    FirebaseDatabase database;
    Query questions;
    String subject, level, levelId, duration;
    TextView txtSubject, txtLevel, txtDuration, txtQuesNo, txtQuestion,lblHeading;
    FButton btnSubmit, btnClear, btnNext, btnPrevious;
    RadioButton opt1, opt2, opt3, opt4;
    RadioGroup optGroup;
    List<Questions> questionsList;
    String answers[];
    int marks[];

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

        lblHeading = (TextView) findViewById(R.id.lblHeading);
        Typeface pacifico = Typeface.createFromAsset(getAssets(),"fonts/Pacifico-Regular.ttf");
        lblHeading.setTypeface(pacifico);

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
        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers[position] = "opt1";
            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers[position] = "opt2";
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers[position] = "opt3";
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers[position] = "opt4";
            }
        });

        optGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton) group.findViewById(checkedId);
//                Toast.makeText(TestActivity.this, ""+button.getText(), Toast.LENGTH_SHORT).show();
                if(button.getText().equals(questionsList.get(position).getCorrectAnswer())) {
                    marks[position] = 1;
                }
            }
        });
    }


    private void btnNextClick() {
        if (position < totalQuestions - 1) {
            position++;
            getQuestion(position);
            Toast.makeText(this, ""+marks[position], Toast.LENGTH_SHORT).show();
        } else {
            showAlert("No more question found!");
        }
    }

    private void btnPreviousClick() {
        if (position > 0) {
            position--;
            getQuestion(position);
            Toast.makeText(this, ""+marks[position], Toast.LENGTH_SHORT).show();
        }
    }

    private void btnSubmitClick() {
        int getmarks = 0;
        for(int i = 0; i< totalQuestions; i++)
        {
            if(marks[i] == 1)
                getmarks++;
        }
        Toast.makeText(this, getmarks+"/"+totalQuestions, Toast.LENGTH_SHORT).show();
        Common.obt = getmarks+"";
        Common.tot = totalQuestions+"";
        User.marks += Integer.parseInt(Common.obt);

        DatabaseReference student = database.getReference("Users").child(User.userId);
        com.example.examinationsystem.model.User user = new com.example.examinationsystem.model.User(User.userName, User.userId,User.pass, User.userEmail,User.imageUrl, User.marks);
        student.setValue(user);

        String time = new String(String.valueOf(Calendar.getInstance().getTime()));
        DatabaseReference result = database.getReference("Result").child(User.userId).child(Common.subjId).child(Common.levelId);
        Result rs = new Result(time, Integer.parseInt(Common.obt));
        result.setValue(rs);
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
//                    Toast.makeText(TestActivity.this, ""+totalQuestions, Toast.LENGTH_SHORT).show();
                }
                answers = new String[totalQuestions];
                marks = new int[totalQuestions];
                startTest();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void startTest() {
        if(totalQuestions > 0) {

            txtQuesNo.setText(position+1+ "/ "+totalQuestions);
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

        if(answers[position] != null) {
            switch (answers[position]) {
                case "opt1":
                    opt1.setChecked(true);
//                    Toast.makeText(this, "opt1", Toast.LENGTH_SHORT).show();
                    break;
                case "opt2":
                    opt2.setChecked(true);
//                    Toast.makeText(this, "opt2", Toast.LENGTH_SHORT).show();
                    break;
                case "opt3":
                    opt3.setChecked(true);
//                    Toast.makeText(this, "opt3", Toast.LENGTH_SHORT).show();
                    break;
                case "opt4":
                    opt4.setChecked(true);
//                    Toast.makeText(this, "opt4", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            opt1.setChecked(false);
            opt2.setChecked(false);
            opt3.setChecked(false);
            opt4.setChecked(false);
        }
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
