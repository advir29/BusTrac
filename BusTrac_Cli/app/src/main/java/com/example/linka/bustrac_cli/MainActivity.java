package com.example.linka.bustrac_cli;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    String value = null;
    private  static  final int REQUEST_CODE_PERMISSION =2;
    GPSTracker gps;
    double dLat;
    double dLon;
    double latitude;
    double longitude;
    double rLat1;
    double rLat2;
    double rLon1;
    double rLon2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = new GPSTracker(MainActivity.this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Location");
        gps = new GPSTracker(MainActivity.this);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                TextView textView = findViewById(R.id.loc);
                textView.setText(value);
                String [] sep = value.split(",");
                String lat = sep[0].trim();
                String lon = sep[1].trim();
                dLat = Double.parseDouble(lat);
                dLon = Double.parseDouble(lon);
                rLat1 = dLat + 0.1;
                rLat2 = dLat - 0.1;
                rLon1 = dLon + 0.1;
                rLon2 = dLon - 0.1;
                if(((latitude>=rLat1)||(latitude<=rLat2))&&((longitude>=rLon1)||(longitude<=rLon2))){
                        textView.setText("Bus is arriving. Get ready to board.");
                    }
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
