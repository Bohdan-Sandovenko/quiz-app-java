package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.Quiz;
import com.example.myapplication.data.QuizItem;
import com.example.myapplication.data.QuizStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textQuestion;
    private TextView textQuestionNumber;
    private TextView textScore;
    private TextView textTheme;
    private Button buttonA, buttonB, buttonC, buttonD;
    private Button[] answerButtons;
    private Button buttonNext;
    private ImageButton buttonBack;
    private int selectedIndex = -1;

    private Quiz quiz;
    private List<QuizItem> items;
    private int currentIndex = 0;
    private int correctCount = 0;
    private int[] currentAnswerMapping;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textQuestion = findViewById(R.id.tv_question);
        textQuestionNumber = findViewById(R.id.tv_question_number);
        textScore = findViewById(R.id.tv_total_questions);
        textTheme = findViewById(R.id.tv_theme);
        buttonA = findViewById(R.id.btn_answer1);
        buttonB = findViewById(R.id.btn_answer2);
        buttonC = findViewById(R.id.btn_answer3);
        buttonD = findViewById(R.id.btn_answer4);
        answerButtons = new Button[]{buttonA, buttonB, buttonC, buttonD};
        buttonNext = findViewById(R.id.btn_next);
        buttonBack = findViewById(R.id.btn_back);

        quiz = loadQuiz();
        items = quiz.getItems();
        
        textTheme.setText(quiz.getTheme());

        showQuestion();

        buttonA.setOnClickListener(v -> selectAnswer(0));
        buttonB.setOnClickListener(v -> selectAnswer(1));
        buttonC.setOnClickListener(v -> selectAnswer(2));
        buttonD.setOnClickListener(v -> selectAnswer(3));

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        buttonNext.setOnClickListener(v -> {
            if (selectedIndex == -1) return;
            if (currentAnswerMapping == null || selectedIndex >= currentAnswerMapping.length) return;
            int originalIndex = currentAnswerMapping[selectedIndex];
            if (originalIndex == items.get(currentIndex).getCorrectIndex()) {
                correctCount++;
            }

            currentIndex++;
            if (currentIndex < items.size()) {
                clearSelection();
                showQuestion();
            } else {
                QuizStore.INSTANCE.addResult(new QuizStore.QuizResult(quiz.getId(), correctCount));
                Intent resultIntent = new Intent(this, ResultActivity.class);
                resultIntent.putExtra(ResultActivity.EXTRA_SCORE, correctCount);
                resultIntent.putExtra(ResultActivity.EXTRA_TOTAL, items.size());
                resultIntent.putExtra(ResultActivity.EXTRA_QUIZ_ID, quiz.getId());
                startActivity(resultIntent);
                finish();
            }
        });
    }

    private void selectAnswer(int index) {
        selectedIndex = index;
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setSelected(i == index);
        }
    }

    private void showQuestion() {
        QuizItem item = items.get(currentIndex);
        textQuestion.setText(item.getQuestion());
        textQuestionNumber.setText("Question " + (currentIndex + 1) + " of " + items.size());
        textScore.setText(items.size() + " Questions");

        List<String> answers = item.getAnswers();
        
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < answers.size() && i < 4; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);
        
        currentAnswerMapping = new int[4];
        for (int i = 0; i < indices.size(); i++) {
            currentAnswerMapping[i] = indices.get(i);
        }
        
        buttonA.setText(indices.size() > 0 ? answers.get(indices.get(0)) : "");
        buttonB.setText(indices.size() > 1 ? answers.get(indices.get(1)) : "");
        buttonC.setText(indices.size() > 2 ? answers.get(indices.get(2)) : "");
        buttonD.setText(indices.size() > 3 ? answers.get(indices.get(3)) : "");
        
        clearSelection();
    }

    private void clearSelection() {
        selectedIndex = -1;
        for (Button button : answerButtons) {
            button.setSelected(false);
        }
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
