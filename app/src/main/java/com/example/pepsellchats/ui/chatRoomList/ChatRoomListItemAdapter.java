package com.example.pepsellchats.ui.chatRoomList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomListItemAdapter extends RecyclerView.Adapter<ChatRoomListItemAdapter.ViewHolder>  {

    Context context;
    List<ChatRoomListItem> items;
    private final LayoutInflater inflater;
    private final ChatRoomItemRecyclerViewInterface recViewInterface;

    public ChatRoomListItemAdapter(Context context, List<ChatRoomListItem> items, ChatRoomItemRecyclerViewInterface recViewInterface) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
        this.recViewInterface = recViewInterface;
    }

    public void setFilteredList(ArrayList<ChatRoomListItem> filteredList) {
        this.items = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_room_list_item, parent, false);
        return new ViewHolder(view, recViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(items.get(position).getName());
        holder.message.setText(items.get(position).getMessage());

        if(items.get(position).getPhone() == null)
            holder.phone.setVisibility(View.GONE);
        else
            holder.phone.setVisibility(View.VISIBLE);

        Picasso.get().load(items.get(position).getUrl()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, message;
        final View phone;
        final ImageView logo, chat;

        ViewHolder(View view, ChatRoomItemRecyclerViewInterface recViewInterface) {
            super(view);
            name = view.findViewById(R.id.crli_name);
            message = view.findViewById(R.id.crli_last_mes);

            phone = view.findViewById(R.id.crli_phone);
            logo = view.findViewById(R.id.chat_cli_logo);
            chat = view.findViewById(R.id.crli_open_chat);

            phone.setOnClickListener(view1 -> {
                if(recViewInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recViewInterface.onItemClick("phone", pos);
                    }
                }
            });

            chat.setOnClickListener(view12 -> {
                if(recViewInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        recViewInterface.onItemClick("chat", pos);
                    }
                }
            });
        }
    }
}
