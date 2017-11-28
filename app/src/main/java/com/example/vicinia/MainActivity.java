package com.example.vicinia;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vicinia.fragments.ChatHistoryFragment;
import com.example.vicinia.fragments.ChatMessageFragment;
import com.example.vicinia.services.GPSTracker;

import static com.example.vicinia.services.ChatMessageServices.getWelcome;

public class MainActivity extends AppCompatActivity {
    static MainActivity instance;
    FragmentManager fragmentManager;

    public String uuid;

    public GPSTracker gps;

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    private ChatMessageFragment fChatMessage;
    private ChatHistoryFragment fChatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        fragmentManager = getSupportFragmentManager();

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        fChatMessage = (ChatMessageFragment) fragmentManager.findFragmentById(R.id.chat_message_fragment);
        fChatHistory = (ChatHistoryFragment) fragmentManager.findFragmentById(R.id.chat_history_fragment);

        gps = new GPSTracker(this);

        getWelcome();
    }


    /**
     * This method will make the error message visible and hide all other
     * View components
     */
    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
//        mTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide all other
     * View components
     */
    private void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);

    }

    /**
     * This method will make the error message visible and hide all other
     * View components
     */
    private void showErrorMessage() {
//        mTextView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public static MainActivity getInstance() {
        return instance;
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
}
