package com.example.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity2 extends AppCompatActivity {

    CircleImageView mCircleImageViewBack;
    TextInputEditText mTexInputUsername;
    TextInputEditText mTexInputEmail;
    TextInputEditText mTexInputPassword;
    TextInputEditText mTexInputConfirmPassword;
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mCircleImageViewBack = findViewById(R.id.circleImageBack);
        mTexInputUsername = findViewById(R.id.textInputUsername);
        mTexInputEmail = findViewById(R.id.textInputEmail);
        mTexInputPassword = findViewById(R.id.textInputPassword);
        mTexInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        mButtonRegister = findViewById(R.id.btnRegister);


        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


        mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void register() {
        String username = mTexInputUsername.getText().toString();
        String email = mTexInputEmail.getText().toString();
        String password = mTexInputPassword.getText().toString();
        String confirmPassword = mTexInputConfirmPassword.getText().toString();
        
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if (isEmailValid(email)){
            }else {
                Toast.makeText(this, "El correo electronico no es valido", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}