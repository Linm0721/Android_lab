package com.example.linm.lab3;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ACER on 2017/10/31.
 */

public class GoodsEvent{
    private Map<String, Object> goods = new LinkedHashMap<>();
    public GoodsEvent(Map<String, Object> temp){
        goods = temp;
    }
    public Map<String, Object> getGoods(){
        return goods;
    }
}