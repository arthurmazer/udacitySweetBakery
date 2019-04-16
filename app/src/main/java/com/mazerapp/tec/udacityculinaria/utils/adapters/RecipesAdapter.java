package com.mazerapp.tec.udacityculinaria.utils.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.utils.interfaces.OnItemClicked;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private ArrayList<Recipes> recipesList;
    Context mContext;
    OnItemClicked onItemClicked;


    public RecipesAdapter(Context context, OnItemClicked onItemClicked) {
        this.recipesList = new ArrayList<>();
        this.mContext = context;
        this.onItemClicked = onItemClicked;
    }

    public void setList(ArrayList<Recipes> recipesList) {
        this.recipesList = recipesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_receipt, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Recipes recipe = recipesList.get(position);

        holder.tvRecipeTitle.setText(recipe.getName());
        holder.imageRecipeUrl = recipe.getImage();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvRecipeTitle;
        String imageRecipeUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            tvRecipeTitle = itemView.findViewById(R.id.tv_recipe_title);
            ImageView ivRecipe = itemView.findViewById(R.id.iv_recipe);
            itemView.setOnClickListener(this);


            //Dava pra colocar uma imagem pra cada um igual vi outros projetos fazendo,
            //mas não vi muito sentido nisso em termos de escalabilidade
            if (imageRecipeUrl == null || imageRecipeUrl.isEmpty()){
                Picasso.with(mContext).load(R.drawable.default_background).into(ivRecipe);
            }else{
                Picasso.with(mContext).load(imageRecipeUrl).into(ivRecipe);
            }

        }

        @Override
        public void onClick(View v) {
            onItemClicked.onClicked(recipesList.get(getAdapterPosition()));
        }
    }
}
