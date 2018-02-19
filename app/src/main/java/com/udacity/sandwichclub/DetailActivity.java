package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView placeOfOriginTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        String alsoKnownAs = "";
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();

            StringBuilder csvBuilder = new StringBuilder();

            for (String alsoKnownAsItem : alsoKnownAsList) {
                csvBuilder.append(alsoKnownAsItem);
                csvBuilder.append(", ");
            }
            alsoKnownAs = csvBuilder.toString();

            //Remove last comma
            alsoKnownAs = alsoKnownAs.substring(0, alsoKnownAs.length() - 2);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        StringBuilder csvBuilder2 = new StringBuilder();

        for (String ingredientsItem : ingredientsList) {
            csvBuilder2.append(ingredientsItem);
            csvBuilder2.append(", ");
        }
        String ingredients = csvBuilder2.toString();

        //Remove last comma
        ingredients = ingredients.substring(0, ingredients.length() - 2);

        alsoKnownAsTextView.setText(alsoKnownAs);
        ingredientsTextView.setText(ingredients);
        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        descriptionTextView.setText(sandwich.getDescription());
    }
}
