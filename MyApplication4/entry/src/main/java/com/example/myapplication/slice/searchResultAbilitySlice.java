package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.img_item;
import com.example.myapplication.provider.MyItemProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;

import java.util.List;

public class searchResultAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_search_result);

        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_lc1);

        List<img_item> searchResults = (List<img_item>) intent.getSerializableParam("searchResult");
        MyItemProvider myItemProvider = new MyItemProvider(searchResults, this);
        listContainer.setItemProvider(myItemProvider);
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
