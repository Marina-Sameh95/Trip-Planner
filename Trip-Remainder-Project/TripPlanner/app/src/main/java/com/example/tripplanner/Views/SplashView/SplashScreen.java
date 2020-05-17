package com.example.tripplanner.Views.SplashView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripplanner.Views.HomeView.MainActivity;
import com.example.tripplanner.Views.Login.Login;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        database.setPersistenceEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("MyLogin.txt", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
        Boolean loginCheck = sharedPreferences.getBoolean("FirstLogin", false);
//        editor.commit();
        String user = sharedPreferences.getString("user", "");
        if (loginCheck){

            Intent  intent = new Intent(getApplicationContext(), MainActivity.class);   // add user
            intent.putExtra("user",user);
            intent.putExtra("firstLogin",false);            //newwwwwwwwwwww remeber to do this once [logout]
            startActivity(intent);
        }else{
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("user",user);
        startActivity(intent);
        }
        finish();
    }
}
