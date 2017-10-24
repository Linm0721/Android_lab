package com.example.linm.lab3;

/**
 * Created by ACER on 2017/10/22.
 */
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.linm.lab3.MainActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.linm.lab3.R.mipmap.back;



public class InfoActivity extends AppCompatActivity {
    private Map<String, Info> mProductDetailsMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Top);
     /**** new ****/
        String [] names = new String[] {"Enchated Forest", "Arla Milk", "Devondale Milk",
                "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干",
                "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
        String [] prices = new String[] {"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00",
                "¥ 179.00", "¥ 14.90", "¥ 132.59", "¥ 141.43",
                "¥ 139.43", "¥ 28.90"};
        String [] types = new String[] {"作者", "产地", "产地", "版本", "重量", "产地", "重量",
                "重量", "重量", "重量"};
        String [] infos = new String[] {"Johanna Basford", "德国", "澳大利亚", "8GB", "2Kg",
                "英国", "300g", "118g", "249g", "640g"};
        String [] background = new String[]{"1","2","3","4","5","6","7","8","9","10"};

        for(int i=0; i < names.length; i++){
            Info temp = new Info(names[i],prices[i], types[i], infos[i], background[i]);
            mProductDetailsMap.put(names[i], temp);
        }

        Bundle bundle = getIntent().getExtras();
        String ProductName = bundle.getString("name");
        final Info p = mProductDetailsMap.get(ProductName);


     /**** new end ****/





/********  original     **********/
/*
        final Info p = (Info) getIntent().getSerializableExtra("Info");*/

        ImageView img = (ImageView)findViewById(R.id.img);

        if(p.getBackground().equals("1")){
            img.setImageResource(R.drawable.enchatedforest);
        }
        else if(p.getBackground().equals("2")){
            img.setImageResource(R.drawable.arla);
        }
        else if(p.getBackground().equals("3")){
            img.setImageResource(R.drawable.devondale);
        }
        else if(p.getBackground().equals("4")){
            img.setImageResource(R.drawable.kindle);
        }
        else if(p.getBackground().equals("5")){
            img.setImageResource(R.drawable.waitrose);
        }
        else if(p.getBackground().equals("6")){
            img.setImageResource(R.drawable.mcvitie);
        }
        else if(p.getBackground().equals("7")){
            img.setImageResource(R.drawable.ferrero);
        }
        else if(p.getBackground().equals("8")){
            img.setImageResource(R.drawable.maltesers);
        }
        else if(p.getBackground().equals("9")){
            img.setImageResource(R.drawable.lindt);
        }
        else if(p.getBackground().equals("10")){
            img.setImageResource(R.drawable.borggreve);
        }


        Button back = (Button) findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView info1 = (TextView) findViewById(R.id.info1);
           info1.setText(p.getinfo1());

        TextView info2 = (TextView) findViewById(R.id.info2);
          info2.setText(p.getFrom());

        TextView name = (TextView) findViewById(R.id.Name);
          name.setText(p.getName());

        TextView tel = (TextView) findViewById(R.id.telephone);
         tel.setText(p.getTel());

        /********  original    end **********/


       String[] operations1 = new String[]{"更多产品信息"};
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.more, operations1);
        ListView listView1 = (ListView) findViewById(R.id.more);
        listView1.setAdapter(arrayAdapter1);

        String[] operations2 = new String[]{"一键下单", "分享商品", "不感兴趣", "查看更多商品促销信息"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.more, operations2);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(arrayAdapter);

        /*  星星的切换 */
       final Button star = (Button) findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tag) {
                    star.setBackgroundResource(R.mipmap.full_star);
                    tag = true;
                } else {
                    star.setBackgroundResource(R.mipmap.empty_star);
                    tag = false;
                }
            }
        });

        /*购物车*/
        Button shopcart = (Button) findViewById(R.id.chat);
        shopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("cycle", p.getcycle());
                temp.put("name",p.getName());
                temp.put("price",p.getTel());
                MainActivity.shoplist.add(temp);
                Toast.makeText(InfoActivity.this, "商品已添加到购物车", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean tag = false;
}