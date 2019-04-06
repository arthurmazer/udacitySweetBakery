package com.mazerapp.tec.udacityculinaria.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mazerapp.tec.udacityculinaria.models.Recipes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetData {

    public static ArrayList<Recipes> getRecipesListFromJSON() {
        URL urlJson;
        HttpURLConnection con;
        BufferedReader bufferedReader;
        StringBuilder result = new StringBuilder();
        String readLine;
        String strJson;
        try {
            urlJson = new URL(Constants.URL_RECIPES);
            con = (HttpURLConnection) urlJson.openConnection();
            con.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((readLine = bufferedReader.readLine()) != null) {
                result.append(readLine);
            }
            bufferedReader.close();
            strJson = result.toString();

            Gson gson = new Gson();
            return gson.fromJson(strJson, new TypeToken<List<Recipes>>(){}.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
