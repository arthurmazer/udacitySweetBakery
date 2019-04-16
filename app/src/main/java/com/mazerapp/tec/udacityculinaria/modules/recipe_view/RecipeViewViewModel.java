package com.mazerapp.tec.udacityculinaria.modules.recipe_view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.modules.widget.RecipeIntentService;
import com.mazerapp.tec.udacityculinaria.utils.Constants;
import com.mazerapp.tec.udacityculinaria.utils.cache.SharedPrerencesManager;

import java.util.ArrayList;

public class RecipeViewViewModel extends ViewModel {

    private MutableLiveData<Recipes> actualRecipesLiveData = new MutableLiveData<>();
    private int actualStep = 0;
    private ArrayList<Steps> listSteps = new ArrayList<>();
    private SharedPrerencesManager sharedPrerencesManager;

    public void getExtras(Context context, Intent it){
        Bundle bundle = it.getExtras();
        if (bundle == null) return;
        Recipes recipe = bundle.getParcelable(Constants.EXTRA_RECIPE);
        if (recipe != null){
            actualRecipesLiveData.postValue(bundle.getParcelable(Constants.EXTRA_RECIPE));
            saveIngredientOnCache(context, recipe);
            RecipeIntentService.startActionUpdateWidgets(context);
        }

    }

    public void saveIngredientOnCache(Context context, Recipes recipe){
        sharedPrerencesManager = new SharedPrerencesManager(context);
        sharedPrerencesManager.setActualRecipe(recipe);
    }


    public MutableLiveData<Recipes> getActualRecipesLiveData() {
        return actualRecipesLiveData;
    }

    public int getActualStep() {
        return actualStep;
    }

    public void setActualStep(int actualStep) {
        this.actualStep = actualStep;
    }

    public ArrayList<Steps> getListSteps() {
        return listSteps;
    }

    public void setListSteps(ArrayList<Steps> listSteps) {
        this.listSteps = listSteps;
    }
}
