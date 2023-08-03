package com.cst2335.Kaur0918;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    public long id;
    @ColumnInfo(name = "Message")
    public String message;

    @ColumnInfo(name = "TimeSent")
    public String timeSent;

    @ColumnInfo(name = "SendOrReceived")
    public boolean isSendButton;

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
