package com.vi.baking.ui;

import android.content.Intent;
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
    private TextView mRecipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mRecipeName = (TextView) findViewById(R.id.tv_recipe_detail_recipe_name);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("Recipe")) {
                mRecipe = intentThatStartedThisActivity.getParcelableExtra("Recipe");
                mRecipeName.setText(mRecipe.getName());
                Log.d(TAG, "onCreate: " + mRecipe.getName());
                Log.d(TAG, "onCreate: " + mRecipe.getIngredients().get(3).getIngredient());
                Log.d(TAG, "onCreate: " + mRecipe.getSteps().get(4).getDescription());


            }
        }
    }
}
