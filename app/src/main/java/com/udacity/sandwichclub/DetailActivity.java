package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        TextView originTv = findViewById(R.id.origin_tv);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if(placeOfOrigin.isEmpty())
            originTv.setText("Unkown");
        else
            originTv.setText(placeOfOrigin);

        List<String> alsoKnownAsArray = sandwich.getAlsoKnownAs();
        if(alsoKnownAsArray.size() > 0){
            TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
            LinearLayout alsoKnownAsLl = findViewById(R.id.also_known_ll);
            alsoKnownAsLl.setVisibility(View.VISIBLE);
            for(int i = 0; i < alsoKnownAsArray.size(); i++)  {
                alsoKnownAsTv.append(alsoKnownAsArray.get(i));
                if(i != alsoKnownAsArray.size() - 1)
                    alsoKnownAsTv.append(", ");
            }
        }

        List<String> ingredientsArray = sandwich.getIngredients();
        if(ingredientsArray.size() > 0){
            TextView ingredients = findViewById(R.id.ingredients_tv);
            for(int i = 0; i < ingredientsArray.size(); i++)  {
                ingredients.append(ingredientsArray.get(i));
                if(i != ingredientsArray.size() - 1)
                    ingredients.append(", ");
            }
        }

        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
