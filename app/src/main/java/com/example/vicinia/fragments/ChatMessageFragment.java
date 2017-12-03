package com.example.vicinia.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.vicinia.MainActivity;
import com.example.vicinia.R;
import com.example.vicinia.services.GpsServices;
import com.example.vicinia.utilities.UrlUtilities;

import static com.example.vicinia.services.ChatMessageServices.sendChatMessage;

public class ChatMessageFragment extends Fragment {
    MainActivity parent;
    GpsServices gps;

    EditText mChatMessage;
    ImageButton mChatButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        this.parent = (MainActivity) getActivity();

        return inflater.inflate(R.layout.fragment_chat_message, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mChatButton = view.findViewById(R.id.btn_send);
        mChatMessage = view.findViewById(R.id.et_message);
    }

    @Override
    public void onResume() {
        this.gps = this.parent.gpsServices;
        super.onResume();
    }

    /**
     * This method constructs the URL (using {@link UrlUtilities}) for the chat url ,
     * and fires off an AsyncTask to perform the POST request using {@link com.example.vicinia.clients.ChatMessageClient.ApiPostTask}
     */
    public void onChatButton(View v) {
        String message = mChatMessage.getText().toString();
        if (message.length() < 1)
            return;

        mChatMessage.setText("");

        // for the UI
        parent.onSendMessage(message);

        double lat = gps.getLatitude();
        double lng = gps.getLongitude();

        // For the api

        Location location = gps.getLastLocation();
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
        }

        sendChatMessage(message, lat, lng);
    }
}