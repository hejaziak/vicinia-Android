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
import com.example.vicinia.models.ApiMessage;
import com.example.vicinia.services.ApiServices;
import com.example.vicinia.services.GpsServices;
import com.example.vicinia.utilities.DialogUtilities;

import static com.example.vicinia.utilities.DialogUtilities.gpsErrorBuilder;
import static com.example.vicinia.utilities.DialogUtilities.helpDialogBuilder;
import static com.example.vicinia.utilities.DialogUtilities.internetErrorDialogBuider;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "VICINIA/MainActivity";

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

        uuid = getIntent().getStringExtra("UUID");

        fragmentManager = getSupportFragmentManager();
        gpsServices = new GpsServices(this);

        fChatMessage = (ChatMessageFragment) fragmentManager.findFragmentById(R.id.chat_message_fragment);
        fChatHistory = (ChatHistoryFragment) fragmentManager.findFragmentById(R.id.chat_history_fragment);
        fQuickAction = (QuickActionFragment) fragmentManager.findFragmentById(R.id.quick_actions_fragment);

        //hide keyboard at startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String welcomeMessageContent = getIntent().getStringExtra("WELCOME_MESSAGE");

        ApiMessage welcomeMessage = new ApiMessage();
        welcomeMessage.setMessage(welcomeMessageContent);
        onReceiveMessage(welcomeMessage);
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
     * @calls:       {@link ChatHistoryFragment#onReceiveMessage(String)}
     */
    public void onReceiveMessage(ApiMessage message) {
        fChatHistory.onReceiveMessage(message);
    }

    /**
     * called whenever send button is pressed
     * @param v the view (button)
     *
     * @called_from: none
     * @calls:  {@link ChatMessageFragment#onChatButton()}
     */
    public void onChatButton(View v) {
        fChatMessage.onChatButton();
    }

    /**
     * called whenever cinema button is pressed
     * @param v the view (button)
     *
     * @called_from: none
     * @calls:  {@link ChatMessageFragment#onChatButton()}
     *          {@link ChatHistoryFragment#sendTypingMessage(String)}
     */
    public void onCinemaButton(View v) {
        fQuickAction.onCinemaButton();
        fChatHistory.sendTypingMessage("Getting nearby cinemas...");
    }

    /**
     * called whenever gas station button is pressed
     * @param v the view (button)
     *
     * @called_from: none
     * @calls:  {@link ChatMessageFragment#onChatButton()}
     *          {@link ChatHistoryFragment#sendTypingMessage(String)}
     */
    public void onGasStationButton(View v) {
        fQuickAction.onGasStationButton();
        fChatHistory.sendTypingMessage("Getting nearby gas stations...");
    }

    /**
     * called whenever hospital button is pressed
     * @param v the view (button)
     *
     * @called_from: none
     * @calls:  {@link ChatMessageFragment#onChatButton()}
     *          {@link ChatHistoryFragment#sendTypingMessage(String)}
     */
    public void onHospitalButton(View v) {
        fQuickAction.onHospitalButton();
        fChatHistory.sendTypingMessage("Getting nearby hospitals...");
    }

    /**
     * called whenever restaurant button is pressed
     * @param v the view (button)
     *
     * @called_from: none
     * @calls:  {@link ChatMessageFragment#onChatButton()}
     *          {@link ChatHistoryFragment#sendTypingMessage(String)}
     */
    public void onRestaurantButton(View v) {
        fQuickAction.onRestaurantButton();
        fChatHistory.sendTypingMessage("Getting nearby restaurants...");
    }

    public void sendChatMessage(String message){
        String lat = String.valueOf(gpsServices.getLatitude());
        String lng = String.valueOf(gpsServices.getLongitude());

        if(lat.equals("0.0") || lng.equals("0.0")) {
            onGpsError();
            return;
        }
        ApiServices.postChat(this, uuid, lat, lng, message);
    }

    /**
     * called whenever details button is pressed
     * @param placeID id of place in interest
     *
     * @called_from: none
     * @calls:  {@link ChatHistoryFragment#sendTypingMessage()}
     */
    public void onGetDetailsButton(String placeID) {
        double lat = gpsServices.getLatitude();
        double lng = gpsServices.getLongitude();

        if(lat == 0 || lng == 0){
            onGpsError();
            return;
        }
        ApiServices.getDetails(this, uuid, placeID, lat, lng);

        fChatHistory.sendTypingMessage();
    }

    public void onInternetError() {
        internetErrorDialogBuider(this);
    }

    public void onGpsError() { gpsErrorBuilder(this); }

    public ChatMessageFragment getChatMessageFragment() { return fChatMessage; }
}
