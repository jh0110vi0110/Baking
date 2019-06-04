package com.vi.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.vi.baking.R;
import com.vi.baking.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {
    //private static final String TAG = "IngredientsWidgProv";
    public static final String PREFERENCES_WIDGET_RECIPE = "WIDGET_RECIPE";
    public static final String PREFERENCES_WIDGET_INGREDIENTS = "WIDGET_INGREDIENTS";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        SharedPreferences sharedPreferences = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);

        //Set Text on Widget from SharedPrefs
        views.setTextViewText(R.id.tv_ingredients_widget_title,
                sharedPreferences.getString(PREFERENCES_WIDGET_RECIPE, ""));
        views.setTextViewText(R.id.tv_ingredients_widget_ingredients,
                sharedPreferences.getString(PREFERENCES_WIDGET_INGREDIENTS, ""));

        //Open App when widget is clicked
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.tv_ingredients_widget_title, pendingIntent);
        views.setOnClickPendingIntent(R.id.tv_ingredients_widget_ingredients, pendingIntent);

        // update widgets
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    //Trigger updates from other classes
    public static void updateIngredientsWidgets (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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

