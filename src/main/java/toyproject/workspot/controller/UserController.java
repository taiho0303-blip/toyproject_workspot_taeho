package toyproject.workspot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.workspot.domain.User;
import toyproject.workspot.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/new")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "user/createUserForm";
    }

    @PostMapping("/user/new")
    public String create(@Valid UserForm form, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user/createUserForm";
        }

        User user = new User();
        user.setName(form.getName());
        user.setNickName(form.getNickName());
        user.setJob(form.getJob());
        user.setGender(form.getGender());
        user.setAgeGroup(form.getAgeGroup());

        userService.join(user);

        redirectAttributes.addAttribute("status", true);
        redirectAttributes.addAttribute("type", "user");
        return "redirect:/";
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "user/users";
    }
}