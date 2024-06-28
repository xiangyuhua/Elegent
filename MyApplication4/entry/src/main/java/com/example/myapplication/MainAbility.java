package com.example.myapplication;

import com.example.myapplication.slice.MainAbilitySlice;
import com.example.myapplication.slice.loginAbilitySlice;
import com.example.myapplication.slice.registerAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(loginAbilitySlice.class.getName());

        super.addActionRoute("login", loginAbilitySlice.class.getName());
        super.addActionRoute("register", registerAbilitySlice.class.getName());
    }
}
