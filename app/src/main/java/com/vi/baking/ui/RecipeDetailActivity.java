package com.vi.baking.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.vi.baking.R;
import com.vi.baking.model.Recipe;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String TAG = "RecipeDetailActivity";
    private Recipe mRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Intent intentThatStartedThisActivity = getIntent();

        if (savedInstanceState == null){
            if (intentThatStartedThisActivity != null) {
                if (intentThatStartedThisActivity.hasExtra("Recipe")) {
                    mRecipe = intentThatStartedThisActivity.getParcelableExtra("Recipe");
                    setTitle(mRecipe.getName());
                    RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                    recipeDetailFragment.setRecipe(mRecipe);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.fl_recipe_detail_container, recipeDetailFragment)
                            .commit();


                    Log.d(TAG, "onCreate: " + mRecipe.getName());
                    Log.d(TAG, "onCreate: " + mRecipe.getIngredients().get(3).getIngredient());
                    Log.d(TAG, "onCreate: " + mRecipe.getSteps().get(4).getDescription());
                }
            }

        } else {
            mRecipe = savedInstanceState.getParcelable("Recipe");
            setTitle(savedInstanceState.getString("Title"));
        }
       // getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title", mRecipe.getName());
        outState.putParcelable("Recipe", mRecipe);
    }
}
