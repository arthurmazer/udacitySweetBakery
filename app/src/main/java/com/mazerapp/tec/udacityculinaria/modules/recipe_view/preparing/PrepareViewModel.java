package com.mazerapp.tec.udacityculinaria.modules.recipe_view.preparing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.utils.Constants;

import java.util.ArrayList;

public class PrepareViewModel extends AndroidViewModel{

    private MutableLiveData<Boolean> showBtnPreviousLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> showBtnNextLiveData = new MutableLiveData<>();
    private MutableLiveData<Steps> currentStepLiveData = new MutableLiveData<>();
    private MutableLiveData<String> toolbarTextLiveData = new MutableLiveData<>();
    private int indexStepLoaded;
    private ArrayList<Steps> arraySteps;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public PrepareViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public void initViewModel(ArrayList<Steps> arraySteps){
        this.arraySteps = new ArrayList<>();
        this.arraySteps.addAll(arraySteps);
    }

    void setCurrentStepLoaded(int indexStepLoaded) {
        this.indexStepLoaded = indexStepLoaded;
        checkToolbarText(indexStepLoaded);
        currentStepLiveData.postValue(this.arraySteps.get(indexStepLoaded));
        checkButtonsVisibility(indexStepLoaded);
    }

    private void checkToolbarText(int indexStepLoaded){
        if (indexStepLoaded == 0){
            toolbarTextLiveData.postValue(context.getString(R.string.step_intro));
        }else{
            toolbarTextLiveData.postValue(String.format(context.getString(R.string.step_field), indexStepLoaded));
        }
    }

    private void checkButtonsVisibility(int indexStepLoaded){
        if (indexStepLoaded == 0){
            showBtnPreviousLiveData.postValue(false);
            showBtnNextLiveData.postValue(true);
        }else if(indexStepLoaded == arraySteps.size()-1){
            showBtnPreviousLiveData.postValue(true);
            showBtnNextLiveData.postValue(false);
        }else{
            showBtnPreviousLiveData.postValue(true);
            showBtnNextLiveData.postValue(true);
        }
    }

    public void getExtras(Bundle arguments){
        if (arguments == null) return;
        this.indexStepLoaded = arguments.getInt(Constants.EXTRA_STEP_SELECTED,0);
        initViewModel(arguments.getParcelableArrayList(Constants.EXTRA_LIST_STEPS));
        setCurrentStepLoaded(this.indexStepLoaded);
    }

    void onLastStepClicked(){
        setCurrentStepLoaded(this.indexStepLoaded-1);
    }

    void onNextStepClicked(){
        setCurrentStepLoaded(this.indexStepLoaded+1);
    }

    public MutableLiveData<Steps> getCurrentStepLiveData() {
        return currentStepLiveData;
    }


    public MutableLiveData<Boolean> getShowBtnPreviousLiveData() {
        return showBtnPreviousLiveData;
    }

    public MutableLiveData<String> getToolbarTextLiveData() {
        return toolbarTextLiveData;
    }

    public MutableLiveData<Boolean> getShowBtnNextLiveData() {
        return showBtnNextLiveData;
    }


}
