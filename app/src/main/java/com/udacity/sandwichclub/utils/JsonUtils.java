package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_MAIN_NAME_KEY = "mainName";
    private static final String JSON_ALSO_KNOWN_KEY = "alsoKnownAs";
    private static final String JSON_ORIGIN_KEY = "placeOfOrigin";
    private static final String JSON_DESCRIPTION_KEY = "description";
    private static final String JSON_IMAGE_KEY = "image";
    private static final String JSON_INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        // Create a JSONObject from the JSON response string
        JSONObject baseJsonResponse = null;
        try {
            baseJsonResponse = new JSONObject(json);
            if (baseJsonResponse.has(JSON_NAME_KEY)) {
                // Extract the JSONObject associated with the key called "name"
                JSONObject nameArray = baseJsonResponse.getJSONObject(JSON_NAME_KEY);

                String mainName = "";
                if (nameArray.has(JSON_MAIN_NAME_KEY)) {
                    mainName = nameArray.optString(JSON_MAIN_NAME_KEY);
                }

                List<String> alsoKnownAs = new ArrayList<>();
                if (nameArray.has(JSON_ALSO_KNOWN_KEY)) {

                    // Extract the JSONArray associated with the key called alsoKnownAs
                    JSONArray alsoKnownAsArray = nameArray.getJSONArray(JSON_ALSO_KNOWN_KEY);

                    // For each alsoKnownAs item in the alsoKnownAsArray, add its value to alsoKnownAs list
                    for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                        String alsoKnownAsString = alsoKnownAsArray.optString(i);
                        alsoKnownAs.add(alsoKnownAsString);
                    }
                }

                String placeOfOrigin = baseJsonResponse.optString(JSON_ORIGIN_KEY);

                String description = baseJsonResponse.optString(JSON_DESCRIPTION_KEY);

                String image = baseJsonResponse.optString(JSON_IMAGE_KEY);

                List<String> ingredients = new ArrayList<>();
                if (baseJsonResponse.has(JSON_INGREDIENTS_KEY)) {

                    // Extract the JSONArray associated with the key called authors
                    JSONArray ingredientsArray = baseJsonResponse.getJSONArray(JSON_INGREDIENTS_KEY);

                    // For each ingredient in the ingredientsArray, add its value to ingredients' list
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        String ingredientsString = ingredientsArray.optString(i);
                        ingredients.add(ingredientsString);
                    }
                }

                /* Return a new {@link Sandwich} object with the mainName, alsoKnownAs,
                  placeOfOrigin, description, image and ingredients from the JSON response.
                  */
                return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing the sandwich JSON results", e);
        }
        return null;
    }
}
