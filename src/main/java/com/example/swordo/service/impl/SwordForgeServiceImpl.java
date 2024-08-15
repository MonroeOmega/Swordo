package com.example.swordo.service.impl;

import com.example.swordo.current.ExtraUserData;
import com.example.swordo.models.entity.Sword;
import com.example.swordo.models.entity.SwordForge;
import com.example.swordo.models.entity.SwordInMaking;
import com.example.swordo.models.entity.SwordTypeEnum;
import com.example.swordo.repository.SwordForgeRepository;
import com.example.swordo.service.SwordForgeService;
import com.example.swordo.service.SwordInMakingService;
import com.example.swordo.service.SwordService;
import com.example.swordo.service.UserService;
import com.example.swordo.views.SwordShopView;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SwordForgeServiceImpl implements SwordForgeService {
    private final SwordForgeRepository swordForgeRepository;
    private final SwordInMakingService swordInMakingService;
    private final SwordService swordService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ExtraUserData extraUserData;

    public SwordForgeServiceImpl(SwordForgeRepository swordForgeRepository, SwordInMakingService swordInMakingService, SwordService swordService, UserService userService, ModelMapper modelMapper, ExtraUserData extraUserData) {
        this.swordForgeRepository = swordForgeRepository;
        this.swordInMakingService = swordInMakingService;
        this.swordService = swordService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.extraUserData = extraUserData;
    }

    @Override
    public void forgeSword(SwordTypeEnum type) {
        SwordInMaking swor = swordInMakingService.getSwordInMakingByType(type);
        Sword sword = new Sword();
        sword.setStrength(random(swor.getMinStrength(),swor.getMaxStrength()));
        sword.setDurability(random(swor.getMinDurability(), swor.getMaxDurability()));
        sword.setCritChance(random(swor.getMinCritChance(),swor.getMaxCritChance()));
        sword.setType(type);
        swordService.saveSword(sword);
        SwordForge swordForge = new SwordForge();
        swordForge.setSwordInMaking(swor);
        swordForge.setSword(sword);
        swordForge.setPrice(price(sword));
        swordForgeRepository.save(swordForge);
    }

    public int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    @Override
    public void initSwords() {
        if(swordForgeRepository.count() != 0){
            return;
        }
        addSwordSets(1);
    }

    @Override
    public void addSwordSets(int sets) {
        if(sets <= 0){
            return;
        }
        IntStream.range(0, sets)
                .forEach( check -> Arrays.stream(SwordTypeEnum.values())
                        .forEach(swordTypeEnum -> {
                            if (swordTypeEnum != SwordTypeEnum.BROKEN_SWORD){
                                forgeSword(swordTypeEnum);
                            }
                        }));
    }



    @Override
    public int price(Sword sword) {
        int price = 100;
        SwordInMaking swordInMaking = swordInMakingService.getSwordInMakingByType(sword.getType());

        price+= 10 * (sword.getCritChance() - swordInMaking.getMinCritChance());
        price+= 5 * (sword.getStrength() - swordInMaking.getMinStrength());
        price+= 2 * (sword.getDurability() - swordInMaking.getMinDurability());

        double modifier = 0.1;
        switch (sword.getType()){
            case DAGGER -> modifier = 0.8;
            case SHORTSWORD -> modifier = 1.0;
            case LONGSWORD -> modifier = 1.5;
            case GREATSWORD -> modifier = 2.0;
        }
        price = (int) (price * modifier);

        return price;
    }

    @Override
    public List<SwordShopView> shopView() {
        if(swordForgeRepository.count() < 4){
            addSwordSets(2);
        }
        return swordForgeRepository
                .findAll()
                .stream()
                .map(sword -> modelMapper.map(sword,SwordShopView.class))
                .collect(Collectors.toList());
    }

    @Override
    public void buySword(Long id) {
        SwordForge swordForge = swordForgeRepository.findById(id).orElse(null);
        if(swordForge.getPrice() <= extraUserData.getCoins()){
            Long oldId = extraUserData.getSword().getId();
            swordForgeRepository.deleteById(id);
            extraUserData.setSword(swordForge.getSword());
            extraUserData.setCoins(extraUserData.getCoins() - swordForge.getPrice());
            userService.saveUserData();
            swordService.discard(oldId);
        }
    }

    @Override
    public void buyRandom() {
        if(extraUserData.getCoins() > 200) {
            extraUserData.setCoins(extraUserData.getCoins() - 200);
            SwordTypeEnum randType = SwordTypeEnum.random();
            SwordInMaking swordInMaking = swordInMakingService.getSwordInMakingByType(randType);
            Sword sword = new Sword();
            sword.setType(randType);
            if (sword.getType() == SwordTypeEnum.BROKEN_SWORD) {
                sword.setStrength(swordInMaking.getMinStrength());
                sword.setDurability(swordInMaking.getMinDurability());
                sword.setCritChance(swordInMaking.getMinCritChance());
            } else {
                sword.setStrength(random(swordInMaking.getMinStrength(), swordInMaking.getMaxStrength()));
                sword.setDurability(random(swordInMaking.getMinDurability(), swordInMaking.getMaxDurability()));
                sword.setCritChance(random(swordInMaking.getMinCritChance(), swordInMaking.getMaxCritChance()));
            }
            swordService.saveSword(sword);
            Long oldId = extraUserData.getSword().getId();
            extraUserData.setSword(sword);
            userService.saveUserData();
            swordService.discard(oldId);
        }
    }

    @Override
    public boolean upgradableCrit() {
        return extraUserData.getSword().getCritChance() <= swordInMakingService.getSwordInMakingByType(extraUserData.getSword().getType()).getMaxCritChance();
    }

    @Override
    public boolean upgradableDamage() {
        return extraUserData.getSword().getStrength() <= swordInMakingService.getSwordInMakingByType(extraUserData.getSword().getType()).getMaxStrength();
    }

    @Override
    public boolean fixable() {

        SwordTypeEnum type = extraUserData.getSword().getType();
        return type != SwordTypeEnum.BROKEN_SWORD && extraUserData.getSword().getDurability() <= swordInMakingService.getSwordInMakingByType(type).getMinDurability();
    }

    @Override
    public void upgradeDamage() {
        extraUserData.getSword().setStrength(extraUserData.getSword().getStrength() + 10);
        extraUserData.setCoins(extraUserData.getCoins()-100);
        userService.saveUserData();
    }

    @Override
    public void fix() {
        if(extraUserData.getSword().getType() != SwordTypeEnum.BROKEN_SWORD){
            int check = random(1,20);
            if (check >= 11) {
                extraUserData.getSword().setDurability(extraUserData.getSword().getDurability() + 10);
            }else {
                extraUserData.getSword().setDurability(extraUserData.getSword().getDurability() - 5);
                if(extraUserData.getSword().getDurability() <= 0){
                    extraUserData.setSword(swordService.getBroken());
                    userService.saveUserData();
                }
            }
        }
    }

    @Override
    public void upgradeCrit() {
        extraUserData.getSword().setCritChance(extraUserData.getSword().getCritChance() + 10);
        extraUserData.setCoins(extraUserData.getCoins()-100);
        userService.saveUserData();
    }

    @Override
    public long swordsInShop() {
        return swordForgeRepository.count();
    }

    @Override
    public void adminSword(SwordTypeEnum swordType) {
        SwordInMaking swor = swordInMakingService.getSwordInMakingByType(swordType);
        Sword sword = new Sword();
        sword.setType(swor.getType());
        sword.setDurability(swor.getMaxDurability());
        sword.setStrength(swor.getMaxStrength());
        sword.setCritChance(swor.getMaxCritChance());
        extraUserData.setSword(sword);
        userService.saveUserData();
    }

    @Override
    public long getLooseSwordCount() {
         long count = 0;
         count = swordService.swordCount() - swordForgeRepository.count() - userService.userCount();
        return count;
    }

    @Override
    public void adminRemoveLooseSwords() {
        List<Long> ids = new ArrayList<>();
        swordForgeRepository.findAll().forEach(user -> ids.add(user.getSword().getId()));
        ids.addAll(userService.userSwordIds());
        List<Long> theIds = new ArrayList<>();
        swordService.getAll().forEach(sword -> {
            if(!ids.contains(sword.getId())){
                theIds.add(sword.getId());
            }
        });
        swordService.adminDelete(theIds);
    }


}
