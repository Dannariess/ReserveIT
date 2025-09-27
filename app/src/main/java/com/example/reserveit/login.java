package com.example.reserveit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class login extends AppCompatActivity {
    private EditText username, password;
    private Button admin, org;
    private TextView createAccount;
    private SharedPreferences sharedPreferences;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUIComponents();
        clickListener();
        eyeToggleListener();
    }

    private void setupUIComponents() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        admin = findViewById(R.id.admin);
        org = findViewById(R.id.org);
        createAccount = findViewById(R.id.createaccount);
        sharedPreferences = getSharedPreferences("SavedInfo", MODE_PRIVATE);

        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void clickListener() {
        createAccount.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, Signup.class);
            startActivity(intent);
        });

        org.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            String savedUser = sharedPreferences.getString("username", "");
            String savedPass = sharedPreferences.getString("password", "");

            if (user.isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (pass.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            } else if (user.equals(savedUser) && pass.equals(savedPass)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                // Add your navigation code here
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        admin.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String adminpass = "admin123";
            String adminuser = "admin";

            if (user.isEmpty()){
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (pass.isEmpty()){
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            } else if (user.equals(adminuser) && pass.equals(adminpass)){
                Toast.makeText(this, "Log in successfully", Toast.LENGTH_SHORT).show();
            }else if(!user.equals(adminuser)){
                Toast.makeText(this, "Incorrect username", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(adminpass)) {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }else if (!user.equals(adminuser) && !pass.equals(adminpass)){
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void eyeToggleListener() {
        password.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    isPasswordVisible = !isPasswordVisible;

                    if (isPasswordVisible) {
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        setEndDrawable(R.drawable.eye_openresize);
                    } else {
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        setEndDrawable(R.drawable.eye_closeresize);
                    }
                    password.setSelection(password.getText().length());

                    return true;
                }
            }
            return false;
        });
    }
    private void setEndDrawable(int drawableResId) {
        Drawable newDrawable = ContextCompat.getDrawable(this, drawableResId);
        Drawable lockIcon = password.getCompoundDrawables()[0];

        password.setCompoundDrawablesWithIntrinsicBounds(
                lockIcon,
                null,
                newDrawable,
                null
        );
    }
}