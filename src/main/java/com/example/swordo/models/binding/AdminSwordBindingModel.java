package com.example.swordo.models.binding;


import com.example.swordo.models.entity.SwordTypeEnum;

public class AdminSwordBindingModel {
    private SwordTypeEnum type;
    public AdminSwordBindingModel() {
    }
    public SwordTypeEnum getType() {
        return type;
    }

    public void setType(SwordTypeEnum type) {
        this.type = type;
    }
}
