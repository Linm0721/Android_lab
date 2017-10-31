package com.example.linm.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //


        Notification.Builder builder = new Notification.Builder(context);
        int i = intent.getIntExtra("info",0);
        builder.setContentTitle("新商品热卖")
                .setContentText(MainActivity.Infos.get(i).getName()+"仅售"+MainActivity.Infos.get(i).getPrice()+"!")
                .setAutoCancel(true)
                .setTicker("您有一条新消息")
                .setSmallIcon(MainActivity.Infos.get(i).getImgid())
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), MainActivity.Infos.get(i).getImgid()));


        Intent x= new Intent(context, GoodsInfoActivity.class);
        Bundle bundle = new Bundle();
        String productname = MainActivity.Infos.get(i).getName();
    //    Log.e("product",MainActivity.Infos.get(i).getName());
        bundle.putString("name",productname);//属性为name，数据为productname
        x.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                x, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Notification notify = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int uniqueId = (int) System.currentTimeMillis();
        manager.notify(uniqueId,notify);

    }
}
class DynamicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int Imgid = intent.getIntExtra("imgid",0);
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentTitle("马上下单")
                .setContentText(intent.getStringExtra("name")+"已经添加到购物车")
                .setSmallIcon(Imgid)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),Imgid))
                .setAutoCancel(true);

        Intent x= new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();

        //    Log.e("product",MainActivity.Infos.get(i).getName());
        bundle.putString("tag","shopcar");//属性为name，数据为productname
        x.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                x, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Notification notify = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int uniqueId = (int) System.currentTimeMillis();
        manager.notify(uniqueId,notify);
    }
}