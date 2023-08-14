package com.example.pepsellchats.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepsellchats.R;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {
    Context context;
    List<ChatItem> items;
    private final LayoutInflater inflater;

    public MessageAdapter(Context context, List<ChatItem> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_item, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        long accUserId = items.get(position).getAccUserId();
        long userId = items.get(position).getUserId();

        if(accUserId == userId){
            holder.userCard.setVisibility(View.GONE);

            // Change bias to show card on left
            ConstraintSet cs = new ConstraintSet();
            cs.clone(holder.layout);
            cs.setHorizontalBias(holder.mCard.getId(), 100);
            holder.layout.setConstraintSet(cs);
        } else {
            holder.userCard.setVisibility(View.VISIBLE);
            holder.userName.setText(items.get(position).getUserName());
            Picasso.get().load(items.get(position).getUserLogo()).into(holder.userLogo);

            // Change bias to show card on right
            ConstraintSet cs = new ConstraintSet();
            cs.clone(holder.layout);
            cs.setHorizontalBias(holder.mCard.getId(), 0);
            holder.layout.setConstraintSet(cs);
        }

        Timestamp ts = new Timestamp(items.get(position).getTime());
        Date date = new Date(ts.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm", Locale.getDefault());
        String strDate = formatter.format(date);
        holder.mTime.setText(strDate);

        if(items.get(position).getmText() != null){
            holder.mImage.setVisibility(View.GONE);
            holder.mText.setVisibility(View.VISIBLE);
            holder.mText.setText(items.get(position).getmText());
        } else {
            holder.mText.setVisibility(View.GONE);
            holder.mImage.setVisibility(View.VISIBLE);
            Picasso.get().load(items.get(position).getmImage()).into(holder.mImage);
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
        final TextView userName, mText, mTime;
        final ImageView userLogo, mImage;
        final CardView userCard, mCard;
        ConstraintLayout layout;

        ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.chat_recycler_layout);

            userName = view.findViewById(R.id.cruc_name);
            userLogo = view.findViewById(R.id.cruc_logo);
            userCard = view.findViewById(R.id.cr_user_card);

            mText = view.findViewById(R.id.crmc_message);
            mImage = view.findViewById(R.id.crmc_image);
            mTime = view.findViewById(R.id.crmc_time);
            mCard = view.findViewById(R.id.cr_message_card);
        }
    }
}
