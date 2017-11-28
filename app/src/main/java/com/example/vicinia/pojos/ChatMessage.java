package com.example.vicinia.pojos;

public class ChatMessage {
    private String content;
    private boolean leftAligned;

    public ChatMessage(String content, boolean leftAligned){
        super();
        this.content = content;
        this.leftAligned = leftAligned;
    }

    public String getContent() {
        return content;
    }

    public boolean isLeftAligned() {
        return leftAligned;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLeftAligned(boolean leftAligned) {
        this.leftAligned = leftAligned;
    }
}
