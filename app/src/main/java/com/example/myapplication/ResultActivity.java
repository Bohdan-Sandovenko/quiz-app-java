package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "score";
    public static final String EXTRA_TOTAL = "total";
    public static final String EXTRA_QUIZ_ID = "quiz_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvCongrats = findViewById(R.id.tvCongrats);
        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvMessage = findViewById(R.id.tvMessage);
        Button btnRestart = findViewById(R.id.btnRestart);
        Button btnExit = findViewById(R.id.btnExit);

        int score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        int total = getIntent().getIntExtra(EXTRA_TOTAL, 0);

        tvCongrats.setText("Congratulations!");
        tvScore.setText("Your score: " + score + " out of " + total);

        String message;
        double percent = total == 0 ? 0 : (double) score / total;

        if (percent == 1.0) {
            message = "Perfect! You answered all questions correctly.";
        } else if (percent >= 0.7) {
            message = "Great result! Keep it up!";
        } else if (percent >= 0.4) {
            message = "Not bad! Try again to improve your score.";
        } else {
            message = "Everyone starts somewhere. Review the material and try again!";
        }

        message += "\n\nThank you for taking the quiz!";

        tvMessage.setText(message);

        String quizId = getIntent().getStringExtra(EXTRA_QUIZ_ID);
        
        btnRestart.setOnClickListener(v -> {
            if (quizId != null) {
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("quiz_id", quizId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnExit.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
