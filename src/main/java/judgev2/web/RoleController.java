package judgev2.web;

import judgev2.model.binding.RoleAddBindingModel;
import judgev2.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class RoleController {
   private final UserService userService;

    public RoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/roles/add")
    public ModelAndView add(@Valid @ModelAttribute("roleAddBindingModel")RoleAddBindingModel roleAddBindingModel, BindingResult bindingResult,
                            ModelAndView modelAndView ) {

        modelAndView.addObject("usernames", this.userService.findAllUsernames());
        modelAndView.setViewName("role-add");
        return modelAndView;
    }

    @PostMapping("roles/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute("roleAddBindingModel") RoleAddBindingModel roleAddBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:/admin/roles/add");
            redirectAttributes.addFlashAttribute("roleAddBindingModel", roleAddBindingModel);

            return modelAndView;

        }
    this.userService.changeRole(roleAddBindingModel.getUsername(), roleAddBindingModel.getRole());
        modelAndView.setView(new RedirectView("/"));
        return modelAndView;
    }

}
