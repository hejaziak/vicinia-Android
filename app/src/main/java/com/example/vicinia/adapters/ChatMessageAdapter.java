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

import com.example.vicinia.R;
import com.example.vicinia.fragments.ChatHistoryFragment;
import com.example.vicinia.pojos.ChatMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {
    private static final String TAG = "VICINIA/ChatAdapter";

    //list of messages
    private List<ChatMessage> chatMessages = new ArrayList<>();

    // components of the {@link layout#left_meesage}
    private TextView mMessageTextView;
    private RecyclerView mCardRecyclerView;

    public ChatMessageAdapter(Context context) {
        super(context, R.layout.right_message);
    }

    /**
     * add object to adapter
     *
     * @param object object to be added
     * @called_from: {@link ChatHistoryFragment#onSendMessage(String)}
     * {@link ChatHistoryFragment#sendTypingMessage(String)}
     * {@link ChatHistoryFragment#onReceiveMessage(String)}
     * @calls: none
     */
    @Override
    public void add(ChatMessage object) {
        chatMessages.add(object);
        super.add(object);
    }

    /**
     * get count of chat messages
     * @return number of messages in {@link #chatMessages}
     *
     *
     * @called_from: {@link ChatHistoryFragment#scrollDown()}
     * @calls: none
     */
    public int getCount() {
        return chatMessages.size();
    }


    /**
     * get number of message types
     * @return number of message types in {@link #chatMessages}
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    /**
     * get type of a particular message
     * @param position of message in question
     * @return type of message
     */
    @Override
    public int getItemViewType(int position) {
        return (chatMessages.get(position).isLeftAligned() ? 1 : -1);
    }

    /**
     * *binds* the data to a view
     * @param position    of message in question
     * @param convertView the resultant view to be displayed
     * @param parent      where the view will be displayed (list view)
     * @return the view
     *
     * @called_from: none
     * @calls: {@link #handleCardList(View, List, String)}
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = getItem(position);

        String inMessage = message.getContent();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (message.isLeftAligned()) {
            convertView = inflater.inflate(R.layout.left_meesage, parent, false);

            //the left message has a recycler view that we have to initialise
            mCardRecyclerView = convertView.findViewById(R.id.cards_recycler_view);

            //1. create a layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            //2. create a recycler adapter
            List<String[]> mCardList = new ArrayList<>();
            CardListAdapter adapter = new CardListAdapter(mCardList);

            //3. configure the recycler view with the layout manager and adapter
            mCardRecyclerView.setHasFixedSize(true);
            mCardRecyclerView.setLayoutManager(layoutManager);
            mCardRecyclerView.setAdapter(adapter);

            //3. make hyperlinks in the textView clickable
            mMessageTextView = convertView.findViewById(R.id.text);
            mMessageTextView.setMovementMethod(LinkMovementMethod.getInstance());

            //if content is a JSONArray
            if (inMessage.charAt(0) == '[')
                return handleCardList(convertView, mCardList, inMessage);
        } else {
            convertView = inflater.inflate(R.layout.right_message, parent, false);
            mMessageTextView = convertView.findViewById(R.id.text);
        }

        // fromHtml from API >= N is deprecated. For this to work do this to allow compatibility mod
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mMessageTextView.setText(Html.fromHtml(inMessage, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mMessageTextView.setText(Html.fromHtml(inMessage));
        }
        return convertView;
    }

    /**
     * converts the JSONArray into cards
     * @param convertView the resultant view to be displayed
     * @param mCardList   recycler's view card list
     * @param inMessage   the JSONArray
     * @return the view
     *
     * @called_from: {@link #getView(int, View, ViewGroup)}
     * @calls: none
     */
    private View handleCardList(View convertView, List<String[]> mCardList, String inMessage) {
        Log.v(TAG, "This is a JSON list");

        //load default reply message
        mMessageTextView.setText(R.string.chat_response);

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

            //notify adapter that we finished adding info
            mCardRecyclerView.getAdapter().notifyDataSetChanged();
        } catch (JSONException ignored) {
        }

        return convertView;
    }
}
