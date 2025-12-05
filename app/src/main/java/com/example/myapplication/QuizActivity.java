package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.Quiz;
import com.example.myapplication.data.QuizItem;
import com.example.myapplication.data.QuizStore;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textQuestion;
    private TextView textQuestionNumber;
    private TextView textScore;
    private Button buttonA, buttonB, buttonC, buttonD;
    private Button buttonNext;
    private Button buttonPrevious;
    private int selectedIndex = -1;

    private List<QuizItem> items;
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

        Quiz quiz = loadQuiz();
        items = quiz.getItems();

        showQuestion();

        buttonA.setOnClickListener(v -> selectAnswer(0));
        buttonB.setOnClickListener(v -> selectAnswer(1));
        buttonC.setOnClickListener(v -> selectAnswer(2));
        buttonD.setOnClickListener(v -> selectAnswer(3));

        buttonPrevious.setOnClickListener(v -> finish());

        buttonNext.setOnClickListener(v -> {
            if (selectedIndex == -1) return; // no selection
            if (selectedIndex == items.get(currentIndex).getCorrectIndex()) {
                correctCount++;
            }

            currentIndex++;
            if (currentIndex < items.size()) {
                clearSelection();
                showQuestion();
            } else {
                // quiz finished
                QuizStore.INSTANCE.addResult(new QuizStore.QuizResult(quiz.getId(), correctCount));
                buttonNext.setEnabled(false);
                buttonA.setEnabled(false);
                buttonB.setEnabled(false);
                buttonC.setEnabled(false);
                buttonD.setEnabled(false);
                textQuestion.setText("Finished!");
                textQuestionNumber.setText("");
                textScore.setText("Score: " + correctCount + "/" + items.size());
            }
        });
    }

    private void selectAnswer(int index) {
        selectedIndex = index;
    }

    private void showQuestion() {
        QuizItem item = items.get(currentIndex);
        textQuestion.setText(item.getQuestion());
        textQuestionNumber.setText("Question " + (currentIndex + 1) + " of " + items.size());
        textScore.setText(items.size() + " Questions");

        List<String> answers = item.getAnswers();
        // Ensure we have up to four options; pad with empty strings if shorter.
        buttonA.setText(answers.size() > 0 ? answers.get(0) : "");
        buttonB.setText(answers.size() > 1 ? answers.get(1) : "");
        buttonC.setText(answers.size() > 2 ? answers.get(2) : "");
        buttonD.setText(answers.size() > 3 ? answers.get(3) : "");
    }

    private void clearSelection() {
        selectedIndex = -1;
    }

    private Quiz loadQuiz() {
        String quizId = getIntent().getStringExtra("quiz_id");
        Quiz quiz = QuizStore.INSTANCE.getQuizById(quizId);
        if (quiz == null) {
            return QuizStore.INSTANCE.getQuizzes().get(0);
        }
        return quiz;
    }
}
