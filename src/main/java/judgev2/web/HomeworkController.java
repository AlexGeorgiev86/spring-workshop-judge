package judgev2.web;

import judgev2.model.binding.CommentAddBindingModel;
import judgev2.model.binding.HomeworkAddBindingModel;
import judgev2.model.service.CommentServiceModel;
import judgev2.model.service.HomeworkServiceModel;
import judgev2.model.service.UserServiceModel;
import judgev2.model.view.HomeworkViewModel;
import judgev2.service.CommentService;
import judgev2.service.ExerciseService;
import judgev2.service.HomeworkService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/homework")
public class HomeworkController {

    private final ExerciseService exerciseService;
    private final ModelMapper modelMapper;
    private final HomeworkService homeworkService;
    private final CommentService commentService;

    public HomeworkController(ExerciseService exerciseService, ModelMapper modelMapper, HomeworkService homeworkService, CommentService commentService) {
        this.exerciseService = exerciseService;
        this.modelMapper = modelMapper;
        this.homeworkService = homeworkService;
        this.commentService = commentService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("exercises", this.exerciseService.findAllExercises());
        if (!model.containsAttribute("homework")) {
            model.addAttribute("homework", new HomeworkAddBindingModel());
        }
        return "homework-add";
    }

    @PostMapping("/add")
    public ModelAndView addPost(@Valid @ModelAttribute("homework") HomeworkAddBindingModel homeworkAddBindingModel,
                                BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes ra,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors() || homeworkAddBindingModel.getExercise().equals("Select exercise")) {
            ra.addFlashAttribute("homework", homeworkAddBindingModel);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.homework", bindingResult);
            modelAndView.setViewName("redirect:/homework/add");
        } else {
            if (this.exerciseService.findByName(homeworkAddBindingModel.getExercise()).getDueDate().isBefore(LocalDateTime.now())) {
                ra.addFlashAttribute("youLate", true);
                modelAndView.setViewName("redirect:/homework/add");
            } else {
                this.homeworkService.addHomework(this.modelMapper.map(homeworkAddBindingModel, HomeworkServiceModel.class),
                        (UserServiceModel) httpSession.getAttribute("user"));
                modelAndView.setViewName("redirect:/");
            }
        }
        return modelAndView;
    }

    @GetMapping("/check")
    public String check(Model model) {

        if (!model.containsAttribute("comment")) {
            model.addAttribute("comment", new CommentAddBindingModel());
        }

        HomeworkViewModel homeworkViewModel = this.modelMapper
                .map(this.homeworkService.findOneWithLowestComments(), HomeworkViewModel.class);
        model.addAttribute("homework", homeworkViewModel);
        return "homework-check";
    }

    @PostMapping("/check")
    public String checkPost(@Valid @ModelAttribute("comment") CommentAddBindingModel commentAddBindingModel, BindingResult bindingResult,
                            HttpSession httpSession, RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.comment", bindingResult);
            ra.addFlashAttribute("comment", commentAddBindingModel);
            return "redirect:check";

        } else {
            CommentServiceModel commentServiceModel = this.modelMapper
                    .map(commentAddBindingModel, CommentServiceModel.class);

            commentServiceModel.setAuthor((UserServiceModel) httpSession.getAttribute("user"));
            this.commentService.addComment(commentServiceModel);
            System.out.println();

            return "redirect:/";
        }
    }

}
