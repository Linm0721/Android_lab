package com.example.linm.lab7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences myPreference = getSharedPreferences("password",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = myPreference.edit();


        final EditText NP = (EditText) findViewById(R.id.newpw);
        final EditText CP = (EditText) findViewById(R.id.confpw);
        final Button OK = (Button) findViewById(R.id.ok);
        final Button CLEAR =  (Button) findViewById(R.id.clear);
        //editor.putString("password",null);
        //editor.commit();

        if(myPreference.getString("password",null)!=null){
            Log.e("debug",myPreference.getString("password",null));
            NP.setVisibility(View.INVISIBLE);
            CP.setHint("Password");
        }

        OK.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String newpassword = NP.getText().toString();
                String confirmpassword = CP.getText().toString();

                if(myPreference.getString("password",null)==null){
                    if(newpassword.isEmpty() || confirmpassword.isEmpty()){ //密码为空
                        Toast.makeText(MainActivity.this, "Password cannot be empty",Toast.LENGTH_SHORT).show();
                    }
                    else if(!newpassword.equals(confirmpassword)){ //密码不匹配
                        Toast.makeText(MainActivity.this, "Password Mismatch",Toast.LENGTH_SHORT).show();
                    }
                    else{ //密码匹配，保存密码，跳转
                        editor.putString("password",confirmpassword);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this,FileEdit.class);
                        startActivity(intent);
                    }
                }
                else{
                    if(confirmpassword.isEmpty()){  //密码为空
                        Toast.makeText(MainActivity.this, "Password cannot be empty",Toast.LENGTH_SHORT).show();
                    }
                    else if(confirmpassword.equals(myPreference.getString("password",""))){  //密码匹配，跳转
                        Toast.makeText(MainActivity.this, "Password Correct",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,FileEdit.class);
                        startActivity(intent);
                    }
                    else{ //密码不匹配
                        Toast.makeText(MainActivity.this, "Password Mismatch",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        CLEAR.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                NP.setText("");
                CP.setText("");
            }
        });

    }
}
