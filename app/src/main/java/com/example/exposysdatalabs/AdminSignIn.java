package com.example.exposysdatalabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminSignIn extends AppCompatActivity {
    //To get admin mail and password from the EditTextView
    EditText adminEmail;
    EditText adminPassword;

    //Already set admin mail and password
    String mail;
    String password;

    public void signIn(View view){
        mail = "adminExposys@gmail.com";
        password = "specialPrivilage";
        if(!(adminEmail.getText().toString().equals(mail))){
            adminEmail.setError("Email user is not admin");
            Toast.makeText(this, "Email user in not admin", Toast.LENGTH_SHORT).show();
        }else if(!(adminPassword.getText().toString().equals(password))){
            adminPassword.setError("Incorrect password");
            Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, AdminMainPage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);

        mail = "adminExposys@gmail.com";
        password = "specialPrivilage";

        adminEmail = findViewById(R.id.emailAdmin);
        adminPassword = findViewById(R.id.passwordAdmin);

    }
}