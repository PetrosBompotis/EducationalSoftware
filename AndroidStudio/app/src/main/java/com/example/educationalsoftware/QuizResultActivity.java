package com.example.educationalsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {
    private TextView scoreTextView, quizResultsTextView;
    private Integer score;
    private Long quizId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        scoreTextView = findViewById(R.id.score_text);
        quizResultsTextView = findViewById(R.id.quizResultsTextView);

        initializeExtras();
    }

    private void initializeExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getInt("score");
            quizId = extras.getLong("quizId");
        }
        scoreTextView.setText(score + "%");
        setQuizResultMessage(score);
    }

    private void setQuizResultMessage(float score) {
        String message;
        if (score >= 90) {
            message = "Αποτελέσματα Κουίζ\n\nΕξαιρετική δουλειά! \uD83C\uDF1F Είσαι αστέρι!";
        } else if (score >= 75) {
            message = "Αποτελέσματα Κουίζ\n\nΠολύ καλή δουλειά! \uD83D\uDC4D Συνέχισε έτσι!";
        } else if (score >= 50) {
            message = "Αποτελέσματα Κουίζ\n\nΚαλή προσπάθεια! \uD83D\uDCAA Μπορείς να τα πας ακόμα καλύτερα!";
        } else {
            message = "Αποτελέσματα Κουίζ\n\nΜην ανησυχείς! \uD83D\uDE0A Συνέχισε να προσπαθείς και θα βελτιωθείς!";
        }
        quizResultsTextView.setText(message);
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