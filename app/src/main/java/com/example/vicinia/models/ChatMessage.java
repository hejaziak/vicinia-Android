package com.example.vicinia.models;

import android.view.View;
import android.view.ViewGroup;

import com.example.vicinia.adapters.ChatMessageAdapter;
import com.example.vicinia.fragments.ChatHistoryFragment;

/**
 * used to model chat messages when passing them to the list view adapter {@link com.example.vicinia.adapters.ChatMessageAdapter}
 */
public class ChatMessage {
    private String content;
    private boolean leftAligned;

    /**
     * @param content     the message
     * @param leftAligned indicates if leftMessage or rightMessage
     * @called_from: {@link ChatHistoryFragment#onSendMessage(String)}
     * {@link ChatHistoryFragment#sendTypingMessage(String)}
     * {@link ChatHistoryFragment#onReceiveMessage(String)}
     * @calls: none
     */
    public ChatMessage(String content, boolean leftAligned) {
        super();
        this.content = content;
        this.leftAligned = leftAligned;
    }

    /**
     * getter for content
     *
     * @return returns content
     * @called_from: {@link ChatMessageAdapter#getView(int, View, ViewGroup)}
     * @calls: none
     */
    public String getContent() {
        return content;
    }

    /**
     * setter for content
     *
     * @param content new content
     * @called_from: {@link ChatHistoryFragment#onReceiveMessage(String)}
     * @calls: none
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * getter for leftAligned flag
     *
     * @return returns content
     * @called_from: {@link ChatMessageAdapter#getView(int, View, ViewGroup)}
     * {@link ChatMessageAdapter#getView(int, View, ViewGroup)}
     * @calls: none
     */
    public boolean isLeftAligned() {
        return leftAligned;
    }
}
