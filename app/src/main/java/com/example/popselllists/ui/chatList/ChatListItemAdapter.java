package com.example.popselllists.ui.chatList;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popselllists.R;

import java.util.ArrayList;
import java.util.List;

public class ChatListItemAdapter extends RecyclerView.Adapter<ChatListItemAdapter.ViewHolder>  {

    Context context;
    List<ChatListItem> items;
    private final LayoutInflater inflater;

    public ChatListItemAdapter(Context context, List<ChatListItem> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    public void setFilteredList(ArrayList<ChatListItem> filteredList) {
        this.items = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.scrollView.setVisibility(View.VISIBLE);

        holder.name.setText(items.get(position).getName());
        holder.message.setText(items.get(position).getMessage());

        if(items.get(position).getPhone() == null)
            holder.phone.setVisibility(View.GONE);
        else
            holder.phone.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, message;
        final View phone;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.chat_list_item_name);
            message = view.findViewById(R.id.chat_list_item_last_mes);

            phone = view.findViewById(R.id.chat_list_item_phone);
        }
    }
}
