package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.QuizStore

@Composable
fun HomeScreen(
    onStartQuiz: (quizId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    QuizStore.pruneMissingQuizzes()
    val quizzes = QuizStore.quizzes
    val results = QuizStore.results

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        CategoriesSection(quizzes = quizzes, onStartQuiz = onStartQuiz)
        RecentActivitySection(results = results, onStartQuiz = onStartQuiz)
    }
}
