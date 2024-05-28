package com.example.educationalsoftware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;

public class RecognitionQuizActivity extends AppCompatActivity {
    private TextView welcomeTitle, welcomeText;
    private Button startQuizButton, submitButton;
    private TextView questionText1, questionText2, questionText3;
    private RadioGroup choicesRadioGroup1, choicesRadioGroup2, choicesRadioGroup3;
    private RadioButton choice1_1, choice2_1, choice3_1, choice1_2, choice2_2, choice3_2, choice1_3, choice2_3, choice3_3;
    private Integer counter;
    private static final String SHARED_PREFS_NAME = "MyPreferences";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition_quiz);
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        initViews();
        hideAllExceptWelcomeViews();
    }

    private void initViews() {
        welcomeText = findViewById(R.id.welcome_text);
        welcomeTitle = findViewById(R.id.welcome_title);
        startQuizButton = findViewById(R.id.startQuizButton);
        submitButton = findViewById(R.id.submit_button);

        questionText1 = findViewById(R.id.question_text1);
        questionText2 = findViewById(R.id.question_text2);
        questionText3 = findViewById(R.id.question_text3);

        choicesRadioGroup1 = findViewById(R.id.choices_radio_group1);
        choicesRadioGroup2 = findViewById(R.id.choices_radio_group2);
        choicesRadioGroup3 = findViewById(R.id.choices_radio_group3);

        choice1_1 = findViewById(R.id.choice1_1);
        choice2_1 = findViewById(R.id.choice2_1);
        choice3_1 = findViewById(R.id.choice3_1);

        choice1_2 = findViewById(R.id.choice1_2);
        choice2_2 = findViewById(R.id.choice2_2);
        choice3_2 = findViewById(R.id.choice3_2);

        choice1_3 = findViewById(R.id.choice1_3);
        choice2_3 = findViewById(R.id.choice2_3);
        choice3_3 = findViewById(R.id.choice3_3);

        counter = 0;
    }

    private void hideAllExceptWelcomeViews() {
        questionText1.setVisibility(View.GONE);
        questionText2.setVisibility(View.GONE);
        questionText3.setVisibility(View.GONE);

        choicesRadioGroup1.setVisibility(View.GONE);
        choicesRadioGroup2.setVisibility(View.GONE);
        choicesRadioGroup3.setVisibility(View.GONE);

        choice1_1.setVisibility(View.GONE);
        choice2_1.setVisibility(View.GONE);
        choice3_1.setVisibility(View.GONE);

        choice1_2.setVisibility(View.GONE);
        choice2_2.setVisibility(View.GONE);
        choice3_2.setVisibility(View.GONE);

        choice1_3.setVisibility(View.GONE);
        choice2_3.setVisibility(View.GONE);
        choice3_3.setVisibility(View.GONE);

        submitButton.setVisibility(View.GONE);
    }

    private void showAllExceptWelcomeViews() {
        questionText1.setVisibility(View.VISIBLE);
        questionText2.setVisibility(View.VISIBLE);
        questionText3.setVisibility(View.VISIBLE);

        choicesRadioGroup1.setVisibility(View.VISIBLE);
        choicesRadioGroup2.setVisibility(View.VISIBLE);
        choicesRadioGroup3.setVisibility(View.VISIBLE);

        choice1_1.setVisibility(View.VISIBLE);
        choice2_1.setVisibility(View.VISIBLE);
        choice3_1.setVisibility(View.VISIBLE);

        choice1_2.setVisibility(View.VISIBLE);
        choice2_2.setVisibility(View.VISIBLE);
        choice3_2.setVisibility(View.VISIBLE);

        choice1_3.setVisibility(View.VISIBLE);
        choice2_3.setVisibility(View.VISIBLE);
        choice3_3.setVisibility(View.VISIBLE);

        submitButton.setVisibility(View.VISIBLE);
    }
    public void start(View view) {
        welcomeTitle.setVisibility(View.GONE);
        welcomeText.setVisibility(View.GONE);
        startQuizButton.setVisibility(View.GONE);
        showAllExceptWelcomeViews();
    }

    public void submit(View view) {
        int selectedId = choicesRadioGroup1.getCheckedRadioButtonId();

        if (selectedId != -1) {
            // Get the selected radio button
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedChoice = selectedRadioButton.getText().toString();

            // Check if the selected choice is correct
            if (selectedChoice.contentEquals(choice1_1.getText())) {
                counter += 1;
            }
        }else {
            Toast.makeText(RecognitionQuizActivity.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
        }

        int selectedId2 = choicesRadioGroup2.getCheckedRadioButtonId();

        if (selectedId2 != -1) {
            // Get the selected radio button
            RadioButton selectedRadioButton = findViewById(selectedId2);
            String selectedChoice = selectedRadioButton.getText().toString();

            // Check if the selected choice is correct
            if (selectedChoice.contentEquals(choice1_2.getText())) {
                counter += 1;
            }
        }else {
            Toast.makeText(RecognitionQuizActivity.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
        }

        int selectedId3 = choicesRadioGroup3.getCheckedRadioButtonId();

        if (selectedId3 != -1) {
            // Get the selected radio button
            RadioButton selectedRadioButton = findViewById(selectedId3);
            String selectedChoice = selectedRadioButton.getText().toString();

            // Check if the selected choice is correct
            if (selectedChoice.contentEquals(choice2_3.getText())) {
                counter += 1;
            }
        }else {
            Toast.makeText(RecognitionQuizActivity.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
        }

        updateLevel(counter);

        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    private void updateLevel(int counter) {
        String level;
        switch (counter) {
            case 3:
                level = "HIGH";
                break;
            case 2:
                level = "MEDIUM";
                break;
            default:
                level = "LOW";
                break;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("level", level);
        editor.apply();
    }
}