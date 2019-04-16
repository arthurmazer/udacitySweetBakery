package com.mazerapp.tec.udacityculinaria.modules.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.RecipeViewAcivity;

import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_RECIPE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetId, Recipes recipe, String recipeIngredients) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.widget_title_tv, recipe.getName());
        views.setTextViewText(R.id.widget_ingredients_tv, recipeIngredients);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
       /* for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
       RecipeIntentService.startActionUpdateWidgets(context);
    }

    public void startRecipesACtivity(Context context, Recipes recipe){
            Intent it = new Intent(context, RecipeViewAcivity.class);
            it.putExtra(EXTRA_RECIPE, recipe);
            context.startActivity(it);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

