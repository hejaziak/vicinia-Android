package com.example.vicinia;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.vicinia.fragments.ChatHistoryFragment;
import com.example.vicinia.fragments.ChatMessageFragment;
import com.example.vicinia.fragments.QuickActionFragment;
import com.example.vicinia.services.GpsServices;
import com.example.vicinia.utilities.DialogUtilities;

import static com.example.vicinia.utilities.DialogUtilities.gpsErrorBuilder;
import static com.example.vicinia.utilities.DialogUtilities.helpDialogBuilder;
import static com.example.vicinia.utilities.DialogUtilities.internetErrorDialogBuider;

public class MainActivity extends AppCompatActivity {
    static MainActivity instance;
    FragmentManager fragmentManager;

    public String uuid;

    public GpsServices gpsServices;

    private ChatMessageFragment fChatMessage;
    private ChatHistoryFragment fChatHistory;
    private QuickActionFragment fQuickAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        fragmentManager = getSupportFragmentManager();
        gpsServices = new GpsServices(this);

        fChatMessage = (ChatMessageFragment) fragmentManager.findFragmentById(R.id.chat_message_fragment);
        fChatHistory = (ChatHistoryFragment) fragmentManager.findFragmentById(R.id.chat_history_fragment);
        fQuickAction = (QuickActionFragment) fragmentManager.findFragmentById(R.id.quick_actions_fragment);

        //hide keyboard at startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gpsServices.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gpsServices.onStop();
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
                helpDialogBuilder(this);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSendMessage(String message) {
        fChatHistory.onSendMessage(message);
        fChatHistory.sendTypingMessage();
    }

    public void onReceiveMessage(String message) { fChatHistory.onReceiveMessage(message); }

    public void onChatButton(View v) { fChatMessage.onChatButton(v);  }

    public void onCinemaButton(View v) {
        fQuickAction.onCinemaButton();
        fChatHistory.sendTypingMessage("Getting nearby cinemas...");
    }

    public void onGasStationButton(View v) {
        fQuickAction.onGasStationButton();
        fChatHistory.sendTypingMessage("Getting nearby gas stations...");
    }

    public void onHospitalButton(View v) {
        fQuickAction.onHospitalButton();
        fChatHistory.sendTypingMessage("Getting nearby hospitals...");
    }

    public void onRestaurantButton(View v) {
        fQuickAction.onRestaurantButton();
        fChatHistory.sendTypingMessage("Getting nearby restaurants...");
    }

    public static MainActivity getInstance() { return instance; }

    public void onInternetError() {
        internetErrorDialogBuider(this);
    }

    public void onGpsError() {
        DialogUtilities.gpsErrorBuilder(this);
    }
}
