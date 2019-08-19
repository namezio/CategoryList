package com.ezio.categorylist.retrofit;

import com.ezio.categorylist.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PolyService {
    @GET("wp-json/wp/v2/categories")
    Call<List<Category>> getCategories(@Query("page") int page,
                                       @Query("per_page") int per_page);

}
