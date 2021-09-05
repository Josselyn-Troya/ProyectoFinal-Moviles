package com.example.socialmedia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.socialmedia.R;
import com.example.socialmedia.models.User;
import com.example.socialmedia.providers.AuthProvider;
import com.example.socialmedia.providers.UsersProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
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

    AuthProvider mAuthProvider;
    UsersProvider mUsersProvider;


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

        mAuthProvider = new AuthProvider();
        mUsersProvider = new UsersProvider();


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
                if (password.equals(confirmPassword)){
                    if (password.length()>=6){
                        createUser(username, email, password);
                    }else{
                        Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "El correo electronico no es valido", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(final String username, final String email, String password){
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = mAuthProvider.getUid();

                    User user = new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setUsername(username);

                    mUsersProvider.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                Intent intent = new Intent(RegisterActivity2.this, HomeActivity.class);
                                startActivity(intent);

                                Toast.makeText(RegisterActivity2.this, "El usuario se registro corectamente", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterActivity2.this, "No se pudo almacenar al usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(RegisterActivity2.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}