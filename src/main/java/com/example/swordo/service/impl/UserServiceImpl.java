package com.example.swordo.service.impl;

import com.example.swordo.current.CurrentBattlefieldMonster;
import com.example.swordo.current.ExtraUserData;
import com.example.swordo.exceptions.DeathException;
import com.example.swordo.exceptions.EmailAlreadyExistsException;
import com.example.swordo.exceptions.UsernameAlreadyExistsException;
import com.example.swordo.models.binding.UserRegisterBindingModel;
import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.User;
import com.example.swordo.models.entity.UserRoleEnum;
import com.example.swordo.repository.UserRepository;
import com.example.swordo.service.BattlefieldMonsterService;
import com.example.swordo.service.SwordService;
import com.example.swordo.service.UserService;
import com.example.swordo.views.AdminUsersView;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ExtraUserData extraUserData;
    private final SwordService swordService;
    private final PasswordEncoder encoder;
    private final CurrentBattlefieldMonster currentBattlefieldMonster;
    private final BattlefieldMonsterService battlefieldMonsterService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ExtraUserData extraUserData, SwordService swordService, PasswordEncoder encoder, CurrentBattlefieldMonster currentBattlefieldMonster, BattlefieldMonsterService battlefieldMonsterService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.extraUserData = extraUserData;
        this.swordService = swordService;
        this.encoder = encoder;
        this.currentBattlefieldMonster = currentBattlefieldMonster;
        this.battlefieldMonsterService = battlefieldMonsterService;
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
        if(userRepository.findFirstByEmail(userRegisterBindingModel.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException();
        }
        if(userRepository.findByUsername(userRegisterBindingModel.getUsername()).isPresent()){
            throw new UsernameAlreadyExistsException();
        }
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
        chipSword();
        if(extraUserData.getHitpoints()<= 0){
            extraUserData.setHitpoints(0);
            battlefieldMonsterService.returnCurrentMonster();
            throw new DeathException();
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
        if(currentBattlefieldMonster.getId() != null){
            int hitpoints = currentBattlefieldMonster.getCurrentHitpoints() + ((currentBattlefieldMonster.getMonster().getMaxHitpoints() - currentBattlefieldMonster.getCurrentHitpoints())/2);
            currentBattlefieldMonster.setCurrentHitpoints(hitpoints);
        }
        extraUserData.setHitpoints(300);
        saveUserData();
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
        chipSword();
        if(extraUserData.getHitpoints()<= 0){
            extraUserData.setHitpoints(0);
            battlefieldMonsterService.returnCurrentMonster();
            throw new DeathException();
        }
    }

    private void chipSword() {
        extraUserData.getSword().setDurability(extraUserData.getSword().getDurability() - 1);
        if (extraUserData.getSword().getDurability() <= 0) {
            Sword sword = swordService.getBroken();
            Long oldId = extraUserData.getSword().getId();
            swordService.saveSword(sword);
            extraUserData.setSword(sword);
            saveUserData();
            swordService.discard(oldId);
        }
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
            Sword sword = swordService.getBroken();
            Long oldId = extraUserData.getSword().getId();
            swordService.saveSword(sword);
            extraUserData.setSword(sword);
            saveUserData();
            swordService.discard(oldId);
        }
        if(healthRow == 14){
            extraUserData.setHitpoints(0);
            battlefieldMonsterService.returnCurrentMonster();
            throw new DeathException();
        }
    }

    @Override
    public void processDeath() {
        if(extraUserData.getHitpoints() == 0) {
            extraUserData.setHitpoints(1);
            if (extraUserData.getCoins() < 50) {
                extraUserData.setCoins(50);
            }
            saveUserData();
        }
    }


}
