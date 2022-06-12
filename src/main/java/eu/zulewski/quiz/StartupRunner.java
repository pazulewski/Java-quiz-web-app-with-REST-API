package eu.zulewski.quiz;

import eu.zulewski.quiz.database.entities.PlayerEntity;
import eu.zulewski.quiz.database.repositories.PlayerRepository;
import eu.zulewski.quiz.services.QuizDataService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Log
public class StartupRunner implements CommandLineRunner {

    private final PlayerRepository playerRepository;
    private final QuizDataService quizDataService;

    public StartupRunner(PlayerRepository playerRepository, QuizDataService quizDataService) {
        this.playerRepository = playerRepository;
        this.quizDataService = quizDataService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Executing startup actions...");
        playerRepository.save(new PlayerEntity("John"));
        playerRepository.save(new PlayerEntity("Harry"));
        playerRepository.save(new PlayerEntity("Paul"));

        log.info("List of players from database:");
        List<PlayerEntity> playerEntityList = playerRepository.findAll();
        for (PlayerEntity player : playerEntityList) {
            log.info("Retrieved player: " + player);
        }

        quizDataService.getQuizCategories();
    }
}
