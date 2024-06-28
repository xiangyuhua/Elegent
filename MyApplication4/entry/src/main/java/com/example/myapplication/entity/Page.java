package com.example.myapplication.entity;


public class Page {
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public Page(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Page{" +
                "image=" + image +
                '}';
    }
}