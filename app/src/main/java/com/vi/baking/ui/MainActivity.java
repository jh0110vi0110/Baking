package com.vi.baking.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.vi.baking.R;
import com.vi.baking.adapter.RecipeListAdapter;
import com.vi.baking.data.RecipeFetch;
import com.vi.baking.data.RecipeRetrofit;
import com.vi.baking.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.OnRecipeListener {
    public static final String TAG = "MainActivity";
    private ArrayList<Recipe> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recipeListRecyclerView = (RecyclerView)  findViewById(R.id.rv_recipe_list_test);
        recipeListRecyclerView.setLayoutManager(new AutoFitGridLayoutManager(this, 350));
        final RecipeListAdapter recipeListAdapter =new RecipeListAdapter(mRecipeList, this);
        recipeListRecyclerView.setAdapter(recipeListAdapter);

        RecipeFetch recipeFetch = RecipeRetrofit.make();
        Call<ArrayList<Recipe>> recipeCall = recipeFetch.fetchRecipes();

        recipeCall.enqueue(new Callback<ArrayList<Recipe>>() {

            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.d(TAG, "onResponse: status code: "+ statusCode.toString());

                ArrayList<Recipe> recipes = response.body();
                mRecipeList = recipes;
                for (int i = 0; i < recipes.size(); i++){
                    Log.d(TAG, "onResponse: " + recipes.get(i).getName());
                    Log.d(TAG, "onResponse: " + recipes.get(i).getSteps().get(i).getDescription());

                }

                recipeListAdapter.setRecipeList(recipes);


                //Bundle recipesBundle = new Bundle();
                // recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);



                // recipesAdapter.setRecipeData(recipes,getContext());
                // if (idlingResource != null) {
                //     idlingResource.setIdleState(true);
                // }


            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG, "onFailure http fail: " + t.getMessage());
            }
        });

        //recipeListAdapter.setRecipeList(mRecipeList);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRecipeClick(int position) {
        // Logic when recipe is clicked
    }
}
