package com.example.myapplication;

import com.example.myapplication.entity.img_item;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<img_item> cartItems= new ArrayList<>();

    private CartManager() {

    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(img_item item) {
        cartItems.add(item);
    }

    public List<img_item> getCartItems() {
        return cartItems;
    }
}
