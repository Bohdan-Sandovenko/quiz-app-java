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

        tvCongrats.setText("Gratulacje!");
        tvScore.setText("Twój wynik: " + score + " z " + total);

        String message;
        double percent = total == 0 ? 0 : (double) score / total;

        if (percent == 1.0) {
            message = "Perfekcyjnie! Odpowiedziałeś poprawnie na wszystkie pytania.";
        } else if (percent >= 0.7) {
            message = "Świetny wynik! Tak trzymaj!";
        } else if (percent >= 0.4) {
            message = "Nieźle! Spróbuj jeszcze raz, aby poprawić wynik.";
        } else {
            message = "Każdy kiedyś zaczyna. Powtórz materiał i spróbuj ponownie!";
        }

        message += "\n\nDziękujemy za udział w teście!";

        tvMessage.setText(message);

        btnRestart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnExit.setOnClickListener(v -> finishAffinity());
    }
}
