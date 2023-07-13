package com.cst2335.Kaur0918;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cst2335.Kaur0918.data.ChatMessage;

import java.util.List;

@Dao
public interface ChatMessageDAO {
    @Insert
    long insertMessage(ChatMessage chatMessage);

    @Query("SELECT * FROM ChatMessage;")
    public List<ChatMessage> getAllMessages();

    @Delete
    void deleteMessage(ChatMessage chatMessage);
}
