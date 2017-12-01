package com.example.linm.musicbox;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MusicService extends Service {

    private IBinder mBinder;
    public static  MediaPlayer MP = new MediaPlayer();

    public MusicService() {
        try{
            Log.e("debug", "进入初始化音乐");
            MP.setDataSource(Environment.getExternalStorageDirectory() + "/musicbox/melt.mp3");
            Log.e("debug", "成功初始化音乐");
            MP.prepare();
            MP.setLooping(true);
        }catch(Exception e){
            Log.e("debug", "初始化音乐失败");
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate(){

        super.onCreate();
        Log.e("debug", "创建musicplayer");
      //  MP = new MediaPlayer();
        mBinder = new MyBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder{

     @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)throws RemoteException{
         Log.e("debug", "接收到code"+code);
         switch (code){
                case 101:
                    //播放按钮，服务处理函数
                    if (MP!=null){
                        if(MP.isPlaying()){
                            MP.pause();
                        }
                        else{
                            MP.start();
                        }
                    }
                    break;

                case 102:
                    //停止按钮，服务处理函数
                    if (MP != null){
                        MP.stop();
                        try{
                            MP.prepare();
                            MP.seekTo(0);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    break;

                case 103:
                    //退出按钮，服务处理函数
                    if (MP != null){
                        MP.reset();
                        MP.release();
                    }
                    break;

                case 104:
                    //界面刷新，服务返回数据函数
                    int currpos = MP.getCurrentPosition();
                    reply.writeInt(currpos);
                    break;

                case 105:
                    //拖动进度条，服务处理函数
                    Log.e("debug","时长"+ MP.getDuration());
                    MP.seekTo(data.readInt());
                    break;
            }
            return super.onTransact(code, data, reply, flags);
        }

    }

}

