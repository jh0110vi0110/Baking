package com.vi.baking.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.vi.baking.R;
import com.vi.baking.model.Step;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment {
    private static final String TAG = "StepDetailFragment";
    //private Step mStep;
    private String mRecipeName = "";
    private ArrayList<Step> mStepList;
    private int mCurrentStepId;
    private OnStepListener mOnStepListener;

    private Button mPreviousButton, mNextButton;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private SimpleExoPlayer mPlayer;

    public interface OnStepListener{
        void onStepClick(int position);
    }


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
        this.mCurrentStepId = id;
    }

    public void setRecipeName (String name){
        this.mRecipeName = name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        mOnStepListener = (DetailActivity)getActivity();
        if (savedInstanceState != null){
            mStepList = savedInstanceState.getParcelableArrayList("StepList");
            mCurrentStepId = savedInstanceState.getInt("CurrentStep");
            mRecipeName = savedInstanceState.getString("Title");

            try{
                ((DetailActivity) getActivity()).setTitle(mRecipeName);
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }

        TextView shortDescriptionTextView = rootView.findViewById(R.id.tv_step_detail_fragment_short_description);
        mPreviousButton = rootView.findViewById(R.id.btn_step_detail_fragment_previous);
        mNextButton = rootView.findViewById(R.id.btn_step_detail_fragment_next);
        TextView descriptionTextView = rootView.findViewById(R.id.tv_step_detail_fragment_description);
        CardView exoPlayerContainer = rootView.findViewById(R.id.cv_step_detail_fragment_player_border);
        mSimpleExoPlayerView = rootView.findViewById(R.id.sepv_step_detail_fragment_player);
        mSimpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        //If there is a Video to play
        if( mStepList.get(mCurrentStepId).getVideoURL().equals("")){
            exoPlayerContainer.setVisibility(View.GONE);
            //mSimpleExoPlayerView.setVisibility(View.INVISIBLE);
        }else {
            initializePlayer(Uri.parse(mStepList.get(mCurrentStepId).getVideoURL()));

        }


        //Previous Button Functionality
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreviousButton.setBackgroundResource(R.drawable.ic_left_arrow);
                if ( mCurrentStepId != 0) {
                    mOnStepListener.onStepClick(mCurrentStepId - 1);
                }else{
                    mOnStepListener.onStepClick(mStepList.size() -1 );
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextButton.setBackgroundResource(R.drawable.ic_right_arrow);
                if ( mCurrentStepId != mStepList.size() -1 ){
                    mOnStepListener.onStepClick(mCurrentStepId + 1);
                }else {
                    mOnStepListener.onStepClick(0);
                }

            }
        });


        //Log.d(TAG, "onCreateView: Launched for step: " + mStepList.get(mCurrentStepId).getShortDescription());
        shortDescriptionTextView.setText(mStepList.get(mCurrentStepId).getShortDescription());
        descriptionTextView.setText(mStepList.get(mCurrentStepId).getDescription());



        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("StepList", mStepList);
        outState.putInt("CurrentStep", mCurrentStepId);
        outState.putString("Title", mRecipeName);

    }

    //adapted from ClassicalMoviesQuiz excercise
    private void initializePlayer(Uri mediaUri) {
        if (mPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            //mPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Baking Time");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);
        }
    }


    /*
    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }
    */

}
