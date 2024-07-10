package com.example.swordo.controllers;

import com.example.swordo.models.binding.UserLoginBindingModel;
import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.service.UserServiceModel;
import com.example.swordo.service.UserService;
import jakarta.validation.Valid;
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

            return "redirect:/register";
        }

        userService.registerUser(userRegisterBindingModel);

        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(Model model){
        if(!model.containsAttribute("isFound")){
            model.addAttribute("isFound",true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String confirmLogin(@Valid UserLoginBindingModel userLoginBindingModel
            , BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userLoginBindingModel",userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:login";
        }

        UserServiceModel userServiceModel = userService.findUserByUsernameAndPassword(
                userLoginBindingModel.getUsername(),userLoginBindingModel.getPassword());

        if(userServiceModel == null){
            redirectAttributes.addFlashAttribute("userLoginBindingModel",userLoginBindingModel);
            redirectAttributes.addFlashAttribute("isFound",false);
            return "redirect:login";
        }

        userService.loginUser(userServiceModel);

        return "redirect:/";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }
}
