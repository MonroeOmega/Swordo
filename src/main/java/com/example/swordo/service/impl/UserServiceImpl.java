package com.example.swordo.service.impl;

import com.example.swordo.current.CurrentUser;
import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.entity.User;
import com.example.swordo.models.entity.UserRoleEnum;
import com.example.swordo.models.service.UserServiceModel;
import com.example.swordo.repository.UserRepository;
import com.example.swordo.service.SwordService;
import com.example.swordo.service.UserService;
import com.example.swordo.views.UserProfileView;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final SwordService swordService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser, SwordService swordService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.swordService = swordService;
        this.encoder = encoder;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        User user = modelMapper.map(userRegisterBindingModel,User.class);
        user.setPassword(encoder.encode(userRegisterBindingModel.getPassword()));
        user.setCoins(0);
        user.setHitpoints(300);
        user.setRole(UserRoleEnum.USER);
        user.setSword(swordService.getBroken());
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " is not registered with us;"));
    }

    @Override
    public UserProfileView getByUsernameForProfile(String username) {
        return modelMapper.map(userRepository.findByUsername(username),UserProfileView.class);
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
