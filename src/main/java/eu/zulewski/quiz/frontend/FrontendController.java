package eu.zulewski.quiz.frontend;

import eu.zulewski.quiz.services.OngoingGameService;
import eu.zulewski.quiz.services.QuizDataService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log
public class FrontendController {

    private QuizDataService quizDataService;
    private OngoingGameService ongoingGameService;

    public FrontendController(QuizDataService quizDataService, OngoingGameService ongoingGameService) {
        this.quizDataService = quizDataService;
        this.ongoingGameService = ongoingGameService;
    }

    @GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("message", "some message");
        return "index";
    }

    @GetMapping("/select")
    public String select(Model model) {
        model.addAttribute("gameOptions", new GameOptions());
        model.addAttribute("categories", quizDataService.getQuizCategories());
        return "select";
    }

    @PostMapping("/select")
    public String postSelectForm(Model model, @ModelAttribute GameOptions gameOptions) {
        log.info("Form submitted with data: " + gameOptions);
        ongoingGameService.init(gameOptions);
        return "redirect:game";
    }

    @GetMapping("/game")
    public String game(Model model) {
        model.addAttribute("userAnswer", new UserAnswer());
        model.addAttribute("currentQuestionNumber", ongoingGameService.getCurrentQuestionNumber());
        model.addAttribute("totalQuestionNumber", ongoingGameService.getTotalQuestionNumber());
        model.addAttribute("currentQuestion", ongoingGameService.getCurrentQuestion());
        model.addAttribute("currentQuestionAnswers", ongoingGameService.getCurrentQuestionAnswersInRandomOrder());
        return "game";
    }

    @PostMapping("/game")
    public String postSelectForm(Model model, @ModelAttribute UserAnswer userAnswer) {
        ongoingGameService.checkAnswerForCurrentQuestionAndUpdatePoints(userAnswer.getAnswer());
        boolean hasNextQuestion = ongoingGameService.proceedToNextQuestion();
        if (hasNextQuestion) {
            return "redirect:game";
        } else {
            return "redirect:summary";
        }
    }

    @GetMapping("/summary")
    public String summary(Model model) {
        model.addAttribute("difficulty", ongoingGameService.getDifficulty());
        model.addAttribute("categoryName", ongoingGameService.getCategoryName());
        model.addAttribute("points", ongoingGameService.getPoints());
        model.addAttribute("maxPoints", ongoingGameService.getTotalQuestionNumber());
        return "summary";
    }
}
