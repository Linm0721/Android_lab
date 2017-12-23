package com.example.linm.lab9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.linm.lab9.factory.ServiceFactory;
import com.example.linm.lab9.model.Repo;
import com.example.linm.lab9.service.GithubService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepoActivity extends AppCompatActivity {

    private ProgressBar progressBar; //进度条
    private ListView lv; //ListView列表
    private List<Map<String, String>> repoListData = new ArrayList<>(); //适配器绑定的队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        progressBar = (ProgressBar)findViewById(R.id.pb);
        lv = (ListView)findViewById(R.id.listview);

        String userName = getIntent().getStringExtra("username");//获得用户名
        ServiceFactory.createRetrofit("https://api.github.com")
                .create(GithubService.class)
                .getRepos(userName) //获得库
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repo>>() {
                    //传输完成
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE); //隐藏进度条
                        lv.setVisibility(View.VISIBLE);//显示listview结果
                        System.out.println("完成传输");
                    }

                    public void onError(Throwable throwable) {
                        Toast.makeText(RepoActivity.this, "出错啦", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE); //隐藏进度条
                        lv.setVisibility(View.VISIBLE); //显示listview
                        throwable.printStackTrace();
                    }

                    public void onNext(List<Repo> list) {

                        repoListData.clear();
                        for(int i=0; i<list.size(); i++){
                            Repo repo = list.get(i);
                            Map<String, String> temp = new LinkedHashMap<>();
                            temp.put("name", repo.getName());
                            temp.put("language", repo.getLanguage());
                            temp.put("description", repo.getDescription());
                            repoListData.add(temp);
                        }
                        renewAdapter();
                    }
                });
    }
    private void renewAdapter() {

        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, repoListData,
                R.layout.repoitem,
                new String[]{"name", "language", "description"},
                new int[]{R.id.name, R.id.language, R.id.description});
        lv.setAdapter(mSimpleAdapter);
    }
}
