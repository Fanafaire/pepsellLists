package com.example.pepsellchats.ui.chatList;

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

import java.util.List;

public class ChatListItemAdapter extends RecyclerView.Adapter<ChatListItemAdapter.ViewHolder>  {
    Context context;
    List<ChatListItem> items;
    private final LayoutInflater inflater;
    private final ChatItemRecyclerViewInterface recViewInterface;

    public ChatListItemAdapter(Context context, List<ChatListItem> items,
                                   ChatItemRecyclerViewInterface recViewInterface) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
        this.recViewInterface = recViewInterface;
    }

    @NonNull
    @Override
    public ChatListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        return new ChatListItemAdapter.ViewHolder(view, recViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListItemAdapter.ViewHolder holder, int position) {
        holder.userName.setText(items.get(position).getUserName());
        holder.message.setText(items.get(position).getChatDescription());
        holder.time.setText(items.get(position).getChatLastMesDate());

        Picasso.get().load(items.get(position).getUserLogoURI()).into(holder.logo);

        String uri = items.get(position).getChatMediaURI();
        if(uri != null){
            holder.media.setVisibility(View.VISIBLE);
            Picasso.get().load(items.get(position).getChatMediaURI()).into(holder.media);
        } else {
            holder.media.setVisibility(View.GONE);
//            holder.message.setLayoutParams();
        }
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView userName, message, time;
        final ImageView logo, media, chat, share;

        ViewHolder(View view, ChatItemRecyclerViewInterface chatItemRecyclerViewInterface) {
            super(view);
            userName = view.findViewById(R.id.cli_name);
            logo = view.findViewById(R.id.cli_logo);

            message = view.findViewById(R.id.cli_card_chat_name);
            time = view.findViewById(R.id.cli_card_time);
            media = view.findViewById(R.id.cli_card_image);

            chat = view.findViewById(R.id.cli_card_chat);
            share = view.findViewById(R.id.cli_card_share);

            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(chatItemRecyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            chatItemRecyclerViewInterface.onItemClick("chat", pos);
                        }
                    }
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(chatItemRecyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            chatItemRecyclerViewInterface.onItemClick("share", pos);
                        }
                    }
                }
            });
        }
    }
}
