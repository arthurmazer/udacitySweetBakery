package com.mazerapp.tec.udacityculinaria.utils.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mazerapp.tec.udacityculinaria.models.Ingredients;
import com.mazerapp.tec.udacityculinaria.models.Recipes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SharedPrerencesManager {

    private SharedPreferences sharedPref;

    private static final String SET_RECIPE_INGREDIENTS = "set_recipes_ingredients_session";
    private static final String SET_RECIPE_OBJECT = "set_recipe_object";

    public SharedPrerencesManager(Context context){
        sharedPref = context.getSharedPreferences("session_user" ,Context.MODE_PRIVATE);
    }

    public void setActualRecipe(Recipes recipe){
        SharedPreferences.Editor editor = sharedPref.edit();
        ArrayList<Ingredients> arrayIngredients = recipe.getIngredients();
        Set<String> setStringIngredients = new HashSet<>();

        for (int i = 0; i < arrayIngredients.size(); i++) {
            setStringIngredients.add(arrayIngredients.get(i).getIngredient());
        }

        Gson gson = new Gson();
        String jsonRecipe = gson.toJson(recipe);
        editor.putString(SET_RECIPE_OBJECT, jsonRecipe);

        editor.putStringSet(SET_RECIPE_INGREDIENTS, setStringIngredients);
        editor.apply();

    }

    public Set<String> getActualRecipeIngredients(){
        return sharedPref.getStringSet(SET_RECIPE_INGREDIENTS, null);
    }

    public Recipes getActualRecipe(){
        Gson gson = new Gson();
        String jsonIngreditns = sharedPref.getString(SET_RECIPE_OBJECT, "");
        if (jsonIngreditns != null && !jsonIngreditns.isEmpty()){
            return gson.fromJson(jsonIngreditns, Recipes.class);
        }else
            return null;
    }
}
