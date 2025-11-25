package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView textQuestion;
    private RadioGroup radioGroup;
    private RadioButton radioA, radioB, radioC, radioD;
    private Button buttonNext;
    private TextView textScore;

    private String[] questions = new String[]{
            "What is the capital of France?",
            "2 + 2 = ?",
            "Which one is a programming language?"
    };

    private String[][] options = new String[][]{
            {"Paris", "Berlin", "Madrid", "Rome"},
            {"3", "4", "5", "22"},
            {"Banana", "Java", "Car", "Table"}
    };

    private int[] answers = new int[]{0, 1, 1}; // index of correct answers

    private int currentIndex = 0;
    private int correctCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textQuestion = findViewById(R.id.textQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        radioA = findViewById(R.id.radioA);
        radioB = findViewById(R.id.radioB);
        radioC = findViewById(R.id.radioC);
        radioD = findViewById(R.id.radioD);
        buttonNext = findViewById(R.id.buttonNext);
        textScore = findViewById(R.id.textScore);

        showQuestion();

        buttonNext.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                return; // no selection
            }

            int selectedIndex = -1;
            if (selectedId == R.id.radioA) selectedIndex = 0;
            else if (selectedId == R.id.radioB) selectedIndex = 1;
            else if (selectedId == R.id.radioC) selectedIndex = 2;
            else if (selectedId == R.id.radioD) selectedIndex = 3;

            if (selectedIndex == answers[currentIndex]) {
                correctCount++;
            }

            currentIndex++;
            if (currentIndex < questions.length) {
                radioGroup.clearCheck();
                showQuestion();
            } else {
                // quiz finished
                buttonNext.setEnabled(false);
                textQuestion.setText("Finished!");
                textScore.setText("Score: " + correctCount + "/" + questions.length);
            }
        });
    }

    private void showQuestion() {
        textQuestion.setText(questions[currentIndex]);
        radioA.setText(options[currentIndex][0]);
        radioB.setText(options[currentIndex][1]);
        radioC.setText(options[currentIndex][2]);
        radioD.setText(options[currentIndex][3]);
        textScore.setText("Score: " + correctCount + "/" + (currentIndex));
    }
}


