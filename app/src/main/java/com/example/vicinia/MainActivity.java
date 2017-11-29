package com.example.vicinia;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vicinia.fragments.ChatHistoryFragment;
import com.example.vicinia.fragments.ChatMessageFragment;
import com.example.vicinia.services.GPSTracker;

import static com.example.vicinia.services.ChatMessageServices.getWelcome;

public class MainActivity extends AppCompatActivity {
    static MainActivity instance;
    FragmentManager fragmentManager;

    public String uuid;

    public GPSTracker gps;

    private ChatMessageFragment fChatMessage;
    private ChatHistoryFragment fChatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        fragmentManager = getSupportFragmentManager();
        gps = new GPSTracker(this);

        fChatMessage = (ChatMessageFragment) fragmentManager.findFragmentById(R.id.chat_message_fragment);
        fChatHistory = (ChatHistoryFragment) fragmentManager.findFragmentById(R.id.chat_history_fragment);
    }

    public void onSendMessage(String message) {
        fChatHistory.onSendMessage(message);
    }

    public void onReceiveMessage(String message) {
        fChatHistory.onReceiveMessage(message);

    }

    public void onChatButton(View v) {
        fChatMessage.onChatButton(v);
    }

    public static MainActivity getInstance() {
        return instance;
    }
}
