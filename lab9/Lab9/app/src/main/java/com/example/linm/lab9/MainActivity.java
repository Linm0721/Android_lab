package com.example.linm.lab9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.linm.lab9.adapter.CardAdapter;
import com.example.linm.lab9.factory.ServiceFactory;
import com.example.linm.lab9.model.Github;
import com.example.linm.lab9.service.GithubService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText SearchEdit; //编辑框
    private Button ClearButton; //清除按钮
    private Button FetchButton; //搜索按钮
    private ProgressBar ProgBar; //进度条

    private CardAdapter mAdapter; //适配器
    private RecyclerView mRecyclerView; //RecyclerView列表

    void init(){
        SearchEdit = (EditText)findViewById(R.id.search);
        ClearButton = (Button)findViewById(R.id.clear);
        FetchButton = (Button)findViewById(R.id.fetch);
        ProgBar = (ProgressBar)findViewById(R.id.pg);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CardAdapter() ;
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();//绑定id

        ProgBar.setVisibility(View.GONE);//设置进度不可见

        //清空所有用户github
        ClearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mAdapter.removeAllItem();
            }
        });
        //搜索
        FetchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                ProgBar.setVisibility(View.VISIBLE);//显示进度条
                String name = SearchEdit.getText().toString();//获取搜索的名字
                ServiceFactory.createRetrofit("https://api.github.com")
                        .create(GithubService.class)
                        .getUser(name)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Github>() {
                            //完成传输
                            public final void onCompleted() {
                                ProgBar.setVisibility(View.GONE);//进度条消失
                                System.out.println("完成传输");
                            }
                            //出错
                            public void onError(Throwable throwable) {
                                Toast.makeText(MainActivity.this, "请确认你搜索的用户存在", Toast.LENGTH_SHORT).show();
                                ProgBar.setVisibility(View.GONE);
                                Log.e("debug","error");
                                throwable.printStackTrace();
                            }
                            //找到结果
                            public void onNext(Github github) {
                                mAdapter.addItem(github);//将结果加入到适配器的队列
                            }
                        });

            }
        });

        mAdapter.setOnItemClickListener(new CardAdapter.OnRecyclerViewItemClickListener() {
            //单击跳转到库界面
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, RepoActivity.class);
                intent.putExtra("username", mAdapter.getItem(position).getLogin()); //传入用户名
                MainActivity.this.startActivity(intent);
            }
            //长按删除
            public void onLongClick(int position) {
                mAdapter.removeItem(position);
            }
        });
    }
}
