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
       final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

       //Initialize Recyclerview
        RecyclerView recipeListRecyclerView = (RecyclerView)  rootView.findViewById(R.id.rv_recipe_list);
        recipeListRecyclerView.setLayoutManager(new AutoFitGridLayoutManager(getContext(), 300));
        final RecipeListAdapter recipeListAdapter =new RecipeListAdapter(mRecipeList, this);
        recipeListRecyclerView.setAdapter(recipeListAdapter);

        //Asynchronous call (onResponse onFail)
        RecipeFetch recipeFetch = RecipeRetrofit.make();
        Call<ArrayList<Recipe>> recipeCall = recipeFetch.fetchRecipes();
        recipeCall.enqueue(new Callback<ArrayList<Recipe>>() {

            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                ArrayList<Recipe> recipes = response.body();
                mRecipeList = recipes;
                recipeListAdapter.setRecipeList(recipes);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.d(TAG, "Retrofit onFailure http failure: " + t.getMessage());
            }
        });
        return rootView;
    }

    @Override
    public void onRecipeClick(int position) {
        ArrayList<Recipe> recipeListToSend = mRecipeList;
        Intent intentToStartDetailActivity = new Intent(getContext(), DetailActivity.class);
        Bundle b = new Bundle();
        b.putParcelableArrayList("RecipeList", recipeListToSend);
        b.putInt("RecipeId", position);
        intentToStartDetailActivity.putExtras(b);
        startActivity(intentToStartDetailActivity);
    }
}
