package com.example.numad24su_chunzhangliu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private List<Contact> contacts;
    private Context context;
    // 定义接口处理点击事件（可选）
    private OnContactClickListener listener;

    // 接口回调，用于处理点击事件
    public interface OnContactClickListener {
        void onContactClick(Contact contact);
        void onContactLongClick(Contact contact);
    }

    public ContactsAdapter(List<Contact> contacts, Context context, OnContactClickListener listener) {
        this.contacts = contacts;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载每个列表项的布局（item_contact.xml）
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        // 绑定数据
        Contact contact = contacts.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhoneNumber());

        // 设置点击事件：点击列表项启动电话拨打（或进行编辑操作）
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onContactClick(contact);
            }
        });

        // 设置长按事件：用于删除或其他操作
        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onContactLongClick(contact);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    // ViewHolder 内部类，持有每个列表项中的控件引用
    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPhone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }
}

