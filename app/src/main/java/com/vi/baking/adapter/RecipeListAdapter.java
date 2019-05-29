package com.vi.baking.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vi.baking.R;
import com.vi.baking.model.Recipe;

import java.util.ArrayList;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    //private final static String TAG = "RecipeListAdapter";
    private OnRecipeListener mOnRecipeListener;
    private ArrayList<Recipe> mRecipeList;

    public RecipeListAdapter(ArrayList<Recipe> mRecipeList,  OnRecipeListener mOnRecipeListener) {
        this.mOnRecipeListener = mOnRecipeListener;
        this.mRecipeList = mRecipeList;
    }

    public void setRecipeList(ArrayList<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_item, viewGroup, false);
        return new RecipeViewHolder(view, mOnRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int pos) {
        String recipeName = mRecipeList.get(pos).getName();
        recipeViewHolder.recipeListTextView.setText(recipeName);
        String imageUrl=mRecipeList.get(pos).getImage();
        //Picasso.get()
         //       .load(imageUrl)
         //       .placeholder(R.drawable.ic_launcher_background)
         //       .error(R.drawable.ic_launcher_foreground)
        //        .into(recipeViewHolder.recipeListImageView);
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null){
            return 0;
        }
        return mRecipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnRecipeListener onRecipeListener;
        TextView recipeListTextView;
        //ImageView recipeListImageView;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);
            this.onRecipeListener = onRecipeListener;
            recipeListTextView = (TextView) itemView.findViewById(R.id.tv_recipe_list_name);
            //recipeListImageView = (ImageView) itemView.findViewById(R.id.iv_recipe_list_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }

    public interface OnRecipeListener {
        void onRecipeClick (int position);
    }

}
