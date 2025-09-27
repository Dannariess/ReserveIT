package com.example.reserveit;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

public class Signup extends AppCompatActivity {

    private TextView login;
    private Button signup;
    private EditText username, password, confirmpass;
    private boolean isPasswordVisible = false;
    private boolean isConfirmVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setupUIComponents();
        clickListeners();
        passwordConfirmation();
        passwordEyeToggle();
        confirmEyeToggle();
    }

    private void setupUIComponents() {
        login       = findViewById(R.id.backtologin);
        username    = findViewById(R.id.user);
        password    = findViewById(R.id.password);
        confirmpass = findViewById(R.id.confirmpass);
        signup      = findViewById(R.id.signup);

        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void clickListeners() {
        login.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, login.class);
            startActivity(intent);
        });
    }

    private void passwordConfirmation() {
        signup.setOnClickListener(v -> {
            String user    = username.getText().toString().trim();
            String pass    = password.getText().toString().trim();
            String confirm = confirmpass.getText().toString().trim();

            if (pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences prefs = getSharedPreferences("SavedInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", user);
                editor.putString("password", pass);
                editor.apply();

                Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void passwordEyeToggle() {
        password.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (password.getRight()
                        - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    isPasswordVisible = !isPasswordVisible;

                    if (isPasswordVisible) {
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        setEndDrawable(password, R.drawable.eye_openresize);
                    } else {
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        setEndDrawable(password, R.drawable.eye_closeresize);
                    }
                    password.setSelection(password.getText().length());
                    return true;
                }
            }
            return false;
        });
    }

    private void confirmEyeToggle() {
        confirmpass.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (confirmpass.getRight()
                        - confirmpass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                    isConfirmVisible = !isConfirmVisible;

                    if (isConfirmVisible) {
                        confirmpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        setEndDrawable(confirmpass, R.drawable.eye_openresize);
                    } else {
                        confirmpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        setEndDrawable(confirmpass, R.drawable.eye_closeresize);
                    }
                    confirmpass.setSelection(confirmpass.getText().length());
                    return true;
                }
            }
            return false;
        });
    }
    private void setEndDrawable(EditText field, int drawableResId) {
        Drawable newDrawable = ContextCompat.getDrawable(this, drawableResId);
        Drawable startIcon   = field.getCompoundDrawables()[0];

        field.setCompoundDrawablesWithIntrinsicBounds(
                startIcon, null, newDrawable, null
        );
    }
}
