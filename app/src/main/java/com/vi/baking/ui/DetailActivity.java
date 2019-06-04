package com.vi.baking.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.vi.baking.R;
import com.vi.baking.adapter.StepListAdapter;
import com.vi.baking.model.Ingredient;
import com.vi.baking.model.Recipe;
import com.vi.baking.model.Step;
import com.vi.baking.widget.IngredientsWidgetProvider;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity implements StepListAdapter.OnStepListener, StepDetailFragment.OnStepListener {
    private static final String TAG = "DetailActivity";
    public static final String PREFERENCES_RECIPE_ID = "RECIPE_ID";
    public static final String PREFERENCES_WIDGET_RECIPE = "WIDGET_RECIPE";
    public static final String PREFERENCES_WIDGET_INGREDIENTS = "WIDGET_INGREDIENTS";

    private ArrayList<Recipe> mRecipeList;
    private int mRecipeIndex;
    private boolean mTwoPane;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intentThatStartedThisActivity = getIntent();
        Bundle b = intentThatStartedThisActivity.getExtras();
        if ( findViewById(R.id.fl_detail_container_right) != null){
            mTwoPane = true;
        }

        if (savedInstanceState == null){
            if(b != null){
                mRecipeList = b.getParcelableArrayList("RecipeList");
                mRecipeIndex = b.getInt("RecipeId");
                setTitle(mRecipeList.get(mRecipeIndex).getName());
                displayRecipeAtPosition(mRecipeIndex);
                if (mTwoPane){
                    displayStepAtPosition(0);
                }

            }
        } else {
            mRecipeList = savedInstanceState.getParcelableArrayList("RecipeList");
            mRecipeIndex = savedInstanceState.getInt("RecipePosition");
            setTitle(savedInstanceState.getString("Title"));

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("RecipeList", mRecipeList);
        outState.putInt("RecipePosition", mRecipeIndex);
        outState.putString("Title", mRecipeList.get(mRecipeIndex).getName());
    }

    @Override
    public void onStepClick(int position) {
       displayStepAtPosition(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        mSharedPreferences = getSharedPreferences("WidgetPrefs", MODE_PRIVATE);
        Recipe recipe = mRecipeList.get(mRecipeIndex);

        if (itemClicked == R.id.i_menu_main_add_widget){
            mSharedPreferences
                    .edit()
                    .putInt(PREFERENCES_RECIPE_ID, recipe.getId())
                    .putString(PREFERENCES_WIDGET_RECIPE, recipe.getName())
                    .putString(PREFERENCES_WIDGET_INGREDIENTS, ingredientsToString())
                    .apply();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
            IngredientsWidgetProvider.updateIngredientsWidgets(this, appWidgetManager, appWidgetIds);
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayRecipeAtPosition (int position ){
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setRecipe(mRecipeList.get(position));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fl_detail_container, recipeDetailFragment)
                .commit();
    }

    private void displayStepAtPosition (int position){
        ArrayList<Step> stepListToSend = mRecipeList.get(mRecipeIndex).getSteps();
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepList(stepListToSend);
        stepDetailFragment.setCurrentStepId(position);
        stepDetailFragment.setRecipeName(mRecipeList.get(mRecipeIndex).getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if( mTwoPane ){
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_detail_container_right, stepDetailFragment)
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_detail_container, stepDetailFragment)
                    .commit();
        }

    }

    private String ingredientsToString(){
        StringBuilder outString = new StringBuilder();
        String servings = String.valueOf(mRecipeList.get(mRecipeIndex).getServings());

        outString.append(getString(R.string.servings)).append(": ").append(servings).append("\n");

        ArrayList<Ingredient> ingredients = mRecipeList.get(mRecipeIndex).getIngredients();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                outString.append("- ").append(ingredients.get(i).getIngredient()).append("\n\t\t")
                        .append(ingredients.get(i).getQuantity()).append(" ")
                        .append(ingredients.get(i).getMeasure()).append("\n");
            }
        }
        return outString.toString();
    }

}
