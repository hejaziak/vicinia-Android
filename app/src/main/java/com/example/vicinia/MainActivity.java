package com.example.vicinia;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.vicinia.fragments.ChatHistoryFragment;
import com.example.vicinia.fragments.ChatMessageFragment;
import com.example.vicinia.fragments.QuickActionFragment;
import com.example.vicinia.services.GPSTracker;

public class MainActivity extends AppCompatActivity {
    static MainActivity instance;
    FragmentManager fragmentManager;

    public String uuid;

    public GPSTracker gps;

    private ChatMessageFragment fChatMessage;
    private ChatHistoryFragment fChatHistory;
    private QuickActionFragment fQuickAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        fragmentManager = getSupportFragmentManager();
        gps = new GPSTracker(this);

        fChatMessage = (ChatMessageFragment) fragmentManager.findFragmentById(R.id.chat_message_fragment);
        fChatHistory = (ChatHistoryFragment) fragmentManager.findFragmentById(R.id.chat_history_fragment);
        fQuickAction = (QuickActionFragment) fragmentManager.findFragmentById(R.id.quick_actions_fragment);

        //hide keyboard at startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        switch (itemThatWasClickedId){
            case R.id.btn_help:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSendMessage(String message) {
        fChatHistory.onSendMessage(message);
    }

    public void onReceiveMessage(String message) { fChatHistory.onReceiveMessage(message); }

    public void onChatButton(View v) { fChatMessage.onChatButton(v);  }

    public void onCinemaButton(View v) { fQuickAction.onCinemaButton(); }

    public void onGasStationButton(View v) { fQuickAction.onGasStationButton(); }

    public void onHospitalButton(View v) { fQuickAction.onHospitalButton(); }

    public void onRestaurantButton(View v) { fQuickAction.onRestaurantButton(); }

    public static MainActivity getInstance() { return instance; }
}
