package com.example.exposysdatalabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InternshipApply extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference mDatabase;

    FirebaseUser firebaseUser;

    String[] domain = {"Software Development","Full Stack Developer","Mean Stack Developer","Web Development","Data Science","IOT","App Developer","Cyber Security"
            ,"HR","Process Associate","Content Writer","Digital Marketing","UI/UX Design","Business Development","Marketing","Tele Caller","Email Marketing","SMS Marketing","Photographer/Videographer"
            ,"Film Maker","Digital Content Creator","Social Media Promoter"};

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    EditText fullname;

    TextView test;
    EditText mobile;

    //Getting user details from database(apart from domain
    String Name;
    String mobileNo;
    String email;


    public void apply(View view){
        firebaseUser = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Registered Users");
        mDatabase.child(firebaseUser.getUid()).child("domain").setValue(autoCompleteTextView.getText().toString());
        Toast.makeText(this, "Applied", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LogInMainPage.class);
        //To not allow user to go back to Register activity on pressing back button
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish(); //Register activity closed



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship_apply);

        auth = FirebaseAuth.getInstance();

        test = findViewById(R.id.test);

        fullname = findViewById(R.id.name);
        mobile = findViewById(R.id.number);

        autoCompleteTextView = findViewById(R.id.domain);

        adapterItems = new ArrayAdapter<>(this,R.layout.dropdownlistitem,domain);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

            }
        });
        firebaseUser = auth.getCurrentUser();

    }
}