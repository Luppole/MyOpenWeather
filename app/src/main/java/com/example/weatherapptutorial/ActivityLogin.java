package com.example.weatherapptutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityLogin extends AppCompatActivity {

    DBHelper dbHelper;

    Button btnLogin, btnReturnToSignUp;

    protected EditText etUsername, etPassword;

    public static String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsernameLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DBHelper(this);
        btnReturnToSignUp = findViewById(R.id.returnToSignBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLoggedIn = dbHelper.checkUser(etUsername.getText().toString(), etPassword.getText().toString());
                if(isLoggedIn) {
                    username = etUsername.getText().toString();
                    Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(ActivityLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnReturnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                    startActivity(intent);
            }
        });

    }
}
