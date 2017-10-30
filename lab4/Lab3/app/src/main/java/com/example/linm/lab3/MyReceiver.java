package com.example.linm.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.example.linm.lab3.MainActivity;
import com.example.linm.lab3.InfoActivity;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
     //   Toast t = Toast.makeText(context,"静态广播："+intent.getIntExtra("info",0), Toast.LENGTH_SHORT);
     //   t.setGravity(Gravity.TOP,0,0);
     //   t.show();
        Notification.Builder builder = new Notification.Builder(context);
        int i = intent.getIntExtra("info",0);
        builder.setContentTitle("新商品热卖")
                .setContentText(MainActivity.Infos.get(i).getName()+"仅售"+MainActivity.Infos.get(i).getPrice()+"!")
                .setAutoCancel(true);
        switch (MainActivity.Infos.get(i).getName()){
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
