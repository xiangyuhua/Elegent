package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.utils.net.Uri;

public class selfAbilitySlice extends AbilitySlice {
    Button home,cart,self;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_page_self);

        home=(Button) findComponentById(ResourceTable.Id_homePageBtn1);
        cart=(Button) findComponentById(ResourceTable.Id_cartBtn2);
        self=(Button) findComponentById(ResourceTable.Id_selfBtn3);
        home.setClickedListener(component->present(new shoppingMainAbilitySlice(),new Intent()));
        cart.setClickedListener(component->present(new cartAbilitySlice(),new Intent()));
        self.setClickedListener(component->present(new selfAbilitySlice(),new Intent()));

        DataAbilityHelper dataAbilityHelper = DataAbilityHelper.creator(this);
        Uri uri=Uri.parse("dataability:///com.example.myapplication.UserDataAbility/users");
        String[] columns={"userTel","userPwd","userId"};

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
