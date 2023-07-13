package com.cst2335.Kaur0918;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cst2335.Kaur0918.data.ChatMessage;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract ChatMessageDAO chatMessageDAO();
}