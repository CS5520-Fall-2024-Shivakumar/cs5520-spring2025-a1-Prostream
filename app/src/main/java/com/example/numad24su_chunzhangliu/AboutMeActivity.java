package com.example.numad24su_chunzhangliu;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // 假设在布局 activity_about_me.xml 中有一个 TextView，其 id 为 tvAboutMe
        TextView tvAboutMe = findViewById(R.id.tvAboutMe);
        // 显示姓名和邮箱信息
        tvAboutMe.setText("Chunzhang Liu\nliu.chunz@northeastern.edu");
    }
}
