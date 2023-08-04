package com.example.popselllists.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popselllists.R;
import com.example.popselllists.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set RecyclerView
        setRecyclerView(homeViewModel, root);

        return root;
    }

    private void setRecyclerView(HomeViewModel homeViewModel, View root) {
        // Start list initiating
        ArrayList<CardsItem> cardsItem = homeViewModel.getItems().getValue();
        RecyclerView recyclerView = root.findViewById(R.id.cards_recycler_view);
        // Create adapter
        CardsItemAdapter cardsAdapter = new CardsItemAdapter(getContext(), cardsItem);
        // Set adapter for list
        recyclerView.setAdapter(cardsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}