package com.example.linm.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GoodsInfoActivity extends AppCompatActivity {
    DynamicReceiver dynamicReceiver;
    private Map<String, Info> mProductDetailsMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        // RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.Top);
        //lab4
        IntentFilter filter = new IntentFilter();
        filter.addAction("panhouye");
        dynamicReceiver = new DynamicReceiver();
        //注册广播接收
        registerReceiver(dynamicReceiver,filter);

        //lab4

        for(int i=0; i<MainActivity.Infos.size(); i++){
            mProductDetailsMap.put(MainActivity.Infos.get(i).getName(),MainActivity.Infos.get(i));
        }

        Bundle bundle = getIntent().getExtras();
        String ProductName = bundle.getString("name");
        Log.e("receiveproduct",ProductName);
        final Info p = mProductDetailsMap.get(ProductName);

        //设置图片
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

        //设置名字
        TextView name = (TextView) findViewById(R.id.Name);
        name.setText(p.getName());
        //设置价格
        TextView price = (TextView) findViewById(R.id.price);
        price.setText(p.getPrice());
        //设置类型
        TextView type = (TextView) findViewById(R.id.info1);
        type.setText(p.getType());
        //设置信息
        TextView info = (TextView) findViewById(R.id.info2);
        info.setText(p.getInfo());

        //设置返回图标
        Button back = (Button) findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



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
        Button shopcart = (Button) findViewById(R.id.shopcar);
        shopcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("FirstLetter", p.getFirstLetter());
                temp.put("name",p.getName());
                temp.put("price",p.getPrice());
                MainActivity.shoplist.add(temp);
                MainActivity.simpleListAdapter.notifyDataSetChanged();
                Toast.makeText(GoodsInfoActivity.this, "商品已添加到购物车", Toast.LENGTH_SHORT).show();

                //lab4

                Intent intent = new Intent();
                intent.setAction("panhouye");
                intent.putExtra("name",p.getName());
                sendBroadcast(intent);


            }
        });
    }

    private boolean tag = false;
    protected void onPause() {
        super.onPause();

        unregisterReceiver(dynamicReceiver);
    }
}
class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentTitle("马上下单")
                .setContentText(intent.getStringExtra("name")+"已经添加到购物车")
                .setAutoCancel(true);
        switch (intent.getStringExtra("name")){
            case "Arla Milk":{
                builder.setSmallIcon(R.drawable.arla)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.arla));
                break;
            }
            case "Borggreve":{
                builder.setSmallIcon(R.drawable.borggreve)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.borggreve));
                break;
            }
            case "Devondale Milk":{
                builder.setSmallIcon(R.drawable.devondale)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.devondale));
                break;
            }
            case "Enchated Forest":{
                builder.setSmallIcon(R.drawable.enchatedforest)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.enchatedforest));
                break;
            }
            case "Ferrero Rocher":{
                builder.setSmallIcon(R.drawable.ferrero)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ferrero));
                break;
            }
            case "Kindle Oasis":{
                builder.setSmallIcon(R.drawable.kindle)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.kindle));
                break;
            }
            case "Lindt":{
                builder.setSmallIcon(R.drawable.lindt)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.lindt));
                break;
            }
            case "Maltesers":{
                builder.setSmallIcon(R.drawable.maltesers)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.maltesers));
                break;
            }
            case "Mcvitie's 饼干":{
                builder.setSmallIcon(R.drawable.mcvitie)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.mcvitie));
                break;
            }
            case "waitrose 早餐麦片":{
                builder.setSmallIcon(R.drawable.waitrose)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.waitrose));
                break;
            }
        }
        Notification notify = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int uniqueId = (int) System.currentTimeMillis();
        manager.notify(uniqueId,notify);
    }
}