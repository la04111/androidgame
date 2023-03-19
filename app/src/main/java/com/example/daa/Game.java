package com.example.daa;

import android.app.Application;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private List<Question> questions;
    private int numberCorrect;
    private int numberIcorrect;
    private int totalQuestions;
    private int score;
    private Question currentQuestion;


    public Game() {
        numberCorrect = 0;
        numberIcorrect = 0;
        totalQuestions = 0;
        score = 0;
        currentQuestion = new Question(10);
        questions = new ArrayList<Question>();
    }


    public void makeNewQuestion() {
        currentQuestion = new Question(totalQuestions * 2 + 5);
        totalQuestions++;
        questions.add(currentQuestion);

    }


    public boolean checkAnswer(int submittedAnswer) {
        boolean isCorrect;
        if (currentQuestion.getAnswer() == submittedAnswer) {
            numberCorrect++;
            //random score
            Random random = new Random();
            int buff = random.nextInt(4);
            if (buff == 0) {
                score += 10;
            }else if (buff == 1) {
                score += 15;
            }else if (buff == 2) {
                score += 20;
            }else if (buff == 3) {
                score += 28;
            }
            //

            isCorrect = true;
        } else {
            numberIcorrect++;
            if(score  > 0 ){
                score -= 10;
                if(score < 0 )
                {
                    score =0;
                }
            }else
            {
                score =0;
            }
            isCorrect = false;
        }
//        if (score >= 0) {
//            score = numberCorrect * 10 - numberIcorrect * 10;
//            if (score < 0) {
//                score = 0;
//                numberCorrect = numberCorrect;
//                numberIcorrect += 1;
//            }
//        }
        return isCorrect;
    }


    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public int getNumberIcorrect() {
        return numberIcorrect;
    }

    public void setNumberIcorrect(int numberIcorrect) {
        this.numberIcorrect = numberIcorrect;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

}
