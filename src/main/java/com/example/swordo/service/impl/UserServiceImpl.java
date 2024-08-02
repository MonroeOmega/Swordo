package com.example.swordo.service.impl;

import com.example.swordo.current.ExtraUserData;
import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.entity.User;
import com.example.swordo.models.entity.UserRoleEnum;
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
    private final ExtraUserData extraUserData;
    private final SwordService swordService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ExtraUserData extraUserData, SwordService swordService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.extraUserData = extraUserData;
        this.swordService = swordService;
        this.encoder = encoder;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        User user = modelMapper.map(userRegisterBindingModel,User.class);
        user.setPassword(encoder.encode(userRegisterBindingModel.getPassword()));
        user.setCoins(0);
        user.setHitpoints(300);
        if(user.getUsername().equals("JimmyOmega")){
            user.setRole(UserRoleEnum.ADMIN);
        }else {
            user.setRole(UserRoleEnum.USER);
        }
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
    public void loadExtraUserData(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        extraUserData.setId(user.getId());
        extraUserData.setEmail(user.getEmail());
        extraUserData.setHitpoints(user.getHitpoints());
        extraUserData.setCoins(user.getCoins());
        extraUserData.setSword(user.getSword());
    }


}
