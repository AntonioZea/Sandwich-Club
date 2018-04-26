package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {

            final JSONObject sandwichDetails = new JSONObject(json);
            final JSONObject sandwichName = sandwichDetails.getJSONObject("name");
            final JSONArray aka = sandwichName.getJSONArray("alsoKnownAs");
            final JSONArray ingredients = sandwichDetails.getJSONArray("ingredients");

            List<String> alsoKnownAsList = new ArrayList<>(aka.length());
            List<String> ingredientsList = new ArrayList<>(ingredients.length());

            for (int i = 0; i < aka.length(); i++) alsoKnownAsList.add(aka.getString(i));

            for (int i = 0; i < ingredients.length(); i++)
                ingredientsList.add(ingredients.getString(i));

            Sandwich sandwich = new Sandwich();

            sandwich.setMainName(sandwichName.getString("mainName"));
            sandwich.setImage(sandwichDetails.getString("image"));
            sandwich.setAlsoKnownAs(alsoKnownAsList);
            sandwich.setIngredients(ingredientsList);
            sandwich.setPlaceOfOrigin(sandwichDetails.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichDetails.getString("description"));

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
