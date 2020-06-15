package judgev2.web;

import judgev2.model.binding.ExerciseAddBindingModel;
import judgev2.model.service.ExerciseServiceModel;
import judgev2.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ModelMapper modelMapper;

    public ExerciseController(ExerciseService exerciseService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/exercises/add")
    public String add(@Valid @ModelAttribute("exerciseAddBindingModel") ExerciseAddBindingModel exerciseAddBindingModel,
                      BindingResult bindingResult) {
        return "exercise-add";
    }

    @PostMapping("/exercises/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("exerciseAddBindingModel") ExerciseAddBindingModel exerciseAddBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("exerciseAddBindingModel", exerciseAddBindingModel);
            modelAndView.setViewName("redirect:/exercises/add");
        } else {
            this.exerciseService.
                    addExercise(this.modelMapper.map(exerciseAddBindingModel, ExerciseServiceModel.class));
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

}
