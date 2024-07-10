package com.example.swordo.service.impl;

import com.example.swordo.current.CurrentUser;
import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.entity.User;
import com.example.swordo.models.entity.UserRoleEnum;
import com.example.swordo.models.service.UserServiceModel;
import com.example.swordo.repository.UserRepository;
import com.example.swordo.service.SwordService;
import com.example.swordo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final SwordService swordService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser, SwordService swordService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.swordService = swordService;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        User user = modelMapper.map(userRegisterBindingModel,User.class);
        user.setCoins(0);
        user.setHitpoints(300);
        user.setRole(UserRoleEnum.USER);
        userRepository.save(user);
    }

    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {
        return userRepository.findFirstByUsernameAndPassword(username,password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void loginUser(UserServiceModel userServiceModel) {
        currentUser.setUsername(userServiceModel.getUsername());
        currentUser.setEmail(userServiceModel.getEmail());
        currentUser.setCoins(userServiceModel.getCoins());
        currentUser.setRole(userServiceModel.getRole());
        currentUser.setHitpoints(userServiceModel.getHitpoints());
        if(userServiceModel.getSword() != null) {
            currentUser.setSword(swordService.getSword(userServiceModel.getSword().getId()));
        }
        currentUser.setId(userServiceModel.getId());
    }


}
