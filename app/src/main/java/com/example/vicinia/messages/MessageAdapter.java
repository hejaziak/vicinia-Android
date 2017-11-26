package com.example.vicinia.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vicinia.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatMessage> {
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private TextView messageTextView;

    public MessageAdapter(Context context) {
        super(context, R.layout.right_message);
    }

    @Override
    public void add(ChatMessage object) {
        chatMessages.add(object);
        super.add(object);
    }

    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (chatMessages.get(position).isLeftAligned() ? 1 : -1);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        
        if(!message.isLeftAligned())
            convertView = inflater.inflate(R.layout.left_meesage, parent, false);
        else
            convertView = inflater.inflate(R.layout.right_message, parent, false);

        messageTextView = convertView.findViewById(R.id.text);
        messageTextView.setText(message.getContent());

        return convertView;
    }
}
