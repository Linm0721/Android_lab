package com.example.linm.lab9.service;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;

import com.example.linm.lab9.model.Github;
import com.example.linm.lab9.model.Repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubService {
    @GET("/users/{user}")
    Observable<Github> getUser(@Path("user") String user);

    @GET("/users/{user}/repos")
    Observable<List<Repo>> getRepos(@Path("user") String user);
}
