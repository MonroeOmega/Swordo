package com.example.swordo.service.impl;

import com.example.swordo.current.CurrentBattlefieldMonster;
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

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ExtraUserData extraUserData;
    private final SwordService swordService;
    private final PasswordEncoder encoder;
    private final CurrentBattlefieldMonster currentBattlefieldMonster;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ExtraUserData extraUserData, SwordService swordService, PasswordEncoder encoder, CurrentBattlefieldMonster currentBattlefieldMonster) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.extraUserData = extraUserData;
        this.swordService = swordService;
        this.encoder = encoder;
        this.currentBattlefieldMonster = currentBattlefieldMonster;
    }

    public int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
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

    @Override
    public void strike() {
        extraUserData.setHitpoints(extraUserData.getHitpoints()
                -random(currentBattlefieldMonster.getMonster().getMinStrike(),
                currentBattlefieldMonster.getMonster().getMaxStrike()));
        int criticalRoll = random(1,100);
        int damage = extraUserData.getSword().getStrength();
        if(criticalRoll <= extraUserData.getSword().getCritChance()){
            damage*=2;
        }
        currentBattlefieldMonster.setCurrentHitpoints(currentBattlefieldMonster.getCurrentHitpoints()-damage);
        extraUserData.getSword().setDurability(extraUserData.getSword().getDurability()-1);
    }

    @Override
    public void saveUserData() {
        User user =  userRepository.findById(extraUserData.getId()).orElse(null);
        user.setCoins(extraUserData.getCoins());
        user.setHitpoints(extraUserData.getHitpoints());
        user.setSword(extraUserData.getSword());
        userRepository.save(user);

    }

    @Override
    public void rest() {
        //Note: Might make it a tad bit more complicated later.
        extraUserData.setHitpoints(300);
    }

    @Override
    public void adminCoins() {
        extraUserData.setCoins(extraUserData.getCoins()+1000);
    }


}
