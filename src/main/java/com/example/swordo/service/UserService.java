package com.example.swordo.service;

import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.entity.User;
import com.example.swordo.views.AdminUsersView;
import com.example.swordo.views.UserProfileView;

import java.util.List;

public interface UserService {
    void registerUser(UserRegisterBindingModel userRegisterBindingModel);

    User findByUsername(String username);

    void loadExtraUserData(String username);

    void strike();

    void saveUserData();

    void rest();

    void adminCoins();

    void loot();

    void windup();

    List<AdminUsersView> adminView();

    long userCount();

    List<Long> userSwordIds();

    void strikeHim();

    void processDeath();
}
