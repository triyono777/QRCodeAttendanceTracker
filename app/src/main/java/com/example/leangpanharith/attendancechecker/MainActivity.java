package com.example.leangpanharith.attendancechecker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    public void record_attendance (View view) {
        Intent intent = new Intent(this, QRScanner.class);
        startActivity(intent);
    }

    public void view_attendance (View view) {
        Intent intent = new Intent(this, StudentList.class);
        startActivity(intent);
    }

    public void manage_classroom (View view) {
        Intent intent = new Intent(this, ManageClassRoom.class);
        startActivity(intent);

    }
}
