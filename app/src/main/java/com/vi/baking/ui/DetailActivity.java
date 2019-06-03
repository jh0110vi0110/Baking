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
    //private static final String TAG = "DetailActivity";
    private ArrayList<Recipe> mRecipeList;
    private int mRecipeIndex;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intentThatStartedThisActivity = getIntent();
        Bundle b = intentThatStartedThisActivity.getExtras();

        if (savedInstanceState == null){
            if(b != null){
                mRecipeList = b.getParcelableArrayList("RecipeList");
                mRecipeIndex = b.getInt("RecipeId");
                setTitle(mRecipeList.get(mRecipeIndex).getName());
                displayRecipeAtPosition(mRecipeIndex);
            }
        } else {
            mRecipeList = savedInstanceState.getParcelableArrayList("RecipeList");
            mRecipeIndex = savedInstanceState.getInt("RecipePosition");
            setTitle(savedInstanceState.getString("Title"));
        }
        if ( findViewById(R.id.fl_detail_container_right) != null){
            mTwoPane = true;
            displayStepAtPosition(0);
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

    private void displayRecipeAtPosition ( int position ){
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
        if( mTwoPane){
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_detail_container_right, stepDetailFragment)
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.fl_detail_container, stepDetailFragment)
                    .commit();
        }

    }

}
