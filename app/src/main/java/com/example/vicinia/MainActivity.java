package com.example.vicinia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.vicinia.utilities.ChatUtils.buildChatUrl;
import static com.example.vicinia.utilities.ChatUtils.buildDetailsUrl;
import static com.example.vicinia.utilities.ChatUtils.buildWelcomeUrl;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;

    Button mChatButton;
    Button mWelcomeButton;
    Button mDetailstButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mWelcomeButton = (Button) findViewById(R.id.welome_button);
        mChatButton = (Button) findViewById(R.id.chat_button);
        mDetailstButton = (Button) findViewById(R.id.details_button);

        mWelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(buildWelcomeUrl().toString());
            }
        });

        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(buildChatUrl().toString());
            }
        });

        mDetailstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(buildDetailsUrl("{placeID}").toString());
            }
        });
    }
}
