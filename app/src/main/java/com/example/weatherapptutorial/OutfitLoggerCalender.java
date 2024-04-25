package com.example.weatherapptutorial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.widget.TextView;

public class OutfitLoggerCalender extends AppCompatActivity {

    CalendarView calenderView;

    TextView showDate, outfitText;

    EditText getOutfit;

    Calendar calender;

    String username;

    DBHelper datestable;


    Button confirm, removeDataButton, directToAutomate, backToMainBtn;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datestable = new DBHelper(this);
        setContentView(R.layout.activity_outfit_logger);
        calenderView = findViewById(R.id.calenderView);
        calender = Calendar.getInstance();
        showDate = findViewById(R.id.showDate);
        username = ActivityLogin.username;
        outfitText = findViewById(R.id.outfitText);
        getOutfit = findViewById(R.id.getOutfit);
        directToAutomate = findViewById(R.id.open_camera_button);
        removeDataButton = findViewById(R.id.remove_data_button);
        confirm = findViewById(R.id.confirm_button);
        backToMainBtn = findViewById(R.id.backToMainBtn);

        backToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OutfitLoggerCalender.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getDate();
        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                outfitText.setText("You Have No Outfits That Day");
                date = String.valueOf(day) + "/" + String.valueOf((month+1)) + "/" + year;
                showDate.setText("Selected Date: " + date);
                if(!datestable.getValueForDate(username, date).isEmpty()) {
                    outfitText.setText("You Wore A " + datestable.getValueForDate(username, date));

                }


                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        datestable.insertUserDate(username.toString(), date.toString(), getOutfit.getText().toString());
                        Log.i("TAG", username);
                        Log.i("TAG", date);
                        Log.i("TAG", getOutfit.getText().toString());
                        getOutfit.setText("");
                    }
                });

                removeDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(datestable.removeUserDate(username.toString(), date.toString())) {
                            Toast.makeText(OutfitLoggerCalender.this,"Removed Data Successfully",Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(OutfitLoggerCalender.this,"There Is Nothing To Remove",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }

    public void setDate(int day, int month, int year) {
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, month - 1);
        calender.set(Calendar.DAY_OF_MONTH, day);
        long milliseconds = calender.getTimeInMillis();
        calenderView.setDate(milliseconds);
    }

    public String getDate() {
        long date = calenderView.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        calender.setTimeInMillis(date);
        String selected_date = simpleDateFormat.format(calender.getTime());
        return selected_date;
    }
}
