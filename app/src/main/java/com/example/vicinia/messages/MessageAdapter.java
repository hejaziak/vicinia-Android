package com.example.vicinia.messages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vicinia.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatMessage> {
    private List<ChatMessage> chatMessages = new ArrayList<>();

    private TextView messageTextView;
    private LinearLayout wrapper;

    public MessageAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    @Override
    public void add(ChatMessage object) {
        this.chatMessages.add(object);
        super.add(object);
    }

    public int getCount() {
        return this.chatMessages.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatMessage message = getItem(position);

        int type = getItemViewType(position);

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//            if(type==LEFT_MESSAGE){
                row = inflater.inflate(R.layout.message, parent, false);
//            }
//            if(type==RIGHT_MESSAGE){
//                row = inflater.inflate(R.layout.chat_listitem_right, parent, false);
//            }
        }

        wrapper = (LinearLayout) row.findViewById(R.id.chat_history);

        messageTextView = (TextView) row.findViewById(R.id.text);
        messageTextView.setText(message.getContent());

        return row;
    }
}
