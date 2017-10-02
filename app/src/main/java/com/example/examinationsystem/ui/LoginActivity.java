package com.example.examinationsystem.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.examinationsystem.R;
import com.example.examinationsystem.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText etName,etPass,etPhno,etEmail;
    MaterialEditText etLoginPass,etLoginPhno;

    Button signUp, signIn;

    FirebaseDatabase database;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        etLoginPhno = (MaterialEditText) findViewById(R.id.edtLoginPhNo);
        etLoginPass = (MaterialEditText) findViewById(R.id.edtLoginPass);

        signIn = (Button) findViewById(R.id.btn_sign_in);
        signUp = (Button) findViewById(R.id.btn_sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(etLoginPhno.getText().toString().trim(),
                        etLoginPass.getText().toString().trim());
            }
        });
    }

    private void signIn(final String phNo, final String pass) {
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please wait..");
        dialog.show();
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phNo).exists()) {
                    if (!phNo.isEmpty()) {
                        User login = dataSnapshot.child(phNo).getValue(User.class);
                        if (login.getPass().equals(pass)) {
                            com.example.examinationsystem.constants.User.userId = login.getPhNo();
                            com.example.examinationsystem.constants.User.userName = login.getName();
                            com.example.examinationsystem.constants.User.imageUrl = login.getImageUrl();
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login success.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "User not exist.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
    }

    private void showSignUpDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Sign Up");
        builder.setMessage("Please fill the details");
        LayoutInflater inflater = this.getLayoutInflater();
        View signup_layout = inflater.inflate(R.layout.signup_layout, null);
        etName = (MaterialEditText) signup_layout.findViewById(R.id.edtName);
        etPass = (MaterialEditText) signup_layout.findViewById(R.id.edtPass);
        etPhno = (MaterialEditText) signup_layout.findViewById(R.id.edtPhNo);
        etEmail = (MaterialEditText) signup_layout.findViewById(R.id.edtEmail);

        builder.setView(signup_layout);
        builder.setIcon(R.drawable.ic_account_circle_black_24dp);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final User user = new User(etName.getText().toString().trim(),
                        etPhno.getText().toString().trim(),
                        etPass.getText().toString().trim(),
                        etEmail.getText().toString().trim(),"http://findicons.com/files/icons/61/dragon_soft/256/user.png");
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getPhNo()).exists()) {
                            Toast.makeText(LoginActivity.this, "This phone no. is already exist.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            users.child(user.getPhNo()).setValue(user);
                            Toast.makeText(LoginActivity.this, "User registered.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
