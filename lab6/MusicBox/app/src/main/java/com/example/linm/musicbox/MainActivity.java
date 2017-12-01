package com.example.linm.musicbox;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.Duration;

import static com.example.linm.musicbox.MusicService.MP;

public class MainActivity extends AppCompatActivity {

    private Button PlayButton;
    private Button StopButton;
    private Button QuitButton;
    private TextView CurrTime;
    private TextView MusicStatus;
    private TextView Length;
    private ImageView Image;
    private SeekBar Seekbar;
    private boolean tag = false;
    private int statusid = 0;
    //后台音乐服务
    private IBinder MusBinder;
    private ServiceConnection MusServConn;

    //获取权限
    private static boolean hasPermission = false;
    //时间格式
    public static String getTimeFromInt(int time) {

        if (time <= 0) {
            return "0:00";
        }
        int secondnd = (time / 1000) / 60;
        int million = (time / 1000) % 60;
        String f = String.valueOf(secondnd);
        String m = million >= 10 ? String.valueOf(million) : "0"
                + String.valueOf(million);
        return f + ":" + m;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        PlayButton = (Button)findViewById(R.id.play);
        StopButton = (Button)findViewById(R.id.stop);
        QuitButton = (Button)findViewById(R.id.quit);
        CurrTime = (TextView)findViewById(R.id.currtime);
        MusicStatus = (TextView)findViewById(R.id.status);
        Length = (TextView)findViewById(R.id.length);
        Image = (ImageView)findViewById(R.id.image);
        Seekbar = (SeekBar)findViewById(R.id.seekbar);

        //初始化音乐播放状态
        MusicStatus.setText("");

        /*动画旋转*/
        final ObjectAnimator animator = ObjectAnimator.ofFloat(Image,"rotation",0,360);
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator()); //不停止
        animator.setRepeatCount(-1); //不断重复



        //bindService成功后回调onServiceConnected函数，通过IBinder获取Service对象，实现Activity与Service的绑定
        MusServConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusBinder = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                MusBinder = null;
            }
        };

        Intent intent = new Intent(this, MusicService.class);
        Log.e("debug", "启动服务");
        startService(intent);
        bindService(intent,MusServConn, Context.BIND_AUTO_CREATE);

        PlayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                try{
                    int code = 101;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    Log.e("debug", "发送101");
                    MusBinder.transact(code,data,reply,0);

                }catch (RemoteException e){
                    Log.e("debug", "发送101错误");
                    e.printStackTrace();
                }
                //没有播放
                if(!tag){
                    MusicStatus.setText("Playing"); //播放状态
                    if(statusid==0){
                        animator.start();
                        statusid = 1;
                    }
                    else{
                        animator.resume();
                    }
                    PlayButton.setText("PAUSED");
                    tag = true;
                }
                //正在播放
                else{
                    MusicStatus.setText("Paused"); //播放状态
                    animator.pause();
                    PlayButton.setText("PLAY");
                    tag = false;

                }

            }
        });

        StopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MusicStatus.setText("Stopped"); //播放状态
                try{
                    int code = 102;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    Log.e("debug", "发送102");
                    MusBinder.transact(code,data,reply,0);

                }catch (RemoteException e){
                    Log.e("debug", "发送102错误");
                    e.printStackTrace();
                }
                animator.end();
                PlayButton.setText("PLAY");
                tag = false;
                statusid = 0;
            }
        });
        final Intent stopIntent = new Intent(this, MusicService.class);
        QuitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                try{
                    int code = 103;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    Log.e("debug", "发送103");
                    MusBinder.transact(code,data,reply,0);

                }catch (RemoteException e){
                    Log.e("debug", "发送103错误");
                    e.printStackTrace();
                }
                animator.end();
                statusid = 0;
                //解除Service绑定
                unbindService(MusServConn);
                MusServConn = null;
                stopService(stopIntent);
                try{
                    MainActivity.this.finish();
                    System.exit(0);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //滑动条
        Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int code = 105;
                Parcel data = Parcel.obtain();
                data.writeInt(seekBar.getProgress());
                Parcel reply = Parcel.obtain();
                try{
                    MusBinder.transact(code,data,reply,0);
                }catch (RemoteException e){
                    e.printStackTrace();
                }

            }
        });

        //多线程更新UI

        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 123:
                        //UI更新内容
                        //更新进度条
                        Seekbar.setProgress(MP.getCurrentPosition());
                        Seekbar.setMax(MP.getDuration());
                        //更新时间
                        CurrTime.setText(getTimeFromInt(MP.getCurrentPosition()));
                        Length.setText(getTimeFromInt(MP.getDuration()));
                        break;
                }
            }
        };

        Thread mThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if(MusServConn != null ){

                        mHandler.obtainMessage(123).sendToTarget();
                    }
                }
            }
        };

        mThread.start();

    }
    /*获取Sd卡权限相关*/
    public void verifyStoragePermissions(Activity activity)
    {
        try {
            if (ActivityCompat.checkSelfPermission(activity, "android.permission.READ_EXTERNAL_STORAGE") != 0)
            {
                Log.e("debug", "此处应弹出提示框");
                ActivityCompat.requestPermissions(activity,
                        new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1);
            }else
            {
                Log.e("debug", "成功获取权限 in verifyStoragePermissions()");
                hasPermission = true;
            }
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        Log.e("debug", "verifyStoragePermissions()执行完成");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1:
                Log.e("debug", "系统回调，尝试获取权限");
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    /*若用户同意app获取权限，标记可读取文件*/
                    Log.e("debug", "成功获取权限 in onRequestPermissionsResult()");
                    hasPermission = true;
                }else{
                    /*若用户拒绝app获取权限，则弹出信息提示“拒绝权限将无法使用本程序”*/
                    Toast.makeText(this, "拒绝权限将无法使用本程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                Log.e("debug", "undefined requestCode:"+requestCode);
                break;
        }
    }
    /*
    @Override
    public void onBackPressed() {
        //实现Home效果
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
