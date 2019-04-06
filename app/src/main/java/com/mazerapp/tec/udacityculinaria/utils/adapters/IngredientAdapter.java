package com.mazerapp.tec.udacityculinaria.utils.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Ingredients;
import com.mazerapp.tec.udacityculinaria.utils.Commons;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private ArrayList<Ingredients> ingredientsList;
    Context mContext;


    public IngredientAdapter(Context context) {
        this.ingredientsList = new ArrayList<>();
        this.mContext = context;
    }

    public void setList(ArrayList<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Ingredients ingredient = ingredientsList.get(position);

        holder.tvIngredient.setText(String.format(mContext.getString(R.string.ingredient_field), Commons.getStringDouble(ingredient.getQuantity()),
                ingredient.getMeasure(), ingredient.getIngredient()));
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
        return ingredientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvIngredient;

        public ViewHolder(View itemView) {
            super(itemView);

            tvIngredient = itemView.findViewById(R.id.tv_ingredient);
        }
    }
}
