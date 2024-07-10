package com.example.swordo.service;

import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.service.UserServiceModel;

public interface UserService {
    void registerUser(UserRegisterBindingModel userRegisterBindingModel);

    UserServiceModel findUserByUsernameAndPassword(String username, String password);

    void loginUser(UserServiceModel userServiceModel);
}
