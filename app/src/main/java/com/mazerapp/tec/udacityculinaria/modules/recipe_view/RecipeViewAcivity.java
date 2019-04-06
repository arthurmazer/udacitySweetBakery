package com.mazerapp.tec.udacityculinaria.modules.recipe_view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.modules.base.BaseActivity;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.preparing.PrepareFragment;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.recipe.RecipeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeViewAcivity extends BaseActivity {

    private RecipeViewViewModel recipeViewModel;


    @BindView(R.id.frame_recipes)
    @Nullable
    FrameLayout frameRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        initButterKnife();
        initViewModel();
        getExtras();
        registerObservers();
    }

    private void initButterKnife(){
        ButterKnife.bind(this);
    }

    private void getExtras() {
        recipeViewModel.getExtras(getIntent());
    }

    private void registerObservers() {
        recipeViewModel.getActualRecipesLiveData().observe(this, this::setRecipesFragment);
    }

    public void initViewModel(){
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewViewModel.class);
    }

    public void setRecipesFragment(Recipes recipe){
        if (frameRecipes != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_recipes,
                    RecipeFragment.newInstance(recipe)).commit();
            onStepSelected(recipe.getSteps(), 0);
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment,
                    RecipeFragment.newInstance(recipe)).commit();
        }

    }

    public void setPrepareFragment(ArrayList<Steps> listSteps, int indexSelected){
        recipeViewModel.setActualStep(indexSelected);
        recipeViewModel.setListSteps(listSteps);
        if (frameRecipes != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_preparing,
                    PrepareFragment.newInstance(listSteps,indexSelected)).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment,
                    PrepareFragment.newInstance(listSteps,indexSelected)).commit();
        }
    }

    public void onStepSelected(ArrayList<Steps> listSteps, int indexSelected){
        setPrepareFragment(listSteps,indexSelected);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            if (!isPhone){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment,
                        PrepareFragment.newInstance(recipeViewModel.getListSteps(),recipeViewModel.getActualStep())).commit();
            }
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
