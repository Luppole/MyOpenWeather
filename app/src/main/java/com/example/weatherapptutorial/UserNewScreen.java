package com.example.weatherapptutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserNewScreen extends AppCompatActivity {

    Button backToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_screen);
        backToSignUp = findViewById(R.id.backToSignUp);
        backToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserNewScreen.this, ActivityRegister.class);
                startActivity(intent);
            }
        });
    }
}

