package com.vi.baking.data;

import com.vi.baking.model.Recipe;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeFetch {
    //Get list of recipes
    @GET("baking.json")
    Call<ArrayList<Recipe>> fetchRecipes();
}