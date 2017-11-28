package com.example.vicinia;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vicinia.services.GPSTracker;

import static com.example.vicinia.services.ChatMessageServices.getWelcome;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    public String uuid;

    public GPSTracker gps;

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    private Fragment fChatMessage;
    private Fragment fChatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        fChatMessage =  fragmentManager.findFragmentById(R.id.chat_message_fragment);
        fChatHistory = fragmentManager.findFragmentById(R.id.chat_history_fragment);

        gps = new GPSTracker(this);

        getWelcome();
    }


    /**
     * This method will make the error message visible and hide all other
     * View components
     */
    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
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
}
