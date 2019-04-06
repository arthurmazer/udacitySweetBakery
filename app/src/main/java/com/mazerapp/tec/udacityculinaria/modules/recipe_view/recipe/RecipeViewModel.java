package com.mazerapp.tec.udacityculinaria.modules.recipe_view.recipe;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.utils.Constants;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<Recipes> actualRecipeLiveData = new MutableLiveData<>();



    public void getExtras(Bundle arguments){
        if (arguments == null) return;
        Recipes actualRecipe = arguments.getParcelable(Constants.EXTRA_RECIPE);
        actualRecipeLiveData.postValue(actualRecipe);
    }

    public MutableLiveData<Recipes> getActualRecipeLiveData() {
        return actualRecipeLiveData;
    }
}
