package com.example.exposysdatalabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInMainPage extends AppCompatActivity {

    TextView welcome;

    FirebaseUser firebaseUser;

    TextView uid;

    FirebaseAuth auth;
    public void gotoUrl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
    public void apply(View view){
        Intent intent = new Intent(this, InternshipApply.class);
        firebaseUser = auth.getCurrentUser();
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
    public void signout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        startActivity(intent);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_main_page);

        auth = FirebaseAuth.getInstance();

        uid = findViewById(R.id.uid);

        auth = FirebaseAuth.getInstance();

        firebaseUser = auth.getCurrentUser();

        welcome = findViewById(R.id.welcome);

        String userId = firebaseUser.getUid();

        Intent intent = getIntent();

        String displayName;

        if(firebaseUser == null){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            displayName="";
        }else{
            displayName = intent.getStringExtra("name");


        }
        welcome.setText("Welcome "+displayName);

    }


}