package com.vi.baking.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.vi.baking.R;
import com.vi.baking.adapter.StepListAdapter;
import com.vi.baking.model.Recipe;
import com.vi.baking.model.Step;

import java.util.ArrayList;


public class DetailActivity extends AppCompatActivity implements StepListAdapter.OnStepListener, StepDetailFragment.OnStepListener {
    private static final String TAG = "DetailActivity";
    //private Recipe mRecipe;
    private ArrayList<Recipe> mRecipeList;
    private int mRecipeIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intentThatStartedThisActivity = getIntent();
        Bundle b = intentThatStartedThisActivity.getExtras();

        if (savedInstanceState == null){
            if(b != null){

            //if (intentThatStartedThisActivity != null) {
                //if (intentThatStartedThisActivity.hasExtra("RecipeList") &&
                   //     intentThatStartedThisActivity.hasExtra("RecipePosition")) {
                        //intentThatStartedThisActivity.hasExtra("RecipeId")) {
                    //mRecipe = intentThatStartedThisActivity.getParcelableExtra("Recipe");


                    //mRecipeList = intentThatStartedThisActivity.getParcelableExtra("RecipeList");
                    mRecipeList = b.getParcelableArrayList("RecipeList");
                    //mRecipeIndex = intentThatStartedThisActivity.getIntExtra("RecipePosition", 0);
                    mRecipeIndex = b.getInt("RecipeId");
                    //mId = intentThatStartedThisActivity.getIntExtra("RecipeId", 0);
                    //setTitle(mRecipe.getName());
                    setTitle(mRecipeList.get(mRecipeIndex).getName());

                    displayRecipeAtPosition(mRecipeIndex);




                    //Log.d(TAG, "onCreate: " + mRecipe.getName());
                    //Log.d(TAG, "onCreate: " + mRecipe.getIngredients().get(3).getIngredient());
                    //Log.d(TAG, "onCreate: " + mRecipe.getSteps().get(4).getDescription());
                }


        } else {
            mRecipeList = savedInstanceState.getParcelableArrayList("RecipeList");
            mRecipeIndex = savedInstanceState.getInt("RecipePosition");
            setTitle(savedInstanceState.getString("Title"));

            //mRecipe = savedInstanceState.getParcelable("Recipe");
            //setTitle(savedInstanceState.getString("Title"));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("RecipeList", mRecipeList);
        outState.putInt("RecipePosition", mRecipeIndex);
        outState.putString("Title", mRecipeList.get(mRecipeIndex).getName());
        //outState.putString("Title", mRecipe.getName());
        //outState.putParcelable("Recipe", mRecipe);
    }

    @Override
    public void onStepClick(int position) {
        //Step stepToSend = mRecipe.getSteps().get(position);
        //ArrayList<Step> stepListToSend = mRecipe.getSteps();
        ArrayList<Step> stepListToSend = mRecipeList.get(mRecipeIndex).getSteps();
        int stepIdToSend = stepListToSend.get(position).getId();

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepList(stepListToSend);
        //stepDetailFragment.setCurrentStepId(stepIdToSend);
        stepDetailFragment.setCurrentStepId(position);
        stepDetailFragment.setRecipeName(mRecipeList.get(mRecipeIndex).getName());
        //stepDetailFragment.setStep(stepToSend);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_detail_container, stepDetailFragment)
                .addToBackStack("StepDetailFragment")
                .commit();

        /*
                    RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                    recipeDetailFragment.setRecipe(mRecipe);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.fl_recipe_detail_container, recipeDetailFragment)
                            .commit();
         */

        /*
        Recipe recipeToSend = mRecipeList.get(position);
        Intent intentToStartDetailActivity = new Intent(getContext(), DetailActivity.class);
        intentToStartDetailActivity.putExtra("Recipe", recipeToSend);
        startActivity(intentToStartDetailActivity);
         */
    }

    private void displayRecipeAtPosition ( int position ){
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setRecipe(mRecipeList.get(position));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fl_detail_container, recipeDetailFragment)
                //.addToBackStack("DetailFragment")
                .commit();
    }
}
