package com.example.linm.lab3;

/**
 * Created by ACER on 2017/10/22.
 */

import java.io.Serializable;

/*  存储相应的数据  */
public class Info implements Serializable {

    public Info(String name, String tel, String info1, String from, String background) {
        this.background = background;
        this.info1 = info1;
        this.from = from;
        this.name = name;
        this.tel = tel;

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getinfo1() {
        return info1;
    }

    public void setinfo1(String info1) {
        this.info1 = info1;
    }

    public char getcycle() {
        char first = name.charAt(0);
        if (first >= 97 && first <= 122) {
            first -= 32;
        }
        return first;
    }

    private String background;
    private String info1;
    private String from;
    private String name;
    private String tel;

}