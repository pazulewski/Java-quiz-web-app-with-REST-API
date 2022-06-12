package eu.zulewski.quiz.frontend;

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

    public FrontendController(QuizDataService quizDataService) {
        this.quizDataService = quizDataService;
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
    public String select(Model model, @ModelAttribute GameOptions gameOptions) {
        log.info("Form submitted with data: " + gameOptions);
        return "index";
    }
}
