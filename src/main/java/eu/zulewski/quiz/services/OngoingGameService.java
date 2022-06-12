package eu.zulewski.quiz.services;

import eu.zulewski.quiz.dto.QuestionsDto;
import eu.zulewski.quiz.frontend.GameOptions;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

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

        quizDataService.getQuizQuestions();
    }

}
