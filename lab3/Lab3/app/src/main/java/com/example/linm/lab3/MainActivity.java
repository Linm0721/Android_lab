package com.example.linm.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private CommonAdapter commonAdapter;
    protected List<Map<String, Object>> data = new ArrayList<>();
    public static List<Map<String, Object>> shoplist = new ArrayList<Map<String, Object>>(){{
        Map<String, Object> t = new LinkedHashMap<>();
        t.put("FirstLetter", "*");
        t.put("name", "购物车");
        t.put("price","价格");
        add(t);
    }

    };
    /*  为每一项数据创建一个对象，并添加在List中  */
    public static List<Info> Infos = new ArrayList<Info>() {{
        add(new Info("Enchated Forest", "¥ 5.00", "作者", "Johanna Basford", "1"));
        add(new Info("Arla Milk", "¥ 59.00", "产地", "德国", "2"));
        add(new Info("Devondale Milk", "¥ 79.00", "产地", "澳大利亚", "3"));
        add(new Info("Kindle Oasis", "¥ 2399.00", "版本", "8GB", "4"));
        add(new Info("waitrose 早餐麦片", "¥ 179.00", "重量", "2Kg", "5"));
        add(new Info("Mcvitie's 饼干", "¥ 14.90 ", "产地", "英国", "6"));
        add(new Info("Ferrero Rocher", "¥ 132.59", "重量", "300g", "7"));
        add(new Info("Maltesers", "¥ 141.43", "重量", "118g", "8"));
        add(new Info("Lindt", "¥ 139.43", "重量", "249g", "9"));
        add(new Info("Borggreve", "¥ 28.90", "重量", "640g", "10"));
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Map<String, Object>> data = new ArrayList<>();

        char[] FirstLetter = new char[Infos.size()];
        for (int i = 0; i < Infos.size(); i++) {
            char x = Infos.get(i).getFirstLetter();
            FirstLetter[i] = x;
        }
        String[] name = new String[Infos.size()];
        for (int i = 0; i < Infos.size(); i++) {
            String x = Infos.get(i).getName();
            name[i] = x;
        }
        String[] price = new String[Infos.size()];
        for (int i = 0; i < Infos.size(); i++) {
            String x = Infos.get(i).getPrice();
            price[i] = x;
        }
        for (int i = 0; i < Infos.size(); i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("FirstLetter", FirstLetter[i]);
            temp.put("name", name[i]);
            data.add(temp);
        }

        /*商品列表*/

        mRecyclerView = (RecyclerView)findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        commonAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.info,data) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s) {
                TextView name = holder.getView(R.id.name);
                name.setText(s.get("name").toString());
                TextView first = holder.getView(R.id.FirstLetter);
                first.setText(s.get("FirstLetter").toString());
            }

        };

        mRecyclerView.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
           @Override
           //商品列表单击
            public  void onClick(int position){
               final int p = position;
               Intent intent = new Intent(MainActivity.this, InfoActivity.class);
               Bundle bundle = new Bundle();
               String productname = data.get(position).get("name").toString();
               bundle.putString("name",productname);
               intent.putExtras(bundle);
               startActivity(intent);
           }
           @Override
           //商品列表长按
            public  void onLongClick( int position){
                if(position < Infos.size()){
                    commonAdapter.removeItem(position);
             //       data.remove(position);
                    Toast.makeText(MainActivity.this, "移除第"+(position+1)+"个商品", Toast.LENGTH_SHORT).show();
                }
            }

        });

        /***********/

        /*购物车 listview实现*/



        final ListView LV = (ListView) findViewById(R.id.list);
        final SimpleAdapter simpleListAdapter = new SimpleAdapter(this, shoplist, R.layout.shoplistinfo,
                new String[]{"FirstLetter","name","price"}, new int[]{R.id.FirstLetter, R.id.name,R.id.price});
        LV.setAdapter(simpleListAdapter);
         /*  ListView单击事件  */
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0) {
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    Bundle bundle = new Bundle();
                    String productname = shoplist.get(i).get("name").toString();
                    bundle.putString("name", productname);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

        });

        /*  ListView长按事件  */
        LV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(position!=0){
                    AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                    message.setTitle("移除商品");
                    message.setMessage("从购物车移除" + shoplist.get(position).get("name").toString());
                    message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {

                            shoplist.remove(position);
                            simpleListAdapter.notifyDataSetChanged();

                        }
                    });
                    message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    message.create().show();
                }

                return true;
            }
        });
        LV.setVisibility(View.GONE);
        /*购物车图标切换*/
        final FloatingActionButton convert = (FloatingActionButton) findViewById(R.id.convert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tag1) {
                    convert.setImageResource(R.mipmap.mainpage);
                    tag1 = true;
                    LV.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);

                } else {
                    convert.setImageResource(R.mipmap.shoplist);
                    tag1 = false;
                    LV.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    private boolean tag1 = false;
}

