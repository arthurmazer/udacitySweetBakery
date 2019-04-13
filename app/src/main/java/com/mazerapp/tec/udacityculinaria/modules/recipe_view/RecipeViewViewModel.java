package com.mazerapp.tec.udacityculinaria.modules.recipe_view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.Bundle;

import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.utils.Constants;

import java.util.ArrayList;

public class RecipeViewViewModel extends ViewModel {

    private MutableLiveData<Recipes> actualRecipesLiveData = new MutableLiveData<>();
    private int actualStep = 0;
    private ArrayList<Steps> listSteps = new ArrayList<>();

    public void getExtras(Intent it){
        Bundle bundle = it.getExtras();
        if (bundle == null) return;
        actualRecipesLiveData.postValue(bundle.getParcelable(Constants.EXTRA_RECIPE));
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
