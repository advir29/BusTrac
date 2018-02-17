package com.example.linka.bustrac_cli;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    String value = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Location");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                TextView textView = findViewById(R.id.loc);
                textView.setText(value);
                String [] sep = value.split(",");
                String lat = sep[0].trim();
                String lon = sep[1].trim();
                double dLat = Double.parseDouble(lat);
                double dLon = Double.parseDouble(lon);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void buttonClicked(View view){
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("LOC",value);
        startActivity(intent);
    }
}
