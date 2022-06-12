package eu.zulewski.quiz.services;

import eu.zulewski.quiz.dto.QuestionsDto;
import eu.zulewski.quiz.frontend.GameOptions;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log
public class OngoingGameService {
    private GameOptions gameOptions;
    private int currentQuestionIndex;
    private int points;

    private List<QuestionsDto.QuestionDto> questions;

    private QuizDataService quizDataService;

    public OngoingGameService(QuizDataService quizDataService) {
        this.quizDataService = quizDataService;
    }

    public void init(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
        this.currentQuestionIndex = 0;
        this.points = 0;

        quizDataService.getQuizQuestions(gameOptions);
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionIndex + 1;
    }

    public int getTotalQuestionNumber() {
        return questions.size();
    }

    public String getCurrentQuestion() {
        QuestionsDto.QuestionDto question = questions.get(currentQuestionIndex);
        return question.getQuestion();
    }

    public List<String> getCurrentQuestionAnswersInRandomOrder() {
        QuestionsDto.QuestionDto question = questions.get(currentQuestionIndex);

        List<String> answers = new ArrayList<>();
        answers.add(question.getCorrectAnswer());
        answers.addAll(question.getIncorrectAnswers());

        Collections.shuffle(answers);
        return answers;
    }

    public boolean checkAnswerForCurrentQuestionAndUpdatePoints(String userAnswer) {
        QuestionsDto.QuestionDto question = questions.get(currentQuestionIndex);
        boolean correct = question.getCorrectAnswer().equals(userAnswer);
        if (correct) {
            points++;
        }
        return correct;
    }

    public boolean proceedToNextQuestion() {
        currentQuestionIndex++;
        return currentQuestionIndex <questions.size();
    }

}
