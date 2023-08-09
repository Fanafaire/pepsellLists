package com.example.popselllists.ui.chatList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatListViewModel extends ViewModel {

    private final MutableLiveData<ArrayList> cardsItems;

    public ChatListViewModel() {
        cardsItems = new MutableLiveData<>();
        cardsItems.setValue(setInitialData());
    }

    public LiveData<ArrayList> getItems() {
        return cardsItems;
    }

    private ArrayList setInitialData(){
        ArrayList<ChatListItem> chatListItem = new ArrayList<ChatListItem>();

        chatListItem.add(new ChatListItem("Chat Name 1",  "Massage chat 1", "1023499933", null));
        chatListItem.add(new ChatListItem("Chat Name 2",  "Massage chat 2", null, null));
        chatListItem.add(new ChatListItem("Chat Name 3",  "Massage chat 3", null, null));
        chatListItem.add(new ChatListItem("Chat Name 1",  "Massage chat 1", "1023499933", null));
        chatListItem.add(new ChatListItem("Chat Name 2",  "Massage chat 2", null, null));
        chatListItem.add(new ChatListItem("Chat Name 3",  "Massage chat 3", null, null));
        chatListItem.add(new ChatListItem("Chat Name 1",  "Massage chat 1", "1023499933", null));
        chatListItem.add(new ChatListItem("Chat Name 2",  "Massage chat 2", null, null));
        chatListItem.add(new ChatListItem("Chat Name 3",  "Massage chat 3", null, null));
        chatListItem.add(new ChatListItem("Chat Name 1",  "Massage chat 1", "1023499933", null));
        chatListItem.add(new ChatListItem("Chat Name 2",  "Massage chat 2", null, null));
        chatListItem.add(new ChatListItem("Chat Name 3",  "Massage chat 3", null, null));
        chatListItem.add(new ChatListItem("Chat Name 1",  "Massage chat 1", "1023499933", null));
        chatListItem.add(new ChatListItem("Chat Name 2",  "Massage chat 2", null, null));
        chatListItem.add(new ChatListItem("Chat Name 3",  "Massage chat 3", null, null));
        chatListItem.add(new ChatListItem("Chat Name 1",  "Massage chat 1", "1023499933", null));
        chatListItem.add(new ChatListItem("Chat Name 2",  "Massage chat 2", null, null));
        chatListItem.add(new ChatListItem("Chat Name 3",  "Massage chat 3", null, null));

        return chatListItem;
    }
}