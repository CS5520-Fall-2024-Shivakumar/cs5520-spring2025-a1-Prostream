package com.example.numad24su_chunzhangliu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // About Me  AboutMeActivity
        Button aboutMeButton = findViewById(R.id.aboutMeButton);
        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
            }
        });

        Button btnQuicCalc = findViewById(R.id.btnQuicCalc);
        btnQuicCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动新建的计算器 Activity
                Intent intent = new Intent(MainActivity.this, QuicCalcActivity.class);
                startActivity(intent);
            }
        });

        // 新增 Contacts Collector 按钮：启动 ContactsActivity 显示联系人列表
        Button contactsCollectorButton = findViewById(R.id.contactsCollectorButton);
        contactsCollectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        // new button to PrimeActivity
        Button primeActivityButton = findViewById(R.id.btnOpenPrimeActivity);
        primeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrimeActivity.class);
                startActivity(intent);
            }
        });
    }
}
