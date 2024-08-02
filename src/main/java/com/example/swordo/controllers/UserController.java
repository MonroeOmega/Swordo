package com.example.swordo.controllers;

import com.example.swordo.current.ExtraUserData;
import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.service.UserService;
import com.example.swordo.views.UserProfileView;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, ExtraUserData extraUserData) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String confirmRegistration(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()||!userRegisterBindingModel.getRepeatPassword().equals(userRegisterBindingModel.getPassword())){
            redirectAttributes.addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",bindingResult);

            return "redirect:/register";
        }

        userService.registerUser(userRegisterBindingModel);

        return "redirect:login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/login/load")
    public String load(){
        userService.loadExtraUserData(SecurityContextHolder.getContext().getAuthentication().getName());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/login/error")
    public String logError(){
        return "login-error";
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        //Note: The three rows of code below are currently not necessary. Will delete them when I've made sure it's fine to do so.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfileView profileView = userService.getByUsernameForProfile(username);
        model.addAttribute("profileView",profileView);
        return "profile";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }
}
