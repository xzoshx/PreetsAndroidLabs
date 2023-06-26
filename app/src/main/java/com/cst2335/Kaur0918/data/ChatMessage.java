package com.cst2335.Kaur0918.data;

public class ChatMessage {
    private String message;
    private String timeSent;
    private boolean isSendButton;

    public ChatMessage() {
    }

    public ChatMessage(String message, String timeSent, boolean isSendButton) {
        this.message = message;
        this.timeSent = timeSent;
        this.isSendButton = isSendButton;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean getIsSendButton() {
        return isSendButton;
    }
}
