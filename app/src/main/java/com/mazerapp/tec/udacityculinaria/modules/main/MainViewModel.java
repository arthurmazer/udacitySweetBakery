package com.mazerapp.tec.udacityculinaria.modules.main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.RecipeViewAcivity;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.recipe.RecipeFragment;
import com.mazerapp.tec.udacityculinaria.utils.GetData;
import com.mazerapp.tec.udacityculinaria.utils.interfaces.OnLoadRecipes;

import java.util.ArrayList;

import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_RECIPE;

class MainViewModel extends ViewModel {

    @SuppressLint("StaticFieldLeak")
    void loadRecipes(final OnLoadRecipes onLoadRecipes){
        new AsyncTask<String, String, ArrayList<Recipes>>() {
            @Override
            protected ArrayList<Recipes> doInBackground(String... strings) {
                return GetData.getRecipesListFromJSON();
            }

            @Override
            protected void onPostExecute(ArrayList<Recipes> recipes) {
                onLoadRecipes.onLoadRecipes(recipes);
                super.onPostExecute(recipes);
            }
        }.execute();
    }

    void startRecipeViewActivity(Context activity, Recipes recipe){
        Intent it = new Intent(activity, RecipeViewAcivity.class);
        it.putExtra(EXTRA_RECIPE, recipe);
        activity.startActivity(it);
    }
}
