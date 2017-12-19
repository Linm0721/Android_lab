package com.example.linm.lab8;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.linm.lab8.MyDB.TABLE_NAME;

public class Add extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText NAME = (EditText)findViewById(R.id.editname);
        final EditText BIRTH = (EditText)findViewById(R.id.editbirth);
        final EditText GIFT = (EditText)findViewById(R.id.editgift);
        final Button ADDBUTTON = (Button)findViewById(R.id.add);
        final Button CLEARBUTTON = (Button)findViewById(R.id.clear);

        ADDBUTTON.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String name = NAME.getText().toString();
                String birth = BIRTH.getText().toString();
                String gift = GIFT.getText().toString();



                //名字为空
                if(name.equals("")){
                    Toast.makeText(Add.this, "名字不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    MyDB db = new MyDB(getBaseContext());
                    Cursor cursor = db.ifexit(name);
                    if (cursor.moveToFirst() == true) {
                        Toast.makeText(Add.this, "名字不能重复", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        db.insert(name,birth,gift);
                        setResult(99, new Intent());
                        finish();
                    }
                }

            }
        });

        CLEARBUTTON.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NAME.setText("");
                BIRTH.setText("");
                GIFT.setText("");
            }
        });
    }

}
