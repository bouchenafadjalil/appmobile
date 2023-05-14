package com.example.myapplication;

//./gradlew createDebugCoverageReport
//file:///C:/Users/lenovo/Desktop/AndroidTutorial-master/app/build/reports/androidTests/connected/index.html
//.\gradlew clean createDebugCoverageReport sonarqube
//.\gradlew clean sonarqube

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

public class HomeActivity extends HttpActivity {

    private TextView welcomeTextView;
   static SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=  PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_home);
        welcomeTextView = findViewById(R.id.welcome_textview);
        Button logoutButton = findViewById(R.id.logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().clear().apply();
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void responseReceived(String response, Map<String, String> params) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserData();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // loadFoodMenu();

        //   Toast.makeText(this, "onResume(), this method can be called several times", Toast.LENGTH_SHORT).show();
    }

    private void loadUserData() {

        String firstName = preferences.getString("first_name", "");
        String familyName = preferences.getString("family_name", "");
        welcomeTextView.setText("Hello " + firstName + " " + familyName);
    }

    private void loadFoodMenu() {
        // Start FoodMenuActivity
        Intent intent = new Intent(HomeActivity.this, FoodMenuActivity.class);
        startActivity(intent);
        finish();
    }


}