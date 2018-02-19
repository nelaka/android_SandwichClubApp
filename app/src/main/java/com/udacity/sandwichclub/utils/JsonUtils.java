package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.DetailActivity;
import com.udacity.sandwichclub.model.Sandwich;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // Create a JSONObject from the JSON response string
        JSONObject baseJsonResponse = null;
        try {
            baseJsonResponse = new JSONObject(json);
            if (baseJsonResponse.has("name")) {
                // Extract the JSONObject associated with the key called "name"
                JSONObject nameArray = baseJsonResponse.getJSONObject("name");

                String mainName = "";
                if (nameArray.has("mainName")) {
                    mainName = nameArray.getString("mainName");
                }

                List<String> alsoKnownAs = new ArrayList<>();
                if (nameArray.has("alsoKnownAs")) {

                    // Extract the JSONArray associated with the key called alsoKnownAs
                    JSONArray alsoKnownAsArray = nameArray.getJSONArray("alsoKnownAs");

                    // For each alsoKnownAs item in the alsoKnownAsArray, add its value to alsoKnownAs list
                    for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                        String alsoKnownAsString = alsoKnownAsArray.getString(i);
                        alsoKnownAs.add(alsoKnownAsString);
                    }
                }

                String placeOfOrigin = baseJsonResponse.getString("placeOfOrigin");

                String description = baseJsonResponse.getString("description");

                String image = baseJsonResponse.getString("image");

                List<String> ingredients = new ArrayList<>();
                if (baseJsonResponse.has("ingredients")) {

                    // Extract the JSONArray associated with the key called authors
                    JSONArray ingredientsArray = baseJsonResponse.getJSONArray("ingredients");

                    // For each ingredient in the ingredientsArray, add its value to ingredients' list
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        String ingredientsString = ingredientsArray.getString(i);
                        ingredients.add(ingredientsString);
                    }
                }

                /** Create a new {@link Sandwich} object with the mainName, alsoKnownAs,
                 * placeOfOrigin, description, image and ingredients from the JSON response.
                 * */
                Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

                return sandwich;
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
