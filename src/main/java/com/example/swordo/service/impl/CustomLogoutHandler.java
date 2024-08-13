package com.example.swordo.service.impl;

import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutHandler implements LogoutHandler {
    private final UserService userService;
    private final BattlefieldMonsterService battlefieldMonsterService;

    public CustomLogoutHandler(UserService userService, BattlefieldMonsterService battlefieldMonsterService) {
        this.userService = userService;
        this.battlefieldMonsterService = battlefieldMonsterService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        userService.saveUserData();
        battlefieldMonsterService.returnCurrentMonster();
    }
}
