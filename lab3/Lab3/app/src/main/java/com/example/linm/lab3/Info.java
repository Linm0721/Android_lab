package com.example.linm.lab3;

/**
 * Created by ACER on 2017/10/22.
 */

import java.io.Serializable;

/*  存储相应的数据  */
public class Info implements Serializable {

    private String name;
    private String price;
    private String type;
    private String info;
    private String background;

    public Info(String name, String price, String type, String info, String background) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.info = info;
        this.background = background;
    }


    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
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

    public void setInfo(String info) {this.info = info;}

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {this.price = price;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public char getFirstLetter() {
        char first = name.charAt(0);
        if (first >= 97 && first <= 122) {
            first -= 32;
        }
        return first;

    }


}