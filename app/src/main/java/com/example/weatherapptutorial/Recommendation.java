package com.example.weatherapptutorial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Recommendation extends AppCompatActivity {

    private TextView apiResponseTextView;
    private Button getRecButton, backToMain, gotoOutfitcalenderButton;

    private OkHttpClient client = new OkHttpClient();

    private TextView temp, type;

    String strTemp, strType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recommendation);
        View v = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        getRecButton = findViewById(R.id.request_ai_rec_button);
        temp = v.findViewById(R.id.temperature);
        type = v.findViewById(R.id.weatherCondition);
        strTemp = temp.getText().toString();
        strType = type.getText().toString();
        // Initialize UI components
        apiResponseTextView = findViewById(R.id.recommendation_text);
        backToMain = findViewById(R.id.backToMain);
        gotoOutfitcalenderButton = findViewById(R.id.goto_outfitcalender_button);

        gotoOutfitcalenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Recommendation.this, OutfitLoggerCalender.class);
                startActivity(intent);
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Recommendation.this, MainActivity.class);
                startActivity(intent);
            }
        });


        getRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType,
                        "{\"message\": \"It's" + strTemp + "temperature outside, " + MainActivity.temp + "and the type of the weather is " + MainActivity.type + ". What do you think I should wear? CHOOSE ONE!!! DO NOT EXPLAIN!!! answer in one sentence and one sentence only. No further information needed\", \"conversation_id\": null, \"tone\": \"BALANCED\", \"markdown\": false, \"photo_url\": null}");
                Log.i("TAG", strTemp + " " + strType);
                Request request = new Request.Builder()
                        .url("https://copilot5.p.rapidapi.com/copilot")
                        .post(body)
                        .addHeader("content-type", "application/json/media")
                        .addHeader("X-RapidAPI-Key", "24ee723fb5msh3d693838d458541p10a238jsna0e087283885")
                        .addHeader("X-RapidAPI-Host", "copilot5.p.rapidapi.com")
                        .build();

                // Execute API call asynchronously
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Handle the error
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            apiResponseTextView.setText("Error: " + e.getMessage());
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // Process the response
                        final String apiResponse = response.body().string();

                        String relevantMessage = extractMessageContent(apiResponse);

                        // Update UI on the main thread
                        runOnUiThread(() -> {

                            apiResponseTextView.setText(relevantMessage);
                        });
                    }

                    public String extractMessageContent (String jsonResponse){
                        int startIndex = jsonResponse.indexOf("\"message\":") + "\"message\":".length() + 1;
                        int endIndex = jsonResponse.indexOf("\",", startIndex);

                        return jsonResponse.substring(startIndex, endIndex);
                    }
                });

            }
        });
    }

}