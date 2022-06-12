package eu.zulewski.quiz.frontend;

import lombok.Data;

@Data
public class GameOptions {
    private int numberOfQuestions = 10;
    private Difficulty difficulty;
    private int categoryId;
}
