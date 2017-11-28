package com.example.vicinia.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.vicinia.MainActivity;
import com.example.vicinia.R;
import com.example.vicinia.services.GPSTracker;
import com.example.vicinia.utilities.UrlUtilities;

import static com.example.vicinia.services.ChatMessageServices.sendChatMessage;

public class ChatMessageFragment extends Fragment {
    MainActivity parent;
    GPSTracker gps;

    EditText mChatMessage;
    ImageButton mChatButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        this.parent =  (MainActivity) getActivity();
        this.gps = this.parent.gps;

        return inflater.inflate(R.layout.fragment_chat_message, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mChatButton = view.findViewById(R.id.btn_send);
        mChatMessage = view.findViewById(R.id.et_message);
    }

    /**
     * This method constructs the URL (using {@link UrlUtilities}) for the chat url ,
     * and fires off an AsyncTask to perform the POST request using {@link com.example.vicinia.client.ChatMessageClient.ApiPostTask}
     */
    public void onChatButton(View v){
        String message = mChatMessage.getText().toString();
        if(message.length() < 2)
            return;
        
        mChatMessage.setText("");

        parent.onSendMessage(message);

        double lat = 0;
        double lng = 0;

        if(gps != null && gps.canGetLocation()){
            lat = gps.getLatitude();
            lng = gps.getLongitude();
        }

        sendChatMessage(message, lat, lng);
    }
}