package com.example.weatherapptutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityRegister extends AppCompatActivity {

    DBHelper dbHelper;
    EditText etUsername, etPassword, etRetypePassword;
    Button btnRegister, btnGoToLogin, btnReadAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRetypePassword = findViewById(R.id.etRePassword);
        btnRegister = findViewById(R.id.buttonRegister);
        dbHelper = new DBHelper(this);
        btnReadAbout = findViewById(R.id.goToNewScreen);
        btnGoToLogin = findViewById(R.id.buttonLogin);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(intent);
            }

        });

        btnReadAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityRegister.this, UserNewScreen.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, password, repassword;
                user = etUsername.getText().toString();
                password = etPassword.getText().toString();
                repassword = etRetypePassword.getText().toString();
                if(user.equals("") || password.equals("") || repassword.equals("")) {
                    Toast.makeText(ActivityRegister.this, "All fields should be filled", Toast.LENGTH_SHORT).show();
                }

                else {
                    if(password.equals(repassword)) {
                        if(dbHelper.checkUsername(user)) {
                            Toast.makeText(ActivityRegister.this, "Username already exists", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        boolean registrationStatus = dbHelper.insertData(user, password);
                        if(registrationStatus) {
                            Toast.makeText(ActivityRegister.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(ActivityRegister.this, "User Failed To Register", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(ActivityRegister.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }

}
