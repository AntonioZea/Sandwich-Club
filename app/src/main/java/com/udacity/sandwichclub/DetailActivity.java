package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.iv_image);

        Intent intent = getIntent();

        if (intent == null) closeOnError();
        else {

            int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
            if (position == DEFAULT_POSITION) {
                // EXTRA_POSITION not found in intent
                closeOnError();
                return;
            }

            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];

            sandwich = JsonUtils.parseSandwichJson(json);

            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI();

            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        TextView aka = findViewById(R.id.tv_aka);
        TextView description = findViewById(R.id.tv_description);
        TextView ingredients = findViewById(R.id.tv_ingredients);
        TextView placeOfOrigin = findViewById(R.id.tv_origin);

        StringBuilder stringBuilder = new StringBuilder();

        // AKA
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            stringBuilder.append(getResources().getString(R.string.detail_also_known_as_label));
            stringBuilder.append(" ");
            stringBuilder.append(makeStringFromList(sandwich.getAlsoKnownAs()));
            aka.setText(stringBuilder);
            aka.setVisibility(View.VISIBLE);
        }

        // Description.
        if (!sandwich.getDescription().isEmpty()) {
            description.setText(sandwich.getDescription());
            description.setVisibility(View.VISIBLE);
        }

        // Ingredients.
        if (!sandwich.getIngredients().isEmpty()) {
            stringBuilder.setLength(0);
            stringBuilder.append(getResources().getString(R.string.detail_ingredients_label));
            stringBuilder.append(" ");
            stringBuilder.append(makeStringFromList(sandwich.getIngredients()));
            ingredients.setText(stringBuilder);
            ingredients.setVisibility(View.VISIBLE);
        }

        // Description.
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            stringBuilder.setLength(0);
            stringBuilder.append(getResources().getString(R.string.detail_place_of_origin_label));
            stringBuilder.append(" ");
            stringBuilder.append(sandwich.getPlaceOfOrigin());
            stringBuilder.append(".");
            placeOfOrigin.setText(stringBuilder);
            placeOfOrigin.setVisibility(View.VISIBLE);
        }

    }

    private String makeStringFromList(List<String> list) {

        // Found StringJoiner is a better fit but requires API 24?
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {

            if (list.size() > 1 && i == list.size() - 1) stringBuilder.append(" and ");
            else if (i < list.size() - 1  && i > 0)
                stringBuilder.append(", ");

            stringBuilder.append(list.get(i));

        }

        stringBuilder.append(".");

        return stringBuilder.toString();

    }

}
