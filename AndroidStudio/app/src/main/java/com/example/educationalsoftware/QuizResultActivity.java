package com.example.educationalsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private Float score;
    private Long quizId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        scoreTextView = findViewById(R.id.score_text);

        initializeExtras();
    }

    private void initializeExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getFloat("score");
            quizId = extras.getLong("quizId");
        }
        scoreTextView.setText(String.valueOf(score));
    }

    public void redirectMainMenu(View view){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void retry(View view){
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("quizId", quizId);
        startActivity(intent);
    }
}