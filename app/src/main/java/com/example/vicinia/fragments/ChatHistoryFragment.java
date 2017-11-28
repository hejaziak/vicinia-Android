package com.example.vicinia.fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vicinia.R;
import com.example.vicinia.adapters.MessageAdapter;
import com.example.vicinia.pojos.ChatMessage;

public class ChatHistoryFragment extends Fragment {

    ListView mChatHistory;
    private static MessageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_history, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mChatHistory = view.findViewById(R.id.chat_history);

        adapter = new MessageAdapter(getActivity());
        mChatHistory.setAdapter(adapter);
    }

    public void onSendMessage(String message){
        ChatMessage newMessage = new ChatMessage(message, false);
        adapter.add(newMessage);

        scrollDown();
    }

    public void onReceiveMessage(String message){
        ChatMessage newMessage = new ChatMessage(message, true);
        adapter.add(newMessage);

        scrollDown();
    }


    private void scrollDown() {
        mChatHistory.setSelection(adapter.getCount() - 1);
    }
}