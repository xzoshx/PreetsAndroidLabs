package com.cst2335.Kaur0918.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cst2335.Kaur0918.ChatMessage;
import com.cst2335.Kaur0918.data.ChatMessageDAO;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract ChatMessageDAO cmDAO();
}