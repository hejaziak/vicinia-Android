package com.example.vicinia;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vicinia.messages.ChatMessage;
import com.example.vicinia.messages.MessageAdapter;
import com.example.vicinia.utilities.ChatUtils;

import java.io.IOException;
import java.net.URL;

import static com.example.vicinia.utilities.ChatUtils.buildChatUrl;
import static com.example.vicinia.utilities.ChatUtils.buildDetailsUrl;
import static com.example.vicinia.utilities.ChatUtils.buildWelcomeUrl;
import static com.example.vicinia.utilities.ChatUtils.getResponseFromHttpUrl;

public class MainActivity extends AppCompatActivity {

    ListView mChatHistory;
    private static MessageAdapter adapter;

    EditText mChatMessage;
    ImageButton mChatButton;

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChatHistory = findViewById(R.id.chat_history);

        adapter = new MessageAdapter(getApplicationContext());
        mChatHistory.setAdapter(adapter);

        mChatButton = findViewById(R.id.btn_send);
        mChatMessage = findViewById(R.id.et_message);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
    }

    /**
     * This method constructs the URL (using {@link ChatUtils}) for the welcome url ,
     * displays that URL in a TextView,
     * and finally fires off an AsyncTask to perform the GET request using {@link APIQueryTask}
     */
    public void onChatButton(View v){
//        URL url = buildWelcomeUrl();
//        mTextView.setText(url.toString());
//        new APIQueryTask().execute(url);

        String message = mChatMessage.getText().toString();
        mChatMessage.setText("");

        ChatMessage candidateMessage = new ChatMessage(message, false);
        adapter.add(candidateMessage);

    }

    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     */
    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
//        mTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     */
    private void showErrorMessage() {
//        mTextView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class APIQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String response = null;
            try {
                response = getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (/* githubSearchResults != null && */ !response.equals("")) {
                showJsonDataView();
//                mTextView.setText(response);
            } else {
                showErrorMessage();
            }
        }
    }
}
