package com.example.exposysdatalabs;

import static java.util.Arrays.asList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Domain extends AppCompatActivity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domain);

        list = findViewById(R.id.list);
        final ArrayList<String> al = new ArrayList<>(asList("Software Development","Full Stack Developer","Mean Stack Developer","Web Development","Data Science","IOT","App Developer","Cyber Security"
        ,"HR","Process Associate","Content Writer","Digital Marketing","UI/UX Design","Business Development","Marketing","Tele Caller","Email Marketing","SMS Marketing","Photographer/Videographer"
        ,"Film Maker","Digital Content Creator","Social Media Promoter"));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,al);
        list.setAdapter(adapter);


    }
}