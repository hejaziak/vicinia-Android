package com.example.vicinia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.example.vicinia.MainActivity;
import com.example.vicinia.R;
import com.example.vicinia.adapters.ChatMessageAdapter;
import com.example.vicinia.models.ChatMessage;

public class ChatHistoryFragment extends Fragment {
    private ChatMessage lastMessage = null;

    MainActivity parent;
    private ChatMessageAdapter adapter;
    private ListView mChatHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_history, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mChatHistory = view.findViewById(R.id.chat_history);
        parent = (MainActivity) getActivity();

        //configure list view
        adapter = new ChatMessageAdapter(parent);
        mChatHistory.setAdapter(adapter);

        // Hiding keyboard upon touching the screen
        mChatHistory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) parent.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(parent.getChatMessageFragment().getmChatMessage().getWindowToken(), 0);

                return false;
            }
        });
    }

    public void onSendMessage(String message) {
        ChatMessage newMessage = new ChatMessage(message, false);
        adapter.add(newMessage);

        scrollDown();
    }

    public void sendTypingMessage() {
        sendTypingMessage("typing...");
    }

    public void sendTypingMessage(String initialMessage) {
        initialMessage = "<i>" + initialMessage + "</i>";
        
        if (lastMessage != null) {
            lastMessage.setContent(initialMessage);

            adapter.notifyDataSetChanged();
        } else {
            lastMessage = new ChatMessage(initialMessage, true);
            adapter.add(lastMessage);
        }

        scrollDown();
    }

    public void onReceiveMessage(String message) {
        if (lastMessage != null) {
            lastMessage.setContent(message);
            lastMessage = null;

            adapter.notifyDataSetChanged();
        } else {
            ChatMessage newMessage = new ChatMessage(message, true);
            adapter.add(newMessage);
        }

        scrollDown();
    }

    private void scrollDown() {
        mChatHistory.setSelection(adapter.getCount() - 1);
    }
}