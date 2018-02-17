package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        // Create instance of Sandwitch Class
        Sandwich sandwich = new Sandwich();

        // Create JSON Object
        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonName = jsonObject.getJSONObject("name");


        // Sandwich Name
        String mainName = jsonName.getString("mainName");
        sandwich.setMainName(mainName);
        //Log.d("JSONTest", "UTILIS Start: " + jsonName);

        // Sandwich Also Knnown As
        JSONArray alsoKnownAs = jsonName.getJSONArray("alsoKnownAs");
        ArrayList <String> alsoKnownAsList = new ArrayList<>();
        for (int i = 0;  i < alsoKnownAs.length(); i++){
            alsoKnownAsList.add(alsoKnownAs.getString(i));
        }
        sandwich.setAlsoKnownAs(alsoKnownAsList);

        // Sandwich Place of Origin
        String placeOfOrigin = jsonObject.getString("placeOfOrigin");
        sandwich.setPlaceOfOrigin(placeOfOrigin);

        // Sandwich Description
        String description = jsonObject.getString("description");
        sandwich.setDescription(description);

        // Sandwich Image
        String image = jsonObject.getString("image");
        sandwich.setImage(image);

        // Sandwich ingredients
        JSONArray ingredients = jsonObject.getJSONArray("ingredients");
        ArrayList <String> ingredientsList = new ArrayList<>();
        for (int i = 0;  i < ingredients.length(); i++){
            ingredientsList.add(ingredients.getString(i));
        }
        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }
}
