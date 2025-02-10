package com.example.numad24su_chunzhangliu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutMeButton = findViewById(R.id.aboutMeButton);
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //name + email
                Toast.makeText(MainActivity.this, "Chunzhang Liu - liu.chunz@northeastern.edu",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
