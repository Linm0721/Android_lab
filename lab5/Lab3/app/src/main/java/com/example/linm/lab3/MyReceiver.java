package com.example.linm.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int i = intent.getIntExtra("info",0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("新商品热卖") //标题
                .setContentText(MainActivity.Infos.get(i).getName()+"仅售"+MainActivity.Infos.get(i).getPrice()+"!") //内容
                .setAutoCancel(true)
                .setTicker("您有一条新消息") //提示消息
                .setSmallIcon(MainActivity.Infos.get(i).getImgid()) //小图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), MainActivity.Infos.get(i).getImgid())); //大图标

        //跳转到商品详情介绍页面
        Intent x= new Intent(context, GoodsInfoActivity.class);
        Bundle bundle = new Bundle();
        String productname = MainActivity.Infos.get(i).getName();
        bundle.putString("name",productname);//属性为name，数据为productname
        x.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                x, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Notification notify = builder.build();
        //manager负责将状态显示出来
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int uniqueId = (int) System.currentTimeMillis(); //设置随机数，不会覆盖之前的通知
        manager.notify(uniqueId,notify);

    }
}
class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int Imgid = intent.getIntExtra("imgid",0);
        Notification.Builder builder = new Notification.Builder(context);
        //设置Notification显示内容
        builder.setContentTitle("马上下单")
                .setContentText(intent.getStringExtra("name")+"已经添加到购物车")
                .setSmallIcon(Imgid)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),Imgid))
                .setAutoCancel(true);
        //跳转到购物车界面
        Intent x= new Intent(context, MainActivity.class);
        x.putExtra("mode",1); //传值
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                x, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //执行
        Notification notify = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int uniqueId = (int) System.currentTimeMillis();
        manager.notify(uniqueId,notify);

        //lab5
        //实例化RemoteView
        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.m_widget);
        //设置文字
        updateViews.setTextViewText(R.id.appwidget_text,intent.getStringExtra("name")+"已经添加到购物车");
        //设置图片
        updateViews.setImageViewResource(R.id.appwidget_image,Imgid);
        //点击跳转事件
        Intent newintent = new Intent(context,MainActivity.class);
        newintent.addCategory(Intent.CATEGORY_LAUNCHER);
        newintent.putExtra("mode",1);
        PendingIntent pi = PendingIntent.getActivity(context,0,newintent,PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.widget,pi);
        //更新widget
        ComponentName me = new ComponentName(context,mWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(me,updateViews);
    }
}