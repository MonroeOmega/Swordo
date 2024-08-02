package com.example.swordo.service;

import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.entity.User;
import com.example.swordo.views.UserProfileView;

public interface UserService {
    void registerUser(UserRegisterBindingModel userRegisterBindingModel);

    User findByUsername(String username);

    UserProfileView getByUsernameForProfile(String username);

    void loadExtraUserData(String username);
}
