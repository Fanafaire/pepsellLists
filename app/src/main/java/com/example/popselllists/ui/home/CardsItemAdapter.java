package com.example.popselllists.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popselllists.R;

import java.util.List;

public class CardsItemAdapter extends RecyclerView.Adapter<CardsItemAdapter.ViewHolder>  {

    Context context;
    List<CardsItem> items;
    private final LayoutInflater inflater;

    public CardsItemAdapter(Context context, List<CardsItem> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cards_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.smallTextView.setText(items.get(position).getSmallText());
        holder.bigTextView.setText(items.get(position).getBigText());

        if(items.get(position).getTextType()){
            holder.scrollTextView.setText(items.get(position).getBigText());
            holder.smallTextView.setVisibility(View.GONE);
        }
        else{
            holder.scrollTextView.setText(items.get(position).getSmallText());
            holder.bigTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView smallTextView, bigTextView, scrollTextView;

        ViewHolder(View view) {
            super(view);
            smallTextView = view.findViewById(R.id.small_textView);
            bigTextView = view.findViewById(R.id.big_textView);
            scrollTextView = view.findViewById(R.id.scroll_textView);
        }
    }
}
