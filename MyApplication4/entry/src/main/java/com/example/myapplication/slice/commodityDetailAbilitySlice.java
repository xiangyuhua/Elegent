package com.example.myapplication.slice;

import com.example.myapplication.CartManager;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.img_item;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.data.DatabaseHelper;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.preferences.Preferences;
import ohos.data.resultset.ResultSet;
import ohos.utils.net.Uri;

public class commodityDetailAbilitySlice extends AbilitySlice {
    private DataAbilityHelper dataAbilityHelper;
    Text name,price;
    Image image;
    private img_item product;
    private String selectedSize = "L";

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_commodity_detail);

        name=(Text) findComponentById(ResourceTable.Id_productName) ;
        price=(Text) findComponentById(ResourceTable.Id_productPrice) ;
        image=(Image)findComponentById(ResourceTable.Id_productImage);

        dataAbilityHelper= DataAbilityHelper.creator(this);
        Uri uri=Uri.parse("dataability:///com.example.myapplication.commodityDataAbility/cloth");

        String[] columns= {"clothId","clothName","clothImg","clothPrice"};
        DataAbilityPredicates dataAbilityPredicates=new DataAbilityPredicates();
        int clothId=1;
        String clothName = null,clothImg = null,clothPrice = null;
        try {
            ResultSet rs = dataAbilityHelper.query(uri,columns,dataAbilityPredicates);
            int rowCount=rs.getRowCount();
            if(rowCount>0){
                rs.goToFirstRow();
                do{
                    clothId=rs.getInt(0);
                    clothName=rs.getString(1);
                    clothImg=rs.getString(2);
                    clothPrice=rs.getString(3);
                    if(clothId==intent.getIntParam("clothId",1)){
                        name.setText(clothName);
                        price.setText(clothPrice);
                        image.setPixelMap(Integer.parseInt(clothImg));
                        break;
                    }

                }while (rs.goToNextRow());
            }
        } catch (DataAbilityRemoteException e) {
            throw new RuntimeException(e);
        }
        RadioContainer sizeRadioContainer = (RadioContainer) findComponentById(ResourceTable.Id_sizeSelect);
        sizeRadioContainer.setMarkChangedListener((radioButton, index) -> {
            selectedSize=((RadioButton) sizeRadioContainer.getComponentAt(index)).getText();
        });

        product=new img_item(clothName,clothPrice,Integer.parseInt(clothImg),clothId,selectedSize,"1");

        Button addToCartButton = (Button) findComponentById(ResourceTable.Id_addToCartBtn);

        addToCartButton.setClickedListener(component -> addToCart());

    }



    private void addToCart() {
        if (product != null) {
            CartManager.getInstance().addToCart(product);
            new ToastDialog(getContext()).setText("已添加到购物车").show();
        }
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
