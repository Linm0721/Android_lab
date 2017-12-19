package com.example.linm.lab8;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.linm.lab8.MyDB.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    public List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    private ListView LV;
    public SimpleAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LV = (ListView) findViewById(R.id.list);
        dataUpdate(); //根据数据库的内容更新listview

        final Button addbutton = (Button)findViewById(R.id.add); //添加条目按钮
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivityForResult(intent, 9);

            }
        });


        //listview单击事件
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                View newView = factory.inflate(R.layout.dialoglayout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final TextView nameTV = (TextView) newView.findViewById(R.id.editname);
                final  EditText birthET = (EditText) newView.findViewById(R.id.editbirth);
                final EditText giftET = (EditText) newView.findViewById(R.id.editgift);
                final TextView phoneTV = (TextView) newView.findViewById(R.id.editphone);
                phoneTV.setText("无");
                nameTV.setText(datas.get(i).get("name"));
                birthET.setText(datas.get(i).get("birth"));
                giftET.setText(datas.get(i).get("gift"));

                //联系电话

                Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);

                while(cursor.moveToNext() ){
                    int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    String name = cursor.getString(nameIndex);
                    if(name.equals(nameTV.getText().toString())){
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        Cursor phoneNumbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                                + contactId, null, null);
                        String strPhoneNumber = "";
                        if (phoneNumbers.moveToNext())
                        {
                            strPhoneNumber = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }

                        phoneNumbers.close();
                        phoneTV.setText(strPhoneNumber);
                    }

                }
                cursor.close();



                // 自定义对话框
                builder.setView(newView);
                builder.setTitle("改啥( • ̀ω•́ )✧");
                builder.setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDB db = new MyDB(getBaseContext());
                        db.update(nameTV.getText().toString(),birthET.getText().toString(),giftET.getText().toString());
                        dataUpdate();
                    }
                });
                builder.setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }

        });

        // listview的长按事件
        LV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);

                message.setMessage("是否删除");
                message.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除
                        MyDB db = new MyDB(getBaseContext());
                        db.delete(datas.get(position).get("name"));
                        // 删除listview中的对应数据
                        datas.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                message.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                message.create().show();
                return true;
            }
        });

    }
    public void dataUpdate() {
        try {
            //select *
            MyDB db = new MyDB(getBaseContext());
            Cursor cursor = db.selectall();
            datas  = new ArrayList<Map<String, String>>();
            if (cursor == null) {
            } else {
                while (cursor.moveToNext()) {
                    String name1 = cursor.getString(0);
                    String birth1 = cursor.getString(1);
                    String gift1 = cursor.getString(2);
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", name1);
                    map.put("birth", birth1);
                    map.put("gift", gift1);
                    datas .add(map);
                }
                adapter = new SimpleAdapter(MainActivity.this, datas, R.layout.listitem,
                        new String[]{"name", "birth", "gift"},
                        new int[]{R.id.name, R.id.birth, R.id.gift});
                LV.setAdapter(adapter);
            }
        } catch (SQLException e) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9 && resultCode == 99) {
            dataUpdate();
        }
    }


}
