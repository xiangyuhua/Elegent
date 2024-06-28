package com.example.myapplication.entity;

public class img_item{
    public img_item(String name, String price, int img_name,int id) {
        this.name = name;
        this.price = price;
        this.img_name = img_name;
        this.id=id;
    }
    public img_item(String name, String price, int img_name,int id,String size) {
        this.name = name;
        this.price = price;
        this.img_name = img_name;
        this.id=id;
        this.size=size;
    }
    public img_item(String name, String price, int img_name,int id,String size,String num) {
        this.name = name;
        this.price = price;
        this.img_name = img_name;
        this.id=id;
        this.size=size;
        this.num=num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImg_name() {
        return img_name;
    }

    public void setImg_name(int img_name) {
        this.img_name = img_name;
    }

    private String name;
    private String price;
    private int img_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String size;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    private String num;

}