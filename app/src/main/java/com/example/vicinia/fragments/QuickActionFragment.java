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

public class QuickActionFragment extends Fragment{
    public enum QUICK_ACTIONS {CINEMA, RESTAURANT, GAS_STATION, HOSPITAL}

    MainActivity parent;
    GPSTracker gps;

    ImageButton mCinemaButton;
    ImageButton mGasStationButton;
    ImageButton mHospitalButton;
    ImageButton mRestaurantButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        this.parent =  (MainActivity) getActivity();
        this.gps = this.parent.gps;

        return inflater.inflate(R.layout.fragment_quick_actions, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mCinemaButton = view.findViewById(R.id.btn_cinema);
        mGasStationButton = view.findViewById(R.id.btn_gas_station);
        mHospitalButton = view.findViewById(R.id.btn_hospital);
        mRestaurantButton = view.findViewById(R.id.btn_restaurant);
    }

    public void onCinemaButton(){ onQuickActionButton(QUICK_ACTIONS.CINEMA); }
    public void onGasStationButton(){ onQuickActionButton(QUICK_ACTIONS.GAS_STATION); }
    public void onHospitalButton(){ onQuickActionButton(QUICK_ACTIONS.HOSPITAL); }
    public void onRestaurantButton(){ onQuickActionButton(QUICK_ACTIONS.RESTAURANT); }

    public void onQuickActionButton(QUICK_ACTIONS action){
        String message = "";
        double lat = 0;
        double lng = 0;

        switch (action){
            case CINEMA:
                message = "cinema";
                break;
            case GAS_STATION:
                message = "gas station";
                break;
            case HOSPITAL:
                message = "hospital";
                break;
            case RESTAURANT:
                message = "restaurant";
                break;
        }

        if(gps != null && gps.canGetLocation()){
            lat = gps.getLatitude();
            lng = gps.getLongitude();
        }

        sendChatMessage(message, lat, lng);
    }
}