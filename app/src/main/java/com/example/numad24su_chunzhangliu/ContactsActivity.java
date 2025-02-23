
package com.example.numad24su_chunzhangliu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ContactsAdapter contactsAdapter;
    private List<Contact> contactList;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("contacts", (Serializable) contactList);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // make show information exists after rotate
        if (savedInstanceState != null) {
            contactList = (List<Contact>) savedInstanceState.getSerializable("contacts");
        } else {
            contactList = new ArrayList<>();
        }

        // 初始化 RecyclerView 并设置布局管理器和适配器
        recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ContactsAdapter(contactList, this, new ContactsAdapter.OnContactClickListener() {
            @Override
            public void onContactClick(Contact contact) {
                // 点击联系人时启动拨号界面（需确保电话号码格式正确）
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
                startActivity(dialIntent);
            }

            @Override
            public void onContactLongClick(Contact contact) {
                // 长按联系人，弹出对话框提示是否删除
                new AlertDialog.Builder(ContactsActivity.this)
                        .setTitle("Delete Contacts")
                        .setMessage("Do You Want To Delete Contacts " + contact.getName() + "?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int pos = contactList.indexOf(contact);
                                contactList.remove(contact);
                                contactsAdapter.notifyItemRemoved(pos);
                                // 使用 Snackbar 提示删除成功，并提供撤销操作
                                Snackbar.make(recyclerView, "Contacts deleted", Snackbar.LENGTH_LONG)
                                        .setAction("Cancel", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                contactList.add(pos, contact);
                                                contactsAdapter.notifyItemInserted(pos);
                                            }
                                        }).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        recyclerView.setAdapter(contactsAdapter);

        // 初始化 Floating Action Button，用于添加新联系人
        fab = findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 启动添加/编辑联系人 Activity，并等待返回数据
                Intent intent = new Intent(ContactsActivity.this, AddOrEditContactActivity.class);
                startActivityForResult(intent, 100); // 100 为请求码
            }
        });
    }

    // 处理添加新联系人返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            // 假设 AddOrEditContactActivity 将新联系人以 "newContact" 键传递回来
            Contact newContact = (Contact) data.getSerializableExtra("newContact");
            if (newContact != null) {
                contactList.add(newContact);
                contactsAdapter.notifyItemInserted(contactList.size() - 1);
                Snackbar.make(recyclerView, "Contacts add success", Snackbar.LENGTH_LONG)
                        .setAction("Cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int pos = contactList.indexOf(newContact);
                                contactList.remove(newContact);
                                contactsAdapter.notifyItemRemoved(pos);
                            }
                        }).show();
            }
        }
    }
}
