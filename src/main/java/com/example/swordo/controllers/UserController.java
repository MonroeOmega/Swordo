package com.example.swordo.controllers;

import com.example.swordo.exceptions.EmailAlreadyExistsException;
import com.example.swordo.exceptions.UsernameAlreadyExistsException;
import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
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

            return "redirect:/user/register";
        }

        userService.registerUser(userRegisterBindingModel);

        return "redirect:/user/login";
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class, EmailAlreadyExistsException.class})
    public String registrationExceptions(RedirectAttributes redirectAttributes, Exception e){
        redirectAttributes.addFlashAttribute("error",true);
        redirectAttributes.addFlashAttribute("reason",e.getMessage());
        return "redirect:/user/register";
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public String loginException(RedirectAttributes redirectAttributes,UsernameNotFoundException e){
        //Note: Doesn't work for some reason. Look into the throwing of UsernameNotFound
        //Stick with the current variant until you find a better way.
        redirectAttributes.addFlashAttribute("error",true);
        redirectAttributes.addFlashAttribute("reason",e.getMessage());
        return "redirect:/user/login";
    }

    @PostMapping("/login/error")
    public String error(){
        return "login-error";
    }

    @GetMapping("/login/load")
    public String load(){
        userService.loadExtraUserData(SecurityContextHolder.getContext().getAuthentication().getName());
        return "redirect:/town";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }
}
