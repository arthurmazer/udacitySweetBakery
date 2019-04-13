package com.mazerapp.tec.udacityculinaria.modules.recipe_view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Ingredients;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.modules.base.BaseActivity;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.preparing.PrepareActivity;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.preparing.PrepareFragment;
import com.mazerapp.tec.udacityculinaria.utils.adapters.IngredientAdapter;
import com.mazerapp.tec.udacityculinaria.utils.adapters.StepsAdapter;
import com.mazerapp.tec.udacityculinaria.utils.interfaces.OnStepClicked;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_LIST_STEPS;
import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_STEP_SELECTED;

public class RecipeViewAcivity extends BaseActivity  implements OnStepClicked {

    private RecipeViewViewModel recipeViewModel;
    private boolean twoPane = false;

    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;


    @BindView(R.id.recipe_detail_container)
    @Nullable
    FrameLayout frameRecipes;

    private IngredientAdapter ingredientAdapter;
    private StepsAdapter stepsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        initViewModel();
        setupView();
    }

    private void setupView(){
        initButterKnife();
        checkTwoPane();
        setupIngredientsRecycler();
        setupStepsRecycler();
        getExtras();
        registerObservers();
    }


    private void setupIngredientsRecycler(){
        ingredientAdapter = new IngredientAdapter(getApplicationContext());
        rvIngredients.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvIngredients.setItemAnimator(new DefaultItemAnimator());
        rvIngredients.setAdapter(ingredientAdapter);
    }

    private void setupStepsRecycler(){
        stepsAdapter = new StepsAdapter(getApplicationContext(), this);
        rvSteps.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvSteps.setItemAnimator(new DefaultItemAnimator());
        rvSteps.setAdapter(stepsAdapter);
    }

    private void checkTwoPane() {
        if (frameRecipes != null){
            twoPane = true;
        }
    }

    @Override
    protected void onResume() {
        registerObservers();
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterObservers();
        super.onPause();
    }

    private void unregisterObservers() {
        recipeViewModel.getActualRecipesLiveData().removeObservers(this);
    }

    private void initButterKnife(){
        ButterKnife.bind(this);
    }

    private void getExtras() {
        recipeViewModel.getExtras(getIntent());
    }

    private void registerObservers() {
        recipeViewModel.getActualRecipesLiveData().observe(this, recipes -> {
            if (recipes == null) return;
            loadIngredients(recipes.getIngredients());
            loadSteps(recipes.getSteps());
        });
    }

    private void loadIngredients(ArrayList<Ingredients> listIngredients){ingredientAdapter.setList(listIngredients);}

    private void loadSteps(ArrayList<Steps> listSteps){
        stepsAdapter.setList(listSteps);
    }

    public void initViewModel(){
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewViewModel.class);
    }

    @Override
    public void onStepClicked(ArrayList<Steps> list, int selectedIndex) {
        if (twoPane){
            setPrepareFragment(list,selectedIndex);
        }else{
            startPrepareActivity(list,selectedIndex);
        }
    }

    public void startPrepareActivity(ArrayList<Steps> listSteps, int selectedIndex){
        Intent it = new Intent(this, PrepareActivity.class);
        it.putParcelableArrayListExtra(EXTRA_LIST_STEPS,listSteps);
        it.putExtra(EXTRA_STEP_SELECTED, selectedIndex);
        startActivity(it);
    }

    public void setPrepareFragment(ArrayList<Steps> listSteps, int selectedIndex){
        getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_container,
                PrepareFragment.newInstance(listSteps,selectedIndex)).commit();
    }
}
