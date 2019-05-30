package com.vi.baking.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vi.baking.R;
import com.vi.baking.model.Ingredient;
import com.vi.baking.model.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {
    public static final String TAG = "RecipeDetailFragment";
    Recipe mRecipe;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable("Recipe");
        }
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        TextView mIngredientsTextView = rootView.findViewById(R.id.tv_recipe_detail_fragment_ingredients);
        RecyclerView mStepsRecyclerView = rootView.findViewById(R.id.rv_steps_list);

        if (mRecipe != null){
            int servings = mRecipe.getServings();
            ArrayList<Ingredient> ingredients = mRecipe.getIngredients();
            if (ingredients != null) {
                for (int i = 0; i < ingredients.size(); i++) {
                    mIngredientsTextView.append( "\n" + ingredients.get(i).getIngredient() + "\n" );
                    mIngredientsTextView.append("\t\t Quantity: " + ingredients.get(i).getQuantity() + " " +
                            ingredients.get(i).getMeasure() + "\n\n");
                    //mIngredientsTextView.append("\t\t Measure: " + ingredients.get(i).getMeasure() + "\n");
                }
            }

        }

        // Inflate the layout for this fragment
        return rootView;
    }

    public void setRecipe (Recipe recipe) {
        mRecipe = recipe;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("Recipe", mRecipe);
    }
}
