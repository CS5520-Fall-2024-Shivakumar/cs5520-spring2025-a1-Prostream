package com.example.numad24su_chunzhangliu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddOrEditContactActivity extends AppCompatActivity {

    private EditText etName, etPhone;
    private Button btnSave;
    private Contact contact; // 用于判断是否是编辑模式

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);

        // 获取传递过来的联系人（如果是编辑模式）
        if (getIntent().hasExtra("contact")) {
            contact = (Contact) getIntent().getSerializableExtra("contact");
            etName.setText(contact.getName());
            etPhone.setText(contact.getPhoneNumber());
        }

        // 保存按钮点击事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(AddOrEditContactActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 如果是编辑模式，则更新联系人
                if (contact != null) {
                    contact.setName(name);
                    contact.setPhoneNumber(phone);
                } else {
                    contact = new Contact(name, phone);
                }

                // 通过 Intent 返回数据
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newContact", contact);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}

