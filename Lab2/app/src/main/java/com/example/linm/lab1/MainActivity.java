package com.example.linm.lab1;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView Img;
    private TextInputLayout Num;
    private TextInputLayout PW;
    private EditText EditNum;
    private EditText EditPW;
    private RadioGroup RG;
    private RadioButton RB1;
    private RadioButton RB2;
    private Button Login;
    private Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Img = (ImageView)findViewById(R.id.imageView);
        Num = (TextInputLayout)findViewById(R.id.textinputlayout);
        PW = (TextInputLayout)findViewById(R.id.textinputlayout1);
        EditNum = (EditText)findViewById(R.id.editText2);
        EditPW = (EditText)findViewById(R.id.editText);
        RG = (RadioGroup)findViewById(R.id.radioGroup);
        RB1 = (RadioButton)findViewById(R.id.button1);
        RB2 = (RadioButton)findViewById(R.id.radioButton);
        Login = (Button)findViewById(R.id.button2);
        Register = (Button)findViewById(R.id.button);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("上传头像");
        final String[] Array = new String[]{"拍摄","从相册选择"};
        final int selectedIndex[] = {0};
        dialog.setItems(Array, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedIndex[0] = which;

                if(selectedIndex[0]==0){
                    Toast.makeText(MainActivity.this, "您选择了[拍摄]", Toast.LENGTH_SHORT).show();
                }else if(selectedIndex[0]==1){
                    Toast.makeText(MainActivity.this, "您选择了[从相册选择]", Toast.LENGTH_SHORT).show();
                }
            }

        });
        dialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "您选择了[取消]", Toast.LENGTH_SHORT).show();
                    }
                });
        final AlertDialog builder = dialog.create();
        Img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                builder.show();

            }
        });






        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedID){

                    if(checkedID == RB1.getId()){
                     //   Toast.makeText(MainActivity.this, "您选择了学生", Toast.LENGTH_SHORT).show();
                        Snackbar.make(RG, "您选择了学生", Snackbar.LENGTH_SHORT)
                                .setAction("确认", new View.OnClickListener(){
                                    @Override
                                            public void onClick(View v){
                                                Toast.makeText(MainActivity.this,"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                                            }
                                 })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                      }
                      else if(checkedID == RB2.getId()){
                       // Toast.makeText(MainActivity.this,"您选择了教职工",Toast.LENGTH_SHORT).show();
                        Snackbar.make(RG, "您选择了教职工", Snackbar.LENGTH_SHORT)
                             .setAction("确认",new View.OnClickListener(){
                                @Override
                                 public void onClick(View v){
                                    Toast.makeText(MainActivity.this,"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                                 }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                                .setDuration(5000)
                                .show();
                      }

            };
        }
        );
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String number = EditNum.getText().toString();
                String password = EditPW.getText().toString();
                if(number.isEmpty()){
                    Num.setErrorEnabled(true);
                    Num.setError("学号不能为空");
                }
                else if(password.isEmpty()){
                    PW.setErrorEnabled(true);
                    PW.setError("密码不能为空");
                }
                else if(number.equals("123456") && password.equals("6666")){
                    Snackbar.make(Login,"登陆成功",Snackbar.LENGTH_SHORT)
                            .setAction("确认",new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(MainActivity.this,"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .setDuration(5000)
                            .show();
                }
                else if(!number.isEmpty() && !password.isEmpty()){
                    Snackbar.make(Login,"学号或密码错误",Snackbar.LENGTH_SHORT)
                            .setAction("确认",new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(MainActivity.this,"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .setDuration(5000)
                            .show();
                }
            }
        });
        Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(RG.getCheckedRadioButtonId() == RB1.getId()){
                    Snackbar.make(Register,"学生注册功能尚未启用",Snackbar.LENGTH_SHORT)
                            .setAction("确认",new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    Toast.makeText(MainActivity.this,"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .setDuration(5000)
                            .show();
                }
                else if(RG.getCheckedRadioButtonId() == RB2.getId()){
                    Toast.makeText(MainActivity.this,"教职工注册功能尚未启用",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}


