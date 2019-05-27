package com.vi.baking.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RecipeRetrofit {

    static RecipeFetch recipeFetch;
    private final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static RecipeFetch make() {

        Gson gson = new GsonBuilder().create();

        //OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        recipeFetch = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.callFactory(httpClientBuilder.build())
                .build().create(RecipeFetch.class);
        return recipeFetch;
    }
}
