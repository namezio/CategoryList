package com.ezio.categorylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ezio.categorylist.retrofit.EndlessRecyclerViewScrollListener;
import com.ezio.categorylist.retrofit.PolyRetrofit;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView lvList;

    private SwipeRefreshLayout swipe;


    private int page = 1;
    private int per_page = 5;
    private List<Category> categories;
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvList = findViewById(R.id.recy);
        swipe = findViewById(R.id.swipe1);


        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        lvList.setLayoutManager(linearLayoutManager);
        lvList.setHasFixedSize(true);
        lvList.setAdapter(categoryAdapter);
        getData(page, per_page);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
//                categoryAdapter.setOnLoadMore(true);
//                lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//                    @Override
//                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                        getData(page+1, per_page);
//                        Log.d("okokokokok22",""+(page+1));
//                    }
//                });
            }
        });
        lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getData(page + 1, per_page);
            }
        });
    }
    public void getData(int page, int per_page) {

        PolyRetrofit.getInstance().getCategories(page, per_page).
                enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call,
                                           Response<List<Category>> response) {
                        swipe.setRefreshing(false);
                        if (response.body().size() == 0){
                            lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                                @Override
                                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                                }
                            });

                            categoryAdapter.setOnLoadMore(false);
                        }

                        categories.addAll(response.body());
                        categoryAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        swipe.setRefreshing(false);
                    }
                });

    }

}
