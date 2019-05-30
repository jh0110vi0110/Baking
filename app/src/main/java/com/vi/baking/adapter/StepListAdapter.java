package com.vi.baking.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vi.baking.R;
import com.vi.baking.model.Step;

import java.util.ArrayList;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {
    private OnStepListener mOnStepListener;
    private ArrayList<Step> mStepList;

    public StepListAdapter(ArrayList<Step> mStepList, OnStepListener mOnStepListener) {
        this.mOnStepListener = mOnStepListener;
        this.mStepList = mStepList;
    }

    public void setStepList (ArrayList<Step> steps){
        this.mStepList = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_list_item, viewGroup, false);
        return new StepViewHolder(view, mOnStepListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder stepViewHolder, int pos) {
        String stepId = String.valueOf(mStepList.get(pos).getId());
        stepViewHolder.stepIdTextView.setText(stepId);
        stepViewHolder.shortDescriptionTextView.setText(mStepList.get(pos).getShortDescription());
        String videoUrl = mStepList.get(pos).getVideoURL();
        if (videoUrl.equals("") || videoUrl.isEmpty()){
            stepViewHolder.playIconImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mStepList == null){
            return 0;
        }
        return mStepList.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnStepListener onStepListener;
        TextView stepIdTextView, shortDescriptionTextView;
        ImageView playIconImageView;

        public StepViewHolder(@NonNull View itemView, OnStepListener onStepListener) {
            super(itemView);
            this.onStepListener = onStepListener;
            // find views
            stepIdTextView = (TextView) itemView.findViewById(R.id.tv_step_list_id);
            shortDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_step_list_short_description);
            playIconImageView = (ImageView) itemView.findViewById(R.id.iv_step_list_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnStepListener.onStepClick(getAdapterPosition());
        }
    }

    public interface OnStepListener {
        void onStepClick (int position);
    }
}