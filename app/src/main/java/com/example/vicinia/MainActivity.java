package com.example.vicinia;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.vicinia.activities.SplashActivity;
import com.example.vicinia.fragments.ChatHistoryFragment;
import com.example.vicinia.fragments.ChatMessageFragment;
import com.example.vicinia.fragments.QuickActionFragment;
import com.example.vicinia.services.ChatMessageServices;
import com.example.vicinia.services.GpsServices;
import com.example.vicinia.utilities.DialogUtilities;

import org.json.JSONObject;

import static com.example.vicinia.services.ChatMessageServices.getDetails;
import static com.example.vicinia.utilities.DialogUtilities.gpsErrorBuilder;
import static com.example.vicinia.utilities.DialogUtilities.helpDialogBuilder;
import static com.example.vicinia.utilities.DialogUtilities.internetErrorDialogBuider;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "VICINIA/MainActivity";

    public static MainActivity instance;

    public GpsServices gpsServices;
    private FragmentManager fragmentManager;

    //uuid received from backend
    private String uuid;

    //fragments
    private ChatMessageFragment fChatMessage;
    private ChatHistoryFragment fChatHistory;
    private QuickActionFragment fQuickAction;

    /**
     * callback function when activity is being created
     *
     * @param savedInstanceState information about state
     */
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

    /**
     * callback function when activity starts
     * start gps
     *
     * @called_from: none
     * @calls: {@link GpsServices#onStart()}
     */
    @Override
    protected void onStart() {
        super.onStart();
        gpsServices.onStart();
    }

    /**
     * callback function when activity is stopped
     * stop gps
     *
     * @called_from: none
     * @calls: {@link GpsServices#onStop()}
     */
    @Override
    protected void onStop() {
        super.onStop();
        gpsServices.onStop();
    }

    /**
     * callback function when activity populates Actionbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * callback function when activity an item in the
     * Actionbar is selected
     *
     * @called_from: none
     * @calls: {@link DialogUtilities#helpDialogBuilder(Context)}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        switch (itemThatWasClickedId) {
            case R.id.btn_help:
                helpDialogBuilder(this);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * called whenever send button is pressed
     * @param message message to be sent
     *
     * @called_from: {@link ChatMessageFragment#onChatButton()}
     * @calls: {@link ChatHistoryFragment#onSendMessage(String)}
     * {@link ChatHistoryFragment#sendTypingMessage()}
     */
    public void onSendMessage(String message) {
        fChatHistory.onSendMessage(message);
        fChatHistory.sendTypingMessage();
    }

    /**
     * called whenever reply is received
     * @param message message to be sent
     *
     * @called_from: {@link SplashActivity#onStop()}
     * {@link ChatMessageServices#onChatResponse(JSONObject)}
     * {@link ChatMessageServices#onDetailsResponse(JSONObject)}
     * @calls: {@link ChatHistoryFragment#onReceiveMessage(String)}
     */
    public void onReceiveMessage(String message) {
        fChatHistory.onReceiveMessage(message);
        enableAllButtons();
    }

    public void onChatButton(View v) {
        fChatMessage.onChatButton();
        disableAllButtons();
    }

    public void onCinemaButton(View v) {
        fQuickAction.onCinemaButton();
        fChatHistory.sendTypingMessage("Getting nearby cinemas...");
        disableAllButtons();
    }

    public void onGasStationButton(View v) {
        fQuickAction.onGasStationButton();
        fChatHistory.sendTypingMessage("Getting nearby gas stations...");
        disableAllButtons();
    }

    public void onHospitalButton(View v) {
        fQuickAction.onHospitalButton();
        fChatHistory.sendTypingMessage("Getting nearby hospitals...");
        disableAllButtons();
    }

    public void onRestaurantButton(View v) {
        fQuickAction.onRestaurantButton();
        fChatHistory.sendTypingMessage("Getting nearby restaurants...");
        disableAllButtons();
    }

    public void onGetDetailsButton(String placeID) {
        double lat = gpsServices.getLatitude();
        double lng = gpsServices.getLongitude();
        getDetails(placeID, lat, lng);

        fChatHistory.sendTypingMessage();
        disableAllButtons();
    }

    public void enableAllButtons(){
        fChatMessage.enableAllButtons();
        fQuickAction.enableAllButtons();
    }

    public void disableAllButtons(){
        fChatMessage.disableAllButtons();
        fQuickAction.disableAllButtons();
    }

    public ChatMessageFragment getChatMessageFragment() { return fChatMessage; }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void onInternetError() {
        internetErrorDialogBuider(this);
    }

    public void onGpsError() { gpsErrorBuilder(this); }

    public static MainActivity getInstance() {
        return instance;
    }
}
