package com.example.myapplication.data

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.myapplication.R
import java.util.UUID

data class QuizItem(
    val question: String,
    val answers: List<String>,
    val correctIndex: Int
)

data class Quiz(
    val id: String = UUID.randomUUID().toString(),
    val theme: String,
    @DrawableRes val iconRes: Int,
    val items: List<QuizItem>
)

object QuizStore {
    val quizzes: List<Quiz> = listOf(
        Quiz(
            theme = "HTML",
            iconRes = R.drawable.ic_quiz_html,
            items = listOf(
                QuizItem(
                    question = "What does <h1> represent?",
                    answers = listOf("Top-level heading", "Hyperlink", "Inline code", "Table header"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "Which tag creates a link?",
                    answers = listOf("<a>", "<link>", "<href>", "<url>"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "Which attribute opens a link in new tab?",
                    answers = listOf("target=\"_blank\"", "newtab", "rel=\"open\"", "window=\"new\""),
                    correctIndex = 0
                )
            )
        ),
        Quiz(
            theme = "JavaScript",
            iconRes = R.drawable.ic_quiz_js,
            items = listOf(
                QuizItem(
                    question = "How do you declare a constant?",
                    answers = listOf("const", "var", "let", "static"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "Which array method adds to the end?",
                    answers = listOf("push", "pop", "shift", "unshift"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "What keyword refers to the current object?",
                    answers = listOf("this", "self", "that", "context"),
                    correctIndex = 0
                )
            )
        ),
        Quiz(
            theme = "React",
            iconRes = R.drawable.ic_quiz_react,
            items = listOf(
                QuizItem(
                    question = "What hook manages state?",
                    answers = listOf("useState", "useEffect", "useMemo", "useRef"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "Which prop gives children?",
                    answers = listOf("children", "slots", "content", "body"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "What wraps components for context?",
                    answers = listOf("Provider", "Consumer", "Wrapper", "Context"),
                    correctIndex = 0
                )
            )
        ),
        Quiz(
            theme = "C++",
            iconRes = R.drawable.ic_quiz_cpp,
            items = listOf(
                QuizItem(
                    question = "Which symbol starts a preprocessor directive?",
                    answers = listOf("#", "$", "@", "!"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "Which keyword creates an object on the heap?",
                    answers = listOf("new", "create", "alloc", "malloc"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "What does RAII stand for?",
                    answers = listOf("Resource Acquisition Is Initialization", "Runtime Allocation Is Immediate", "Random Access Iteration Index", "Reference And Inheritance Implementation"),
                    correctIndex = 0
                )
            )
        ),
        Quiz(
            theme = "Python",
            iconRes = R.drawable.ic_quiz_python,
            items = listOf(
                QuizItem(
                    question = "How do you define a function?",
                    answers = listOf("def", "function", "fn", "lambda"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "Which keyword creates a virtual environment (via module)?",
                    answers = listOf("python -m venv", "pip venv", "env create", "virtualenv start"),
                    correctIndex = 0
                ),
                QuizItem(
                    question = "What data type is returned by input()?",
                    answers = listOf("str", "int", "bool", "any"),
                    correctIndex = 0
                )
            )
        )
    )

    fun getQuizById(id: String?): Quiz? = quizzes.firstOrNull { it.id == id }

    // In-memory results; in real app persist to datastore/db.
    data class QuizResult(
        val quizId: String,
        val correctAnswers: Int
    )

    private val _results = mutableStateListOf<QuizResult>()
    val results: SnapshotStateList<QuizResult> get() = _results

    fun addResult(result: QuizResult) {
        _results.removeAll { it.quizId == result.quizId }
        _results.add(0, result) // most recent first
    }

    fun pruneMissingQuizzes() {
        _results.removeAll { res -> getQuizById(res.quizId) == null }
    }
}
