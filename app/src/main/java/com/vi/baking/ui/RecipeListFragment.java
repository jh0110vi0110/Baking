package com.vi.baking.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
public class RecipeListFragment extends Fragment {
    public static final String TAG = "RecipeListFragment";

    //private ArrayList<Recipe> mRecipeList;


    public RecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // RecyclerView recipeListRecyclerView;
       final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        //initialize recyclerview
        RecyclerView recipeListRecyclerView = (RecyclerView)  rootView.findViewById(R.id.rv_recipe_list);
        recipeListRecyclerView.setLayoutManager(new AutoFitGridLayoutManager(getContext(), 350));
        //final RecipeListAdapter recipeListAdapter =new RecipeListAdapter((MainActivity)getActivity());
        //recipeListRecyclerView.setAdapter(recipeListAdapter);

        RecipeFetch recipeFetch = RecipeRetrofit.make();
        Call<ArrayList<Recipe>> recipeCall = recipeFetch.fetchRecipes();

        recipeCall.enqueue(new Callback<ArrayList<Recipe>>() {

            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.d(TAG, "onResponse: status code: "+ statusCode.toString());

                ArrayList<Recipe> recipes = response.body();
                for (int i = 0; i < recipes.size(); i++){
                    Log.d(TAG, "onResponse: " + recipes.get(i).getName());
                    Log.d(TAG, "onResponse: " + recipes.get(i).getSteps().get(i).getDescription());

                }

                //recipeListAdapter.setRecipeList(recipes);


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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
               //inflater.inflate(R.layout.fragment_recipe_list, container, false)

       // return rootView;
    }

}
