tvCongrats.setText("Gratulacje!");

tvScore.setText("Twój wynik: " + score + " z " + total);

String message;
double percent = (double) score / total;

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
