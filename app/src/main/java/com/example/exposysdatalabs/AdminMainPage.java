package com.example.exposysdatalabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AdminMainPage extends AppCompatActivity {

    public void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void applicants(View view){
        Intent intent = new Intent(this, AdminApplicants.class);
        startActivity(intent);
    }
    public void about(View view){
        gotoUrl("http://exposysdata.com/about-us.html");
    }
    public void contact(View view){
        gotoUrl("http://exposysdata.in/contact.php");
    }
    public void domain(View view){
        Intent intent = new Intent(this,Domain.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
    }
}