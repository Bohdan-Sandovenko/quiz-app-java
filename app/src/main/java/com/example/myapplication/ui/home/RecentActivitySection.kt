package com.example.myapplication.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.myapplication.R
import com.example.myapplication.data.Quiz
import com.example.myapplication.data.QuizStore

@Composable
fun RecentActivitySection(
    results: List<QuizStore.QuizResult>,
    onStartQuiz: (quizId: String) -> Unit
) {
    Text(
        text = "Recent Activity",
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 12.dp)
    )

    if (results.isEmpty()) {
        Text(
            text = "No recent activity.",
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.text_gray)
        )
        return
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        results.forEach { result ->
            val quiz = QuizStore.getQuizById(result.quizId) ?: return@forEach
            RecentActivityItem(
                quiz = quiz,
                correctAnswers = result.correctAnswers,
                totalQuestions = quiz.items.size,
                onClick = { onStartQuiz(quiz.id) }
            )
        }
    }
}

@Composable
fun RecentActivityItem(
    quiz: Quiz,
    correctAnswers: Int,
    totalQuestions: Int,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bottom_nav_bg)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Image(
                        painter = painterResource(id = quiz.iconRes),
                        contentDescription = quiz.theme,
                        modifier = Modifier
                            .padding(10.dp)
                            .height(40.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text = quiz.theme,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Text(
                        text = "$totalQuestions Question${if (totalQuestions == 1) "" else "s"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_gray)
                    )
                }
            }
            ProgressCircle(
                correct = correctAnswers,
                total = totalQuestions
            )
        }
    }
}

@Composable
fun ProgressCircle(correct: Int, total: Int) {
    val progress = if (total == 0) 0f else correct.toFloat() / total.toFloat()
    val percent = (progress * 100).toInt()
    val color = when {
        percent < 35 -> Color(0xFFD32F2F) // red
        percent < 70 -> Color(0xFFF9A825) // orange
        else -> Color(0xFF2E7D32) // green
    }
    Box(
        modifier = Modifier
            .size(54.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = progress,
            color = color,
            trackColor = color.copy(alpha = 0.15f),
            strokeWidth = 6.dp,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = "$correct/$total",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}
