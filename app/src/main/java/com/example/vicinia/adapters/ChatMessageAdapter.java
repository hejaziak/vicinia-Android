package com.example.vicinia.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vicinia.MainActivity;
import com.example.vicinia.R;
import com.example.vicinia.pojos.ChatMessage;
import com.example.vicinia.services.GpsServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {
    private static final String TAG = "MessageAdapter";

    private List<ChatMessage> chatMessages = new ArrayList<>();
    private TextView mMessageTextView;
    private RecyclerView mCardRecyclerView;

    public ChatMessageAdapter(Context context) {
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

        String inMessage = message.getContent();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (message.isLeftAligned()) {
            convertView = inflater.inflate(R.layout.left_meesage, parent, false);
            mCardRecyclerView = convertView.findViewById(R.id.cards_recycler_view);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            List<String[]> mCardList = new ArrayList<>();
            CardListAdapter adapter =  new CardListAdapter(mCardList);

            mCardRecyclerView.setHasFixedSize(true);
            mCardRecyclerView.setLayoutManager(layoutManager);
            mCardRecyclerView.setAdapter(adapter);

            mMessageTextView = convertView.findViewById(R.id.text);
            mMessageTextView.setMovementMethod(LinkMovementMethod.getInstance());

            if (inMessage.charAt(0) == '[')
                return handleCardList(convertView, mCardList, inMessage);
        }
        else
            convertView = inflater.inflate(R.layout.right_message, parent, false);

        mMessageTextView = convertView.findViewById(R.id.text);
        mMessageTextView.setText(Html.fromHtml(inMessage));
        return convertView;
    }

    private View handleCardList(View convertView, List<String[]> mCardList, String inMessage) {
        Log.v(TAG, "This is a JSON list");

        RecyclerView.Adapter adapter = mCardRecyclerView.getAdapter();

        try {
            JSONArray jsonarray = new JSONArray(inMessage);

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String name = jsonobject.getString("name");
                String distance = jsonobject.getString("distance");
                String rating = jsonobject.getString("rating");
                String id = jsonobject.getString("id");

                String[] placeDetails = {name, distance, rating, id};
                mCardList.add(placeDetails);
            }
            adapter.notifyDataSetChanged();
        }
        catch (JSONException e) {

        }

        return convertView;
    }
}
