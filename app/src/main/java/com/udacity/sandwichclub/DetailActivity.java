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

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                // Error Image
                .error(R.drawable.no_connection)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        TextView alsoKnownHeader =  findViewById(R.id.also_known_header);
        TextView alsoKnownAs =  findViewById(R.id.also_known_tv);

        TextView placeOfOriginHeader =  findViewById(R.id.origin_header);
        TextView placeOfOrigin =  findViewById(R.id.origin_tv);

        TextView descriptionHeader =  findViewById(R.id.description_header);
        TextView description =  findViewById(R.id.description_tv);

        TextView ingredientsHeader =  findViewById(R.id.ingreddients_header);
        TextView ingredients =  findViewById(R.id.ingredients_tv);


        // Also Known As description
        List<String> alsoKnownList = sandwich.getAlsoKnownAs();
        if(alsoKnownList.isEmpty()){
            alsoKnownHeader.setVisibility(View.GONE);
            alsoKnownAs.setVisibility(View.GONE);
        }else{
            StringBuilder text = new StringBuilder();
            for (String s : alsoKnownList){
                text.append(s).append(", ");
            }
            alsoKnownAs.setText(text.substring(0, (text.length()-2)));
        }

        // Place of Origin description
        if(sandwich.getPlaceOfOrigin().isEmpty()){
            placeOfOriginHeader.setVisibility(View.GONE);
            placeOfOrigin.setVisibility(View.GONE);
        }else{
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        // Description
        if(sandwich.getDescription().isEmpty()){
            descriptionHeader.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
        }else{
            description.setText(sandwich.getDescription());
        }

        // Ingriedients List
        List<String> ingredientsList = sandwich.getIngredients();
        if(ingredientsList.isEmpty()){
            ingredientsHeader.setVisibility(View.GONE);
            ingredients.setVisibility(View.GONE);
        }else{
            StringBuilder text = new StringBuilder();
            for (String s : ingredientsList){
                text.append(s).append(", ");
            }
            ingredients.setText(text.substring(0, (text.length()-2)));
        }
    }
}
