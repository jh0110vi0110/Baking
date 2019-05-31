package com.vi.baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vi.baking.R;
import com.vi.baking.adapter.RecipeListAdapter;
import com.vi.baking.data.RecipeFetch;
import com.vi.baking.data.RecipeRetrofit;
import com.vi.baking.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment implements RecipeListAdapter.OnRecipeListener {
    public static final String TAG = "RecipeListFragment";
    private ArrayList<Recipe> mRecipeList;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // RecyclerView recipeListRecyclerView;
       final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        RecyclerView recipeListRecyclerView = (RecyclerView)  rootView.findViewById(R.id.rv_recipe_list);
        recipeListRecyclerView.setLayoutManager(new AutoFitGridLayoutManager(getContext(), 500));
        final RecipeListAdapter recipeListAdapter =new RecipeListAdapter(mRecipeList, this);
        recipeListRecyclerView.setAdapter(recipeListAdapter);

        RecipeFetch recipeFetch = RecipeRetrofit.make();
        Call<ArrayList<Recipe>> recipeCall = recipeFetch.fetchRecipes();

        //Asynchronous call
        recipeCall.enqueue(new Callback<ArrayList<Recipe>>() {

            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                ArrayList<Recipe> recipes = response.body();
                mRecipeList = recipes;
                recipeListAdapter.setRecipeList(recipes);
               // for (int i = 0; i < recipes.size(); i++){
               //     Log.d(TAG, "onResponse: " + recipes.get(i).getName());
               //     Log.d(TAG, "onResponse: " + recipes.get(i).getSteps().get(i).getDescription());
               // }


                //Bundle recipesBundle = new Bundle();
                // recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

                // if (idlingResource != null) {
                //     idlingResource.setIdleState(true);
                // }


            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG, "onFailure http fail: " + t.getMessage());
            }
        });
        return rootView;
    }

    @Override
    public void onRecipeClick(int position) {
        Recipe recipeToSend = mRecipeList.get(position);
        Intent intentToStartDetailActivity = new Intent(getContext(), DetailActivity.class);
        intentToStartDetailActivity.putExtra("Recipe", recipeToSend);
        startActivity(intentToStartDetailActivity);
    }
}
