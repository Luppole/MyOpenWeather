package com.example.weatherapptutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AutomateLogging extends AppCompatActivity {

    Button backToLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automate_logging);
        backToLog = findViewById(R.id.backButtonAutomate);

        backToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutomateLogging.this, OutfitLoggerCalender.class);
                startActivity(intent);
            }
        });

    }
}
