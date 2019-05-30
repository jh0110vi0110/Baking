package com.vi.baking.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vi.baking.R;
import com.vi.baking.model.Step;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment {
    private static final String TAG = "StepDetailFragment";
    //private Step mStep;
    private ArrayList<Step> mStepList;
    private int mcurrentStepId;


    public StepDetailFragment() {
        // Required empty public constructor
    }

    //public void setStep (Step step){
    //    this.mStep = step;
    //}
    public void setStepList (ArrayList<Step> stepList){
        this.mStepList = stepList;
    }

    public void setCurrentStepId (int id){
        this.mcurrentStepId = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.d(TAG, "onCreateView: Launched for step: " + mStep.getShortDescription());
        Log.d(TAG, "onCreateView: Launched for step: " + mStepList.get(mcurrentStepId).getShortDescription());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_detail, container, false);
    }

}
