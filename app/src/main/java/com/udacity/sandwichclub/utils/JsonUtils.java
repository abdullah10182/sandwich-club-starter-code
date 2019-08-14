
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
            JSONObject sandwichData = new JSONObject(json);
            JSONObject sandwichNameObj = sandwichData.getJSONObject("name");
            String mainName = sandwichNameObj.getString("mainName");
            JSONArray alsoKnownAsArray = sandwichNameObj.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<String>();
            for(int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
            String placeOfOrigin = sandwichData.getString("placeOfOrigin");
            String description = sandwichData.getString("description");
            String image = sandwichData.getString("image");
            JSONArray ingredientsArray = sandwichData.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            for(int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
            Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients );
            //public Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients)
            return sandwich;
        } catch ( JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
//public class JsonUtils {
//
//    public static Sandwich parseSandwichJson(String json) {
//        JSONObject sandwichData = new JSONObject(json);
//        JSONObject weather = forecast.getJSONObject("weather");
//        return weather.getString("condition");
//    }
//}
