package com.mazerapp.tec.udacityculinaria.modules.recipe_view.recipe;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Ingredients;
import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.RecipeViewAcivity;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.preparing.PrepareViewModel;
import com.mazerapp.tec.udacityculinaria.utils.Constants;
import com.mazerapp.tec.udacityculinaria.utils.adapters.IngredientAdapter;
import com.mazerapp.tec.udacityculinaria.utils.adapters.StepsAdapter;
import com.mazerapp.tec.udacityculinaria.utils.interfaces.OnStepClicked;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_RECIPE;

public class RecipeFragment extends Fragment implements OnStepClicked {

    private RecipeViewModel recipeViewModel;
    private IngredientAdapter ingredientAdapter;
    private StepsAdapter stepsAdapter;

    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() == null) return;
        ButterKnife.bind(this,view);
        recipeViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
        initViewModel();
        getExtras();
        setupView();
        registerObservers();
    }

    private void registerObservers() {
        recipeViewModel.getActualRecipeLiveData().observe(this, it -> {
            if (it != null) {
                setupToolbar(it.getName());
                loadIngredients(it.getIngredients());
                loadSteps(it.getSteps());
            }
        });
    }

    public static RecipeFragment newInstance(Recipes actualRecipe) {
        RecipeFragment myFragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_RECIPE, actualRecipe);
        myFragment.setArguments(args);
        return myFragment;
    }


    private void initViewModel() {
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
    }

    private void getExtras(){
        recipeViewModel.getExtras(getArguments());
    }

    private void setupToolbar(String title){
        if (getActivity() == null) return;
        getActivity().setTitle(title);
    }

    private void setupView(){
        setupIngredientsRecycler();
        setupStepsRecycler();
    }

    private void setupIngredientsRecycler(){
        if (getActivity() == null) return;
        ingredientAdapter = new IngredientAdapter(getContext());
        rvIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIngredients.setItemAnimator(new DefaultItemAnimator());
        rvIngredients.setAdapter(ingredientAdapter);
    }

    private void setupStepsRecycler(){
        if (getActivity() == null) return;
        stepsAdapter = new StepsAdapter(getContext(), this);
        rvSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSteps.setItemAnimator(new DefaultItemAnimator());
        rvSteps.setAdapter(stepsAdapter);
    }

    private void loadIngredients(ArrayList<Ingredients> listIngredients){
        ingredientAdapter.setList(listIngredients);
    }

    private void loadSteps(ArrayList<Steps> listSteps){
        stepsAdapter.setList(listSteps);
    }

    @Override
    public void onStepClicked(ArrayList<Steps> listSteps, int selectedIndex) {
        if (getActivity() == null) return;
        ((RecipeViewAcivity) getActivity()).onStepSelected(listSteps, selectedIndex);
    }

}
