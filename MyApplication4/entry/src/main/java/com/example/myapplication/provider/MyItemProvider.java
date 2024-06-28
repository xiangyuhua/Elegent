package com.example.myapplication.provider;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.img_item;
import com.example.myapplication.slice.cartAbilitySlice;
import com.example.myapplication.slice.searchResultAbilitySlice;
import com.example.myapplication.slice.shoppingMainAbilitySlice;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.data.search.SearchResult;

import java.util.List;

public class MyItemProvider extends RecycleItemProvider {
    private List<img_item> list;
    private AbilitySlice slice;

    public MyItemProvider(List<img_item> list, AbilitySlice slice) {
        this.list = list;
        this.slice = slice;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer componentContainer) {

        img_item item = list.get(position);
        if(slice instanceof cartAbilitySlice)
        {
            convertComponent = LayoutScatter.getInstance(slice)
                    .parse(ResourceTable.Layout_ability_cart_item, null, false);
            Text text = (Text) convertComponent.findComponentById(ResourceTable.Id_commodity_tName);
            text.setText(list.get(position).getName());
            text = (Text) convertComponent.findComponentById(ResourceTable.Id_commodity_Price);
            text.setText(list.get(position).getPrice());
            Image Img=(Image) convertComponent.findComponentById(ResourceTable.Id_commodity_img);
            Img.setPixelMap(list.get(position).getImg_name());
            text =(Text) convertComponent.findComponentById(ResourceTable.Id_commodity_Size);
            text.setText(list.get(position).getSize());

            Text num=(Text) convertComponent.findComponentById(ResourceTable.Id_commodity_num);
            Button increaseBtn = (Button) convertComponent.findComponentById(ResourceTable.Id_increaseBtn);
            increaseBtn.setClickedListener(component -> {
                int quantity = Integer.valueOf(num.getText()) + 1;
                item.setNum(String.valueOf(quantity));
                num.setText(quantity);
            });

            Button decreaseButton = (Button) convertComponent.findComponentById(ResourceTable.Id_decreaseBtn);
            decreaseButton.setClickedListener(component -> {
                int quantity =Integer.valueOf(num.getText());
                if (quantity>1)
                {
                    quantity-=  1;
                    item.setNum(String.valueOf(quantity));
                    num.setText(quantity);
                }


            });
        }
        else
        {
            convertComponent = LayoutScatter.getInstance(slice)
                    .parse(ResourceTable.Layout_ability_img_item, null, false);
            Text text = (Text) convertComponent.findComponentById(ResourceTable.Id_cloth_name);
            text.setText(list.get(position).getName());
            text = (Text) convertComponent.findComponentById(ResourceTable.Id_cloth_price);
            text.setText(list.get(position).getPrice());
            Image Img=(Image) convertComponent.findComponentById(ResourceTable.Id_img1);
            Img.setPixelMap(list.get(position).getImg_name());
        }
        return convertComponent;
    }


}