package com.example.swordo.service.impl;

import com.example.swordo.current.CurrentBattlefieldMonster;
import com.example.swordo.current.ExtraUserData;
import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.entity.MonsterClassEnum;
import com.example.swordo.models.entity.User;
import com.example.swordo.models.entity.UserRoleEnum;
import com.example.swordo.repository.UserRepository;
import com.example.swordo.service.SwordService;
import com.example.swordo.service.UserService;
import com.example.swordo.views.AdminUsersView;
import com.example.swordo.views.UserProfileView;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    public int calculateDamage() {
        int criticalRoll = random(1, 100);
        int damage = extraUserData.getSword().getStrength();
        if (criticalRoll <= extraUserData.getSword().getCritChance()) {
            damage *= 2;
        }
        if (extraUserData.getSword().getType() == currentBattlefieldMonster.getMonster().getWeakness()) {
            damage *= 2;
        }
        return damage;
    }

    @Override
    public void registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        User user = modelMapper.map(userRegisterBindingModel, User.class);
        user.setPassword(encoder.encode(userRegisterBindingModel.getPassword()));
        user.setCoins(0);
        user.setHitpoints(300);
        if (user.getUsername().equals("JimmyOmega")) {
            user.setRole(UserRoleEnum.ADMIN);
        } else {
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
                - random(currentBattlefieldMonster.getMonster().getMinStrike(),
                currentBattlefieldMonster.getMonster().getMaxStrike()));
        int damage = calculateDamage();
        currentBattlefieldMonster.setCurrentHitpoints(currentBattlefieldMonster.getCurrentHitpoints() - damage);
        extraUserData.getSword().setDurability(extraUserData.getSword().getDurability() - 1);
        if (extraUserData.getSword().getDurability() <= 0) {
            extraUserData.setSword(swordService.getBroken());
            saveUserData();
        }
    }

    @Override
    public void saveUserData() {
        User user = userRepository.findById(extraUserData.getId()).orElse(null);
        user.setCoins(extraUserData.getCoins());
        user.setHitpoints(extraUserData.getHitpoints());
        user.setSword(extraUserData.getSword());
        userRepository.save(user);
    }

    @Override
    public void rest() {
        extraUserData.setCoins(extraUserData.getCoins() - 50);
        extraUserData.setHitpoints(300);
    }

    @Override
    public void adminCoins() {
        extraUserData.setCoins(extraUserData.getCoins() + 1000);
    }

    @Override
    public void loot() {
        if (currentBattlefieldMonster.getCurrentHitpoints() <= 0) {
            extraUserData.setCoins(extraUserData.getCoins() + currentBattlefieldMonster.getLoot());
            saveUserData();
        }
    }

    @Override
    public void windup() {
        extraUserData.setHitpoints(extraUserData.getHitpoints()
                - random(currentBattlefieldMonster.getMonster().getMinStrike(),
                currentBattlefieldMonster.getMonster().getMaxStrike()));
        int wind = random(1, 100);
        if (wind > 50) {
            int damage = calculateDamage() * 2;
            currentBattlefieldMonster.setCurrentHitpoints(currentBattlefieldMonster.getCurrentHitpoints() - damage);
        }
        extraUserData.getSword().setDurability(extraUserData.getSword().getDurability() - 1);
    }

    @Override
    public List<AdminUsersView> adminView() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, AdminUsersView.class))
                .toList();
    }

    @Override
    public long userCount() {
        return userRepository.count();
    }

    @Override
    public List<Long> userSwordIds() {
        List<Long> ids = new ArrayList<>();
        userRepository.findAll().forEach(user -> ids.add(user.getSword().getId()));
        return ids;
    }

    @Override
    public void strikeHim() {
        int durabilityRow = random(1,20);
        int healthRow = random(1,20);
        if(durabilityRow == 14){
            extraUserData.setSword(swordService.getBroken());
            saveUserData();
        }
        if(healthRow == 14){
            extraUserData.setHitpoints(0);
        }
    }


}
