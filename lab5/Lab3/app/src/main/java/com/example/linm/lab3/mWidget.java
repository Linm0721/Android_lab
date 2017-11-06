package com.example.linm.lab3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import static android.R.attr.x;

/**
 * Implementation of App Widget functionality.
 */
public class mWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //实例化RemoteView，与相应的布局对应起来
        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.m_widget);
        //点击跳转到MainActivity页面
        Intent i = new Intent(context,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.widget,pi);

        ComponentName me = new ComponentName(context,mWidget.class);
        //更新widget
        appWidgetManager.updateAppWidget(me,updateViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);
        if(intent.getAction().equals("widgetStaticBroadcast")){
            //得到的随机数
            int i = intent.getIntExtra("info",0);
            //实例化RemoteView
            RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.m_widget);
            //设置文字
            updateViews.setTextViewText(R.id.appwidget_text,
                    MainActivity.Infos.get(i).getName()+"仅售"+MainActivity.Infos.get(i).getPrice()+"!");
            //设置图片
            updateViews.setImageViewResource(R.id.appwidget_image,MainActivity.Infos.get(i).getImgid());
            //点击跳转
            Intent newintent = new Intent(context,GoodsInfoActivity.class);
            newintent.addCategory(Intent.CATEGORY_LAUNCHER);
            String productname = MainActivity.Infos.get(i).getName();
            Bundle bundle = new Bundle();
            bundle.putString("name",productname);//属性为name，数据为productname
            newintent.putExtras(bundle);
            PendingIntent pi = PendingIntent.getActivity(context,0,newintent,PendingIntent.FLAG_UPDATE_CURRENT);
            //实现单击跳转事件
            updateViews.setOnClickPendingIntent(R.id.widget,pi);
            //更新widget
            ComponentName me = new ComponentName(context,mWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(me,updateViews);
        }
    }
}

