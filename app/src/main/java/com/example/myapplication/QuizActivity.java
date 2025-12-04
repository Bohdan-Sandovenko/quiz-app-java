package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView textQuestion;
    private TextView textQuestionNumber;
    private TextView textScore;
    private Button buttonA, buttonB, buttonC, buttonD;
    private Button buttonNext;
    private Button buttonPrevious;
    private int selectedIndex = -1;

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

        textQuestion = findViewById(R.id.tv_question);
        textQuestionNumber = findViewById(R.id.tv_question_number);
        textScore = findViewById(R.id.tv_total_questions);
        buttonA = findViewById(R.id.btn_answer1);
        buttonB = findViewById(R.id.btn_answer2);
        buttonC = findViewById(R.id.btn_answer3);
        buttonD = findViewById(R.id.btn_answer4);
        buttonNext = findViewById(R.id.btn_next);
        buttonPrevious = findViewById(R.id.btn_previous);

        showQuestion();

        buttonA.setOnClickListener(v -> selectAnswer(0));
        buttonB.setOnClickListener(v -> selectAnswer(1));
        buttonC.setOnClickListener(v -> selectAnswer(2));
        buttonD.setOnClickListener(v -> selectAnswer(3));

        buttonPrevious.setOnClickListener(v -> finish());

        buttonNext.setOnClickListener(v -> {
            if (selectedIndex == -1) return; // no selection
            if (selectedIndex == answers[currentIndex]) {
                correctCount++;
            }

            currentIndex++;
            if (currentIndex < questions.length) {
                clearSelection();
                showQuestion();
            } else {
                // quiz finished
                buttonNext.setEnabled(false);
                buttonA.setEnabled(false);
                buttonB.setEnabled(false);
                buttonC.setEnabled(false);
                buttonD.setEnabled(false);
                textQuestion.setText("Finished!");
                textQuestionNumber.setText("");
                textScore.setText("Score: " + correctCount + "/" + questions.length);
            }
        });
    }

    private void selectAnswer(int index) {
        selectedIndex = index;
    }

    private void showQuestion() {
        textQuestion.setText(questions[currentIndex]);
        textQuestionNumber.setText("Question " + (currentIndex + 1) + " of " + questions.length);
        textScore.setText("Score: " + correctCount + "/" + (currentIndex));

        buttonA.setText(options[currentIndex][0]);
        buttonB.setText(options[currentIndex][1]);
        buttonC.setText(options[currentIndex][2]);
        buttonD.setText(options[currentIndex][3]);
    }

    private void clearSelection() {
        selectedIndex = -1;
    }
}

