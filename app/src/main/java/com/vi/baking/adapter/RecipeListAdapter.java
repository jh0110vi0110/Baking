package com.vi.baking.adapter;

import android.content.Context;
import android.net.Uri;
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
    private final static String TAG = "RecipeListAdapter";
    private OnRecipeListener mOnRecipeListener;
    private ArrayList<Recipe> mRecipeList;
    //private Context mContext;

    public RecipeListAdapter(ArrayList<Recipe> mRecipeList,  OnRecipeListener mOnRecipeListener) {
        Log.d(TAG, "RecipeListAdapter: instaniated");
        this.mOnRecipeListener = mOnRecipeListener;
        this.mRecipeList = mRecipeList;
    }

    public void setRecipeList(ArrayList<Recipe> mRecipeList) {
        Log.d(TAG, "setRecipeList: called");
        for (int i = 0; i < mRecipeList.size(); i++){
            Log.d(TAG, "setRecipeList: " + mRecipeList.get(i).getName());
            Log.d(TAG, "setRecipeList: " + mRecipeList.get(i).getSteps().get(i).getDescription());

        }
        this.mRecipeList = mRecipeList;
        //this.mContext = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_item, viewGroup, false);
        return new RecipeViewHolder(view, mOnRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int pos) {
        String recipeName = mRecipeList.get(pos).getName();
        recipeViewHolder.recipeListTextView.setText(recipeName);
        String imageUrl=mRecipeList.get(pos).getImage();
        Log.d(TAG, "onBindViewHolder: Recipe Name Bound: " + recipeName);
        Log.d(TAG, "onBindViewHolder: imageUrl: " + imageUrl);
        //Picasso.get()
         //       .load(imageUrl)
         //       .placeholder(R.drawable.ic_launcher_background)
         //       .error(R.drawable.ic_launcher_foreground)
        //        .into(recipeViewHolder.recipeListImageView);
        
        /*
        if (imageUrl!="") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            
            //Picasso.with(mContext).load(builtUri).into(recipeViewHolder.recipeListImageView);
        }
        */
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
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
            Log.d(TAG, "RecipeViewHolder: instansiated");
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
    //public interface OnRecipeListener {
     //   void onRecipeClick (Recipe clickedRecipe);
    //}

}

/*  Popular Movies based

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private OnRecipeListener mOnRecipeListener;
    private ArrayList<Recipe> mRecipeList;

    public RecipeListAdapter(OnRecipeListener mOnRecipeListener, ArrayList<Recipe> mRecipeList) {
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
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        recipeViewHolder.recipeListTextView.setText(mRecipeList.get(i).getName());
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

        public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);
            this.onRecipeListener = onRecipeListener;
            recipeListTextView = (TextView) itemView.findViewById(R.id.tv_recipe_list_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }

    public interface OnRecipeListener {
        void onRecipeClick (int position);
    }

} */

/*
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    ArrayList<Recipe> mRecipes;
    //Context mContext;
    final private ListItemClickListener lOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Recipe clickedItemIndex);
    }

    public RecipeListAdapter(ListItemClickListener listener) {
        lOnClickListener =listener;
    }


    public void setData(ArrayList<Recipe> recipesList, Context context) {
        lRecipes = recipesIn;
        mContext=context;
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_cardview_items;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup,  false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.textRecyclerView.setText(lRecipes.get(position).getName());
        String imageUrl=lRecipes.get(position).getImage();

        if (imageUrl!="") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(builtUri).into(holder.imageRecyclerView);
        }

    }

    @Override
    public int getItemCount() {
        return lRecipes !=null ? lRecipes.size():0 ;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textRecyclerView;
        ImageView imageRecyclerView;


        public RecipeViewHolder(View itemView) {
            super(itemView);

            textRecyclerView = (TextView) itemView.findViewById(R.id.title);
            imageRecyclerView = (ImageView) itemView.findViewById(R.id.recipeImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(lRecipes.get(clickedPosition));
        }

    }
} */
