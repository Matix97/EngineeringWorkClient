package com.example.exchangetoys.ui.fragment;

public class ToyModelToRecycle {
    private String name;
    private String info;
    private int image;

    public ToyModelToRecycle(String n,String i,int img) {
        name = n;info=i;image=img;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }
    public int getImage()
    {
        return image;
    }
}
