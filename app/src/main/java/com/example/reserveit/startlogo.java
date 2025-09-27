package com.example.reserveit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class startlogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startlogo);

        // Delay then go to login screen
        new Handler().postDelayed(() -> {
            startActivity(new Intent(startlogo.this, login.class));
            finish();
        }, 3000);
    }
}
