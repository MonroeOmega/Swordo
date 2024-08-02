package com.example.swordo.views;

import com.example.swordo.models.entity.Sword;

public class SwordShopView {
    private Long id;
    private Sword sword;
    private Integer price;

    public SwordShopView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sword getSword() {
        return sword;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
