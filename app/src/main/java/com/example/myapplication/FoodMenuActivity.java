package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodMenuActivity extends HttpActivity {

    private RecyclerView recyclerView;
    protected String loadMenu= "foodmenu.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Button logoutButton = findViewById(R.id.logoutmenu);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().clear().apply();
                Intent intent = new Intent(FoodMenuActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //creat param to recive in foodmenu
        Map<String, String> params = new HashMap<>();
        send(loadMenu,params);

    }

    @Override
    protected void responseReceived(String response, Map<String, String> params) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray itemsArray = jsonObject.getJSONArray("menu_list");
            List<Item> items = new ArrayList<>();
            for(int i=0; i<itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                String itemId = itemObject.getString("id");
                String itemName = itemObject.getString("name");
                String itemDescription = itemObject.getString("description");
                String itemImage = url + itemObject.getString("image");
                String itemPrice = itemObject.getString("price");
                items.add(new Item(itemName, itemDescription, itemImage, itemPrice));
            }
            ItemAdapter adapter = new ItemAdapter(items);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
