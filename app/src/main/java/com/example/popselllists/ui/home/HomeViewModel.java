package com.example.popselllists.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<ArrayList> cardsItems;

    public HomeViewModel() {
        cardsItems = new MutableLiveData<>();
        cardsItems.setValue(setInitialData());
    }

    public LiveData<ArrayList> getItems() {
        return cardsItems;
    }

    private ArrayList setInitialData(){
        ArrayList<CardsItem> cardsItem = new ArrayList<CardsItem>();

        cardsItem.add(new CardsItem ("none",  "Small Test", false));
        cardsItem.add(new CardsItem ("Big 33 Text",  "None", true));
        cardsItem.add(new CardsItem ("none",  "Small Test4", false));
        cardsItem.add(new CardsItem ("Big 4333 Text",  "None", true));
        cardsItem.add(new CardsItem ("Big Text",  "None", true));
        cardsItem.add(new CardsItem ("none",  "Small Test", false));
        cardsItem.add(new CardsItem ("none",  "Small Test 2", false));
        cardsItem.add(new CardsItem ("none",  "Small Test 3", false));
        cardsItem.add(new CardsItem ("Big Text",  "None", true));
        cardsItem.add(new CardsItem ("Big Text",  "None", true));
        cardsItem.add(new CardsItem ("Big Text",  "None", true));

        return cardsItem;
    }
}