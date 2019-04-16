package com.mazerapp.tec.udacityculinaria.modules.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mazerapp.tec.udacityculinaria.models.Recipes;
import com.mazerapp.tec.udacityculinaria.utils.Constants;
import com.mazerapp.tec.udacityculinaria.utils.cache.SharedPrerencesManager;

import java.util.Set;

public class RecipeIntentService extends IntentService {


    public RecipeIntentService() {
        super("RecipeIntentService");
    }

    public static void startActionUpdateWidgets(Context context) {
        try {
            Intent intent = new Intent(context, RecipeIntentService.class);
            intent.setAction(Constants.ACTION_UPDATE_WIDGET);
            context.startService(intent);
        }catch(Exception ignored){
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;
        final String action = intent.getAction();
        if (action != null && action.equals(Constants.ACTION_UPDATE_WIDGET)) {

            SharedPrerencesManager sharedPrerencesManager = new SharedPrerencesManager(this);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidget.class));


            Recipes recipe = sharedPrerencesManager.getActualRecipe();
            Set<String> ingredientsSet = sharedPrerencesManager.getActualRecipeIngredients();
            if (ingredientsSet != null) {
                String ingredientsText = TextUtils.join("\n", ingredientsSet);
                RecipeWidget.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetIds, recipe, ingredientsText);
            }
        }
    }
}
