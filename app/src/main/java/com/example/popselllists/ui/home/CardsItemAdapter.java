package com.example.popselllists.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
//        holder.scrollView.setVisibility(View.VISIBLE);

        if(items.get(position).getTextType()){
            holder.scrollTextView.setText(items.get(position).getBigText());
            holder.bigTextView.setText(items.get(position).getBigText());
            holder.bigView.setVisibility(View.VISIBLE);
        }
        else{
            holder.scrollTextView.setText(items.get(position).getSmallText());
            holder.smallTextView.setText(items.get(position).getSmallText());
            holder.smallView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView smallTextView, bigTextView, scrollTextView;
        final CardView scrollView, bigView, smallView;

        ViewHolder(View view) {
            super(view);
            smallTextView = view.findViewById(R.id.small_textView);
            bigTextView = view.findViewById(R.id.big_textView);
            scrollTextView = view.findViewById(R.id.scroll_textView);

            scrollView = view.findViewById(R.id.scroll_item_card);
            bigView = view.findViewById(R.id.big_item_card);
            smallView = view.findViewById(R.id.small_item_card);
        }
    }
}
