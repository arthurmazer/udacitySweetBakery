package com.mazerapp.tec.udacityculinaria.modules.main;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.recipe.RecipeFragment;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.video_view.VideoViewFragment;
import com.mazerapp.tec.udacityculinaria.utils.adapters.RecipesAdapter;
import com.mazerapp.tec.udacityculinaria.utils.interfaces.OnItemClicked;
import com.mazerapp.tec.udacityculinaria.utils.interfaces.OnLoadRecipes;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements OnLoadRecipes, OnItemClicked {


    private MainViewModel mainViewModel;

    @BindView(R.id.rv_recipes)
    RecyclerView rvRecipes;

    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        setupView();
        loadRecipes();
    }

    public void setupView(){
        rvRecipes = findViewById(R.id.rv_recipes);
        recipesAdapter = new RecipesAdapter(this, this);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvRecipes.setItemAnimator(new DefaultItemAnimator());
        rvRecipes.setAdapter(recipesAdapter);
    }

    public void initViewModel(){
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    public void loadRecipes(){
        mainViewModel.loadRecipes(this);
    }

    @Override
    public void onLoadRecipes(ArrayList<Recipes> recipesList) {
        recipesAdapter.setList(recipesList);
    }

    @Override
    public void onClicked(Recipes recipe) {
        mainViewModel.startRecipeViewActivity(this, recipe);
    }


}
