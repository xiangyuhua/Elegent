package com.example.myapplication.slice;

import com.example.myapplication.CartManager;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.img_item;
import com.example.myapplication.provider.MyItemProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;
import ohos.event.notification.NotificationHelper;
import ohos.event.notification.NotificationRequest;
import ohos.event.notification.NotificationSlot;
import ohos.global.resource.NotExistException;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.rpc.RemoteException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cartAbilitySlice extends AbilitySlice {
    private List<img_item> cartItems = new ArrayList<>(); // 购物车商品列表
    private Map<Integer, Integer> itemQuantityMap = new HashMap<>(); // 商品数量映射
    Button home,cart,self;
    private Text totalPriceText;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_shopping_cart);

        home=(Button) findComponentById(ResourceTable.Id_homePageBtn1);
        cart=(Button) findComponentById(ResourceTable.Id_cartBtn2);
        self=(Button) findComponentById(ResourceTable.Id_selfBtn3);
        home.setClickedListener(component->present(new shoppingMainAbilitySlice(),new Intent()));
        cart.setClickedListener(component->present(new cartAbilitySlice(),new Intent()));
        self.setClickedListener(component->present(new selfAbilitySlice(),new Intent()));

        cartItems = CartManager.getInstance().getCartItems();

        totalPriceText = (Text) findComponentById(ResourceTable.Id_totalPriceText);

        updateCartList();

        updateTotalPrice();


        Button checkoutBtn = (Button) findComponentById(ResourceTable.Id_checkoutButton);
        checkoutBtn.setClickedListener(component -> {
            new ToastDialog(getContext()).setText("结算成功").show();
            sendConversation();
            // 清空购物车
            clearCart();
            totalPriceText.setText("总计：¥" + String.format("%.2f", 0.0));
        });
    }
    private void clearCart() {
        cartItems.clear();
        itemQuantityMap.clear();
        updateCartList();
    }
    private void updateCartList() {

        MyItemProvider myItemProvider = new MyItemProvider(cartItems, this);
        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_cartListContainer);
        listContainer.setItemProvider(myItemProvider);

        }


    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (img_item item : cartItems) {
            int quantity = Integer.parseInt(item.getNum());
            totalPrice += Integer.valueOf(item.getPrice()) * quantity;
        }
        totalPriceText.setText("总计：¥" + String.format("%.2f", totalPrice));
    }
    public void sendConversation() {
        NotificationSlot slot = new NotificationSlot("slot", "my_slot", NotificationSlot.LEVEL_DEFAULT);
        slot.setDescription("发布通知");
        try {
            NotificationHelper.addNotificationSlot(slot);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        for (img_item item : cartItems) {
            int notificationId = 1001 + item.getId(); // 确保每个商品通知有不同的ID
            NotificationRequest request = new NotificationRequest(notificationId);
            request.setSlotId(slot.getId());

            NotificationRequest.NotificationPictureContent content = new NotificationRequest.NotificationPictureContent();
            content.setTitle("发货通知")
                    .setText("您购买的【" + item.getName() + "】即将发货")
                    .setExpandedTitle("商品图片")
                    .setBigPicture(getPixelMap(item.getImg_name()));

            NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
            request.setContent(notificationContent);

            try {
                NotificationHelper.publishNotification(request);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private PixelMap getPixelMap(int imgId){
        InputStream inputStream=null;
        try {
            inputStream=getContext().getResourceManager().getResource(imgId);
            ImageSource.SourceOptions sourceOptions=new ImageSource.SourceOptions();
            ImageSource.DecodingOptions decodingOptions=new ImageSource.DecodingOptions();
            ImageSource imageSource=ImageSource.create(inputStream,sourceOptions);
            decodingOptions.desiredPixelFormat= PixelFormat.ARGB_8888;
            return imageSource.createPixelmap(decodingOptions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NotExistException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                }
                catch (IOException e){
                    throw  new RuntimeException(e);
                }
            }
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
