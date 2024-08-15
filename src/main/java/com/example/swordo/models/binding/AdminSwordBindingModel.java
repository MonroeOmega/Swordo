package com.example.swordo.models.binding;


import com.example.swordo.models.entity.SwordTypeEnum;

public class AdminSwordBindingModel {
    //Note: This seems like a roundabout way. Check for a better one.
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
